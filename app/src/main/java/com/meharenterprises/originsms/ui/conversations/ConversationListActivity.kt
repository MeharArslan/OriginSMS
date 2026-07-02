package com.meharenterprises.originsms.ui.conversations

import android.Manifest
import android.app.role.RoleManager
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.core.ConversationSummary
import com.meharenterprises.originsms.lock.LockSetupActivity
import com.meharenterprises.originsms.lock.LockUnlockActivity
import com.meharenterprises.originsms.lock.PinManager
import com.meharenterprises.originsms.ui.SettingsActivity
import com.meharenterprises.originsms.ui.compose.ComposeActivity
import com.meharenterprises.originsms.ui.thread.ThreadActivity
import kotlinx.coroutines.launch

class ConversationListActivity : AppCompatActivity() {

    private lateinit var viewModel: ConversationListViewModel
    private lateinit var adapter: ConversationAdapter
    private lateinit var pinManager: PinManager
    private lateinit var prefs: SharedPreferences

    private lateinit var searchBarRow: View
    private lateinit var editSearch: android.widget.EditText
    private lateinit var selectionActionBar: View
    private lateinit var txtSelectionCount: android.widget.TextView
    private lateinit var toolbar: MaterialToolbar

    private var searchModeActive = false

    private val requiredPermissions = buildList {
        add(Manifest.permission.SEND_SMS)
        add(Manifest.permission.RECEIVE_SMS)
        add(Manifest.permission.READ_SMS)
        add(Manifest.permission.READ_CONTACTS)
        add(Manifest.permission.READ_PHONE_STATE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        if (result.values.all { it }) {
            viewModel.loadConversations()
        }
    }

    private val roleRequestLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        refreshDefaultAppBanner()
        // Becoming the default SMS app changes what the Telephony provider
        // returns (and on some OEM builds, which columns are queryable), so
        // always force a fresh load right after the role grant completes.
        viewModel.loadConversations()
        // The system can take a brief moment to finish exposing the previous
        // default app's message history through the provider after the role
        // switch — a short delayed second reload catches that case so older
        // conversations (e.g. from Google Messages) appear without the user
        // having to manually pull-to-refresh or reopen the app.
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            viewModel.loadConversations()
        }, 1500L)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation_list)
        pinManager = PinManager(this)
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        viewModel = ViewModelProvider(this)[ConversationListViewModel::class.java]

        setupToolbar()
        setupSearchBar()
        setupSelectionActionBar()
        setupRecyclerView()
        setupFab()
        setupDefaultAppBanner()

        viewModel.conversations.observe(this) { list ->
            adapter.submitList(list)
            findViewById<View>(R.id.emptyState).visibility =
                if (list.isEmpty()) View.VISIBLE else View.GONE
        }

        maybePromptDefaultAppOnFirstLaunch()
    }

    override fun onResume() {
        super.onResume()
        ensurePermissionsThenLoad()
        refreshDefaultAppBanner()
        processScheduledAutoActions()
    }

    /**
     * Checks for any thread whose scheduled auto-unhide or auto-unmute time
     * has passed and clears the corresponding flag. There's no background
     * job for this — it's deliberately a lightweight check-on-open instead,
     * since the only consequence of a slightly-late unhide is the chat
     * staying hidden a few extra minutes until the user next opens the app,
     * which is an acceptable tradeoff against running a persistent scheduler
     * for a personal messaging app.
     */
    private fun processScheduledAutoActions() {
        lifecycleScope.launch {
            val dao = com.meharenterprises.originsms.data.db.OriginDatabase
                .getInstance(this@ConversationListActivity).threadLockDao()
            val now = System.currentTimeMillis()
            val cal = java.util.Calendar.getInstance()
            val currentDayMinutes = cal.get(java.util.Calendar.HOUR_OF_DAY) * 60 +
                cal.get(java.util.Calendar.MINUTE)

            // Daily auto-hide: if current time >= configured time and chat is
            // not already hidden, hide it. It stays hidden until manually
            // unhidden via the in-chat 3-dot menu — no auto-unhide.
            dao.getThreadsWithDailyHide().forEach { entry ->
                if (entry.dailyHideTimeMinutes >= 0 &&
                    currentDayMinutes >= entry.dailyHideTimeMinutes &&
                    !entry.isHidden
                ) {
                    dao.upsert(entry.copy(isHidden = true, isLocked = true))
                }
            }

            // One-time scheduled unhide
            dao.getThreadsDueForAutoUnhide(now).forEach { entry ->
                dao.setHidden(entry.threadId, false)
                dao.setAutoUnhideAt(entry.threadId, 0L)
            }
            // Auto-unmute expiry
            dao.getThreadsDueForAutoUnmute(now).forEach { entry ->
                dao.setMutedUntil(entry.threadId, false, 0L)
            }
            viewModel.loadConversations()
        }
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    // ------------------------------------------------------------------
    // Search
    // ------------------------------------------------------------------

    private fun setupSearchBar() {
        searchBarRow = findViewById(R.id.searchBarRow)
        editSearch = findViewById(R.id.editSearch)

        findViewById<View>(R.id.btnCloseSearch).setOnClickListener { exitSearchMode() }

        editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                val query = s?.toString().orEmpty()
                // WhatsApp-style: if the typed text matches the PIN exactly,
                // open the hidden-chats vault and clear the search field so
                // the PIN is never visible in the UI for more than a moment.
                if (query.length >= 4 && pinManager.verifyPin(query)) {
                    editSearch.setText("")
                    exitSearchMode()
                    openHiddenChatsVault()
                    return
                }
                viewModel.setSearchQuery(query)
            }
        })
    }

    private fun enterSearchMode() {
        searchModeActive = true
        toolbar.visibility = View.GONE
        searchBarRow.visibility = View.VISIBLE
        editSearch.requestFocus()
        val imm = getSystemService(android.view.inputmethod.InputMethodManager::class.java)
        imm.showSoftInput(editSearch, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT)
    }

    private fun exitSearchMode() {
        searchModeActive = false
        editSearch.setText("")
        viewModel.setSearchQuery("")
        searchBarRow.visibility = View.GONE
        toolbar.visibility = View.VISIBLE
    }

    // ------------------------------------------------------------------
    // Multi-select action bar
    // ------------------------------------------------------------------

    private fun setupSelectionActionBar() {
        selectionActionBar = findViewById(R.id.selectionActionBar)
        txtSelectionCount = findViewById(R.id.txtSelectionCount)

        findViewById<View>(R.id.btnCloseSelection).setOnClickListener {
            adapter.clearSelection()
            updateSelectionBar()
        }
        findViewById<View>(R.id.btnSelectionMarkRead).setOnClickListener {
            viewModel.markReadMultiple(adapter.getSelectedThreadIds())
            adapter.clearSelection()
            updateSelectionBar()
        }
        findViewById<View>(R.id.btnSelectionArchive).setOnClickListener {
            viewModel.setArchived(adapter.getSelectedThreadIds(), true)
            adapter.clearSelection()
            updateSelectionBar()
        }
        findViewById<View>(R.id.btnSelectionDelete).setOnClickListener {
            val ids = adapter.getSelectedThreadIds()
            AlertDialog.Builder(this)
                .setTitle(R.string.menu_delete_chat)
                .setMessage(getString(R.string.menu_delete_chat) + " (${ids.size})")
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    viewModel.deleteThreads(ids)
                    adapter.clearSelection()
                    updateSelectionBar()
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }
    }

    private fun updateSelectionBar() {
        val count = adapter.getSelectedCount()
        if (count > 0) {
            selectionActionBar.visibility = View.VISIBLE
            txtSelectionCount.text = count.toString()
        } else {
            selectionActionBar.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        val recycler = findViewById<RecyclerView>(R.id.recyclerConversations)
        adapter = ConversationAdapter(
            onClick = { conversation -> openConversation(conversation) },
            onLongClick = { updateSelectionBar() }
        )
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
    }

    private fun setupFab() {
        findViewById<FloatingActionButton>(R.id.fabNewMessage).setOnClickListener {
            startActivity(Intent(this, ComposeActivity::class.java))
        }
    }

    // ------------------------------------------------------------------
    // Default SMS app banner + auto-prompt
    // ------------------------------------------------------------------

    private fun setupDefaultAppBanner() {
        findViewById<View>(R.id.btnSetDefault).setOnClickListener {
            requestDefaultSmsRole()
        }
    }

    private fun maybePromptDefaultAppOnFirstLaunch() {
        val alreadyPrompted = prefs.getBoolean(KEY_PROMPTED_DEFAULT, false)
        if (!alreadyPrompted && !isDefaultSmsApp()) {
            prefs.edit().putBoolean(KEY_PROMPTED_DEFAULT, true).apply()
            requestDefaultSmsRole()
        }
    }

    private fun refreshDefaultAppBanner() {
        val banner = findViewById<View>(R.id.defaultAppBanner)
        banner.visibility = if (isDefaultSmsApp()) View.GONE else View.VISIBLE
    }

    private fun isDefaultSmsApp(): Boolean {
        // RoleManager.isRoleHeld is the most reliable check — it correctly
        // identifies the app regardless of debug/release package name suffix.
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            getSystemService(android.app.role.RoleManager::class.java)
                .isRoleHeld(android.app.role.RoleManager.ROLE_SMS)
        } else {
            // Fallback for older devices: strip .debug suffix before comparing
            val defaultPkg = android.provider.Telephony.Sms.getDefaultSmsPackage(this) ?: return false
            defaultPkg == packageName || defaultPkg == packageName.removeSuffix(".debug")
        }
    }

    private fun requestDefaultSmsRole() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = getSystemService(RoleManager::class.java)
            if (roleManager.isRoleAvailable(RoleManager.ROLE_SMS)) {
                if (!roleManager.isRoleHeld(RoleManager.ROLE_SMS)) {
                    val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS)
                    roleRequestLauncher.launch(intent)
                }
            }
        } else {
            val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT).apply {
                putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName)
            }
            roleRequestLauncher.launch(intent)
        }
    }

    private fun ensurePermissionsThenLoad() {
        val missing = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        if (missing.isEmpty()) {
            viewModel.loadConversations()
        } else {
            permissionLauncher.launch(missing.toTypedArray())
        }
    }

    private fun openConversation(conversation: ConversationSummary) {
        if (conversation.isLocked) {
            val intent = Intent(this, LockUnlockActivity::class.java).apply {
                putExtra(LockUnlockActivity.EXTRA_THREAD_ID, conversation.threadId)
                putExtra(LockUnlockActivity.EXTRA_ADDRESS, conversation.address)
                putExtra(LockUnlockActivity.EXTRA_DISPLAY_NAME, conversation.displayName)
            }
            startActivity(intent)
        } else {
            openThread(conversation)
        }
    }

    private fun openThread(conversation: ConversationSummary) {
        val intent = Intent(this, ThreadActivity::class.java).apply {
            putExtra(ThreadActivity.EXTRA_THREAD_ID, conversation.threadId)
            putExtra(ThreadActivity.EXTRA_ADDRESS, conversation.address)
            putExtra(ThreadActivity.EXTRA_DISPLAY_NAME, conversation.displayName)
        }
        startActivity(intent)
        if (conversation.unreadCount > 0) {
            viewModel.markRead(conversation.threadId)
        }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.menu_conversation_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                enterSearchMode()
                true
            }
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            R.id.action_hidden_chats -> {
                openHiddenChatsVault()
                true
            }
            R.id.action_archived_chats -> {
                openArchivedChats()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        when {
            adapter.getSelectedCount() > 0 -> {
                adapter.clearSelection()
                updateSelectionBar()
            }
            searchModeActive -> exitSearchMode()
            else -> super.onBackPressed()
        }
    }

    private fun openHiddenChatsVault() {
        val intent = Intent(this, LockUnlockActivity::class.java).apply {
            putExtra(LockUnlockActivity.EXTRA_UNLOCK_INTENT, LockUnlockActivity.INTENT_OPEN_VAULT)
        }
        startActivity(intent)
    }

    private fun openArchivedChats() {
        lifecycleScope.launch {
            val archived = viewModel.getArchivedConversations()
            if (archived.isEmpty()) {
                AlertDialog.Builder(this@ConversationListActivity)
                    .setTitle(R.string.menu_archive_chat)
                    .setMessage("No archived chats.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
                return@launch
            }
            val names = archived.map { it.displayName }.toTypedArray()
            AlertDialog.Builder(this@ConversationListActivity)
                .setTitle(R.string.menu_archive_chat)
                .setItems(names) { _, index ->
                    openThread(archived[index])
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }
    }

    companion object {
        private const val PREFS_NAME = "origin_sms_app_prefs"
        private const val KEY_PROMPTED_DEFAULT = "prompted_default_on_launch"
    }
}
