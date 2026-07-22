package com.meharenterprises.originsms.ui.conversations

import android.Manifest
import android.app.role.RoleManager
import android.content.Intent
import com.meharenterprises.originsms.ui.ArchivedChatsActivity
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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.core.ConversationSummary
import com.meharenterprises.originsms.lock.LockSetupActivity
import com.meharenterprises.originsms.lock.LockUnlockActivity
import com.meharenterprises.originsms.lock.PinManager
import com.meharenterprises.originsms.ui.SettingsActivity
import com.meharenterprises.originsms.ui.GeneralSettingsActivity
import com.meharenterprises.originsms.ui.compose.ComposeActivity
import com.meharenterprises.originsms.ui.thread.ThreadActivity
import kotlinx.coroutines.launch

class ConversationListActivity : AppCompatActivity() {
    private var smsContentObserver: android.database.ContentObserver? = null
    private var contentObserver: android.database.ContentObserver? = null

    private val unhideReceiver = object : android.content.BroadcastReceiver() {
        override fun onReceive(ctx: android.content.Context?, i: android.content.Intent?) {
            viewModel.loadConversations()
        }
    }

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
        if (result.values.all { it } && isDefaultSmsApp()) {
            viewModel.loadConversations()
        }
        refreshDefaultAppBanner()
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

    override fun onPause() {
        super.onPause()
        contentObserver?.let { contentResolver.unregisterContentObserver(it) }
        contentObserver = null
    }

    override fun onResume() {
        super.onResume()
        ensurePermissionsThenLoad()
        registerSmsObserver()
        if (contentObserver == null) {
            val h = android.os.Handler(android.os.Looper.getMainLooper())
            val r = Runnable { viewModel.loadConversations() }
            contentObserver = object : android.database.ContentObserver(h) {
                override fun onChange(selfChange: Boolean) { h.removeCallbacks(r); h.postDelayed(r, 50) }
            }
            contentResolver.registerContentObserver(android.provider.Telephony.Sms.CONTENT_URI, true, contentObserver!!)
        }
        refreshDefaultAppBanner()
        processScheduledAutoActions()
        // Refresh display name instantly in case it was changed in Settings
        val displayName = prefs.getString(KEY_DISPLAY_NAME, null)
        toolbar.title = if (!displayName.isNullOrBlank()) displayName
                        else getString(R.string.title_conversations)
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
            val thirtyDaysAgo = now - (30L * 24 * 60 * 60 * 1000)

            // Auto-permanently-delete trashed chats older than 30 days
            dao.getThreadsDueForPermanentDeletion(thirtyDaysAgo).forEach { entry ->
                com.meharenterprises.originsms.core.SmsRepository(this@ConversationListActivity)
                    .deleteThread(entry.threadId)
                dao.clear(entry.threadId)
            }

            // Daily auto-hide
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
            // Only load if app is default SMS app
            if (isDefaultSmsApp()) {
                viewModel.loadConversations()
            }
        }
        refreshDefaultAppBanner()
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        val displayName = prefs.getString(KEY_DISPLAY_NAME, null)
        toolbar.title = if (!displayName.isNullOrBlank()) displayName else getString(R.string.title_conversations)
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
        findViewById<View>(R.id.btnSelectionPin).setOnClickListener {
            viewModel.setPinned(adapter.getSelectedThreadIds(), true)
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
            val count = ids.size
            val name = if (count == 1) adapter.currentList.firstOrNull { it.threadId in ids }?.displayName ?: "Chat" else "$count chats"
            showMoveToTrashDialog(name, onConfirm = {
                viewModel.moveToTrash(ids)
                adapter.clearSelection()
                updateSelectionBar()
                val msg = if (count == 1) "1 chat moved to Trash" else "$count chats moved to Trash"
                com.google.android.material.snackbar.Snackbar.make(
                    findViewById(R.id.recyclerConversations), msg,
                    com.google.android.material.snackbar.Snackbar.LENGTH_LONG
                ).setAction("Undo") {
                    viewModel.restoreFromTrash(ids)
                }.show()
            })
        }
        findViewById<View>(R.id.btnSelectionMore).setOnClickListener { view ->
            val popup = android.widget.PopupMenu(this, view)
            popup.menuInflater.inflate(R.menu.menu_selection_more, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                val ids = adapter.getSelectedThreadIds()
                when (item.itemId) {
                    R.id.action_mark_read -> {
                        viewModel.markReadMultiple(ids)
                        adapter.clearSelection(); updateSelectionBar(); true
                    }
                    R.id.action_mark_unread -> {
                        viewModel.markUnreadMultiple(ids)
                        adapter.clearSelection(); updateSelectionBar(); true
                    }
                    R.id.action_block_selected -> {
                        viewModel.blockSelectedThreads(ids, this)
                        adapter.clearSelection(); updateSelectionBar(); true
                    }
                    else -> false
                }
            }
            popup.show()
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
            onClick = { conversation ->
                if (adapter.isSelectionMode) {
                    updateSelectionBar()
                } else {
                    openConversation(conversation)
                }
            },
            onLongClick = { updateSelectionBar() }
        )
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        // Swipe actions
        val swipePrefs = getSharedPreferences(GeneralSettingsActivity.PREFS_NAME, MODE_PRIVATE)
        val swipeCallback = object : androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback(
            0, androidx.recyclerview.widget.ItemTouchHelper.LEFT or androidx.recyclerview.widget.ItemTouchHelper.RIGHT
        ) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false

            override fun onChildDraw(
                c: android.graphics.Canvas, rv: RecyclerView, vh: RecyclerView.ViewHolder,
                dX: Float, dY: Float, actionState: Int, isActive: Boolean
            ) {
                val item = vh.itemView
                val paint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG)
                val margin = (20 * resources.displayMetrics.density).toInt()
                val iconSize = (28 * resources.displayMetrics.density).toInt()
                val midY = ((item.top + item.bottom) / 2f)

                if (dX > 0) {
                    // Swipe RIGHT — green Archive background (full width)
                    paint.color = android.graphics.Color.parseColor("#388E3C")
                    c.drawRect(item.left.toFloat(), item.top.toFloat(),
                        item.left + dX.coerceAtLeast(item.width.toFloat()),
                        item.bottom.toFloat(), paint)
                    // Archive icon
                    val archiveIcon = androidx.core.content.ContextCompat.getDrawable(
                        this@ConversationListActivity, R.drawable.ic_swipe_archive)
                    archiveIcon?.setTint(android.graphics.Color.WHITE)
                    val left = item.left + margin
                    archiveIcon?.setBounds(left, (midY - iconSize/2).toInt(),
                        left + iconSize, (midY + iconSize/2).toInt())
                    archiveIcon?.draw(c)
                } else if (dX < 0) {
                    // Swipe LEFT — red Delete background (full width)
                    paint.color = android.graphics.Color.parseColor("#C62828")
                    c.drawRect(item.right + dX.coerceAtMost(-item.width.toFloat()),
                        item.top.toFloat(), item.right.toFloat(), item.bottom.toFloat(), paint)
                    // Delete icon
                    val deleteIcon = androidx.core.content.ContextCompat.getDrawable(
                        this@ConversationListActivity, R.drawable.ic_swipe_delete)
                    deleteIcon?.setTint(android.graphics.Color.WHITE)
                    val right = item.right - margin
                    deleteIcon?.setBounds(right - iconSize, (midY - iconSize/2).toInt(),
                        right, (midY + iconSize/2).toInt())
                    deleteIcon?.draw(c)
                }
                super.onChildDraw(c, rv, vh, dX, dY, actionState, isActive)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                if (pos < 0) return
                val conv = adapter.currentList.getOrNull(pos) ?: return
                // Always reset highlight immediately
                adapter.notifyItemChanged(pos)
                val actionKey = if (direction == androidx.recyclerview.widget.ItemTouchHelper.RIGHT)
                    GeneralSettingsActivity.KEY_SWIPE_RIGHT else GeneralSettingsActivity.KEY_SWIPE_LEFT
                val action = swipePrefs.getInt(actionKey, if (direction == androidx.recyclerview.widget.ItemTouchHelper.RIGHT) 0 else 1)
                when (action) {
                    0 -> {
                        AlertDialog.Builder(this@ConversationListActivity)
                            .setTitle("Archive chat?")
                            .setMessage(conv.displayName)
                            .setPositiveButton("Archive") { _, _ ->
                                viewModel.setArchived(setOf(conv.threadId), true)
                                android.widget.Toast.makeText(this@ConversationListActivity,
                                    "${conv.displayName} archived", android.widget.Toast.LENGTH_SHORT).show()
                            }
                            .setNegativeButton(android.R.string.cancel, null)
                            .show()
                    }
                    1 -> {
                        showMoveToTrashDialog(conv.displayName, onConfirm = {
                            viewModel.moveToTrash(setOf(conv.threadId))
                            com.google.android.material.snackbar.Snackbar.make(
                                findViewById(R.id.recyclerConversations),
                                "1 chat moved to Trash",
                                com.google.android.material.snackbar.Snackbar.LENGTH_LONG
                            ).setAction("Undo") {
                                viewModel.restoreFromTrash(setOf(conv.threadId))
                            }.show()
                        })
                    }
                    2 -> {
                        viewModel.markRead(conv.threadId)
                        android.widget.Toast.makeText(this@ConversationListActivity,
                            "Marked as read", android.widget.Toast.LENGTH_SHORT).show()
                    }
                    else -> { /* already notified */ }
                }
            }
        }
        androidx.recyclerview.widget.ItemTouchHelper(swipeCallback).attachToRecyclerView(recycler)
    }

    private fun showMoveToTrashDialog(
        name: String,
        onConfirm: () -> Unit,
        onCancel: (() -> Unit)? = null
    ) {
        // Material Design 3 dialog — no Block Number button
        val dialog = com.google.android.material.dialog.MaterialAlertDialogBuilder(
            this, com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered
        )
            .setTitle("Delete conversation?")
            .setMessage("This conversation will be moved to Trash and can be restored before it is permanently deleted.")
            .setNegativeButton("Cancel") { d, _ ->
                d.dismiss()
                onCancel?.invoke()
            }
            .setPositiveButton("Move to Trash") { _, _ ->
                onConfirm()
            }
            .create()

        dialog.show()

        // Style the positive button as filled Material button
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.let { btn ->
            btn.setTextColor(getColor(R.color.origin_accent))
        }
    }

    private fun setupFab() {
        val fabNew = findViewById<com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton>(R.id.fabNewMessage)
        fabNew.setOnClickListener {
            startActivity(Intent(this, ComposeActivity::class.java))
        }

        val fabScrollTop = findViewById<FloatingActionButton>(R.id.fabScrollTop)
        val recycler = findViewById<RecyclerView>(R.id.recyclerConversations)

        fabScrollTop.setOnClickListener {
            recycler.smoothScrollToPosition(0)
        }

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                val firstVisible = (rv.layoutManager as? LinearLayoutManager)
                    ?.findFirstVisibleItemPosition() ?: 0
                fabScrollTop.visibility = if (firstVisible > 5) View.VISIBLE else View.GONE

                // Animate FAB: collapse (hide text) when scrolling down, expand when at top
                if (dy > 10) {
                    fabNew.shrink() // show only icon
                } else if (dy < -10 || firstVisible == 0) {
                    fabNew.extend() // show icon + text
                }
            }
        })
    }

    // ------------------------------------------------------------------
    // Default SMS app banner + auto-prompt
    // ------------------------------------------------------------------

    private fun setupDefaultAppBanner() {
        setupOnboarding()
    }

    private fun setupOnboarding() {
        val banner = findViewById<View>(R.id.defaultAppBanner)
        banner.visibility = View.GONE

        val container = findViewById<android.view.ViewGroup>(R.id.onboardingContainer) ?: return

        // Wire up the onboarding button
        container.findViewById<View>(R.id.btnSetDefaultOnboarding)?.setOnClickListener {
            requestDefaultSmsRole()
        }

        // Start blob animation
        container.findViewById<com.meharenterprises.originsms.ui.BlobAnimationView>(R.id.blobBackground)?.startAnimation()

        // Illustration is self-animating via SmsIllustrationView.onDraw
        // No outer/mid circle views in simplified layout
    }

    private fun maybePromptDefaultAppOnFirstLaunch() {
        // On first launch auto-prompt; after that rely on onboarding button
        val alreadyPrompted = prefs.getBoolean(KEY_PROMPTED_DEFAULT, false)
        if (!alreadyPrompted && !isDefaultSmsApp()) {
            prefs.edit().putBoolean(KEY_PROMPTED_DEFAULT, true).apply()
            // Don't auto-show dialog — let user tap the button on onboarding screen
        }
    }

    private fun animatePulse(view: View, duration: Long) {
        val anim = android.animation.ObjectAnimator.ofPropertyValuesHolder(
            view,
            android.animation.PropertyValuesHolder.ofFloat("scaleX", 1f, 1.08f, 1f),
            android.animation.PropertyValuesHolder.ofFloat("scaleY", 1f, 1.08f, 1f)
        )
        anim.duration = duration
        anim.repeatCount = android.animation.ValueAnimator.INFINITE
        anim.interpolator = android.view.animation.AccelerateDecelerateInterpolator()
        anim.start()
    }

    private fun refreshDefaultAppBanner() {
        val onboarding = findViewById<View>(R.id.onboardingContainer) ?: return
        val recycler = findViewById<View>(R.id.recyclerConversations)
        val emptyState = findViewById<View>(R.id.emptyState)
        val fabNew = findViewById<View>(R.id.fabNewMessage)
        val fabScroll = recycler?.let {
            (it.parent as? android.view.ViewGroup)?.let { p ->
                p.findViewById<View>(R.id.fabScrollTop)
            }
        }

        if (isDefaultSmsApp()) {
            if (onboarding.visibility == View.VISIBLE) {
                onboarding.animate().alpha(0f).setDuration(300)
                    .withEndAction { onboarding.visibility = View.GONE }.start()
            }
            recycler?.visibility = View.VISIBLE
            findViewById<View>(R.id.fabWrapper)?.visibility = View.VISIBLE
        } else {
            recycler?.visibility = View.GONE
            emptyState?.visibility = View.GONE
            findViewById<View>(R.id.fabWrapper)?.visibility = View.GONE
            if (onboarding.visibility != View.VISIBLE) {
                onboarding.alpha = 0f
                onboarding.visibility = View.VISIBLE
                onboarding.animate().alpha(1f).setDuration(400).start()
            }
        }
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
        // Step 1: Request permissions first (regardless of default SMS status)
        val missing = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        if (missing.isNotEmpty()) {
            permissionLauncher.launch(missing.toTypedArray())
            return
        }
        // Step 2: Check default SMS
        if (!isDefaultSmsApp()) {
            refreshDefaultAppBanner()
            return
        }
        viewModel.loadConversations()
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
        threadLauncher.launch(intent)
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

    private val threadLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { viewModel.loadConversations() }

    private val vaultLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            viewModel.loadConversations()
        }
    }

    private fun openHiddenChatsVault() {
        val intent = Intent(this, LockUnlockActivity::class.java).apply {
            putExtra(LockUnlockActivity.EXTRA_UNLOCK_INTENT, LockUnlockActivity.INTENT_OPEN_VAULT)
        }
        vaultLauncher.launch(intent)
    }

    private fun openArchivedChats() {
        startActivity(android.content.Intent(this, ArchivedChatsActivity::class.java))
    }

    private fun registerSmsObserver() {
        if (smsContentObserver != null) return
        val handler = android.os.Handler(android.os.Looper.getMainLooper())
        var pending = false
        smsContentObserver = object : android.database.ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                if (!pending) {
                    pending = true
                    handler.postDelayed({
                        pending = false
                        viewModel.loadConversations()
                    }, 150)
                }
            }
        }
        contentResolver.registerContentObserver(
            android.provider.Telephony.Sms.CONTENT_URI, true, smsContentObserver!!)
        contentResolver.registerContentObserver(
            android.net.Uri.parse("content://mms-sms/conversations"), true, smsContentObserver!!)
    }

    companion object {
        private const val PREFS_NAME = "origin_sms_app_prefs"
        private const val KEY_PROMPTED_DEFAULT = "prompted_default_on_launch"
        const val KEY_DISPLAY_NAME = "display_name"
    }
}
