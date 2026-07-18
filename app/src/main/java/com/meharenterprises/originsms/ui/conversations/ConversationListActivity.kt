package com.meharenterprises.originsms.ui.conversations

import android.Manifest
import android.app.role.RoleManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
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

class ConversationListActivity : AppCompatActivity() {

    private lateinit var viewModel: ConversationListViewModel
    private lateinit var adapter: ConversationAdapter
    private lateinit var pinManager: PinManager

    private val requiredPermissions = buildList {
        add(Manifest.permission.SEND_SMS)
        add(Manifest.permission.RECEIVE_SMS)
        add(Manifest.permission.READ_SMS)
        add(Manifest.permission.READ_CONTACTS)
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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation_list)
        pinManager = PinManager(this)

        viewModel = ViewModelProvider(this)[ConversationListViewModel::class.java]

        setupToolbar()
        setupRecyclerView()
        setupFab()
        setupDefaultAppBanner()

        viewModel.conversations.observe(this) { list ->
            adapter.submitList(list)
            findViewById<View>(R.id.emptyState).visibility =
                if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        ensurePermissionsThenLoad()
        refreshDefaultAppBanner()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupRecyclerView() {
        val recycler = findViewById<RecyclerView>(R.id.recyclerConversations)
        adapter = ConversationAdapter(
            onClick = { conversation -> openConversation(conversation) },
            onLongClick = { conversation, anchor -> showConversationMenu(conversation, anchor) }
        )
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
    }

    private fun setupFab() {
        findViewById<FloatingActionButton>(R.id.fabNewMessage).setOnClickListener {
            startActivity(Intent(this, ComposeActivity::class.java))
        }
    }

    private fun setupDefaultAppBanner() {
        findViewById<View>(R.id.btnSetDefault).setOnClickListener {
            requestDefaultSmsRole()
        }
    }

    private fun refreshDefaultAppBanner() {
        val banner = findViewById<View>(R.id.defaultAppBanner)
        banner.visibility = if (isDefaultSmsApp()) View.GONE else View.VISIBLE
    }

    private fun isDefaultSmsApp(): Boolean {
        return Telephony.Sms.getDefaultSmsPackage(this) == packageName
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

    private fun showConversationMenu(conversation: ConversationSummary, anchor: View) {
        val popup = androidx.appcompat.widget.PopupMenu(this, anchor)
        popup.menu.add(
            if (conversation.isLocked) getString(R.string.menu_unlock_chat) else getString(R.string.menu_lock_chat)
        ).setOnMenuItemClickListener {
            onLockToggleRequested(conversation)
            true
        }
        popup.menu.add(getString(R.string.menu_hide_chat)).setOnMenuItemClickListener {
            onHideRequested(conversation)
            true
        }
        popup.menu.add(
            if (conversation.unreadCount > 0) getString(R.string.menu_mark_read) else getString(R.string.menu_mark_unread)
        ).setOnMenuItemClickListener {
            viewModel.markRead(conversation.threadId)
            true
        }
        popup.menu.add(getString(R.string.menu_delete_chat)).setOnMenuItemClickListener {
            confirmDelete(conversation)
            true
        }
        popup.show()
    }

    private fun onLockToggleRequested(conversation: ConversationSummary) {
        if (conversation.isLocked) {
            val intent = Intent(this, LockUnlockActivity::class.java).apply {
                putExtra(LockUnlockActivity.EXTRA_THREAD_ID, conversation.threadId)
                putExtra(LockUnlockActivity.EXTRA_ADDRESS, conversation.address)
                putExtra(LockUnlockActivity.EXTRA_DISPLAY_NAME, conversation.displayName)
                putExtra(LockUnlockActivity.EXTRA_UNLOCK_INTENT, LockUnlockActivity.INTENT_REMOVE_LOCK)
            }
            startActivity(intent)
        } else {
            if (pinManager.hasPinConfigured()) {
                viewModel.setLocked(conversation.threadId, true)
            } else {
                val intent = Intent(this, LockSetupActivity::class.java).apply {
                    putExtra(LockSetupActivity.EXTRA_THREAD_ID_TO_LOCK, conversation.threadId)
                }
                startActivity(intent)
            }
        }
    }

    private fun onHideRequested(conversation: ConversationSummary) {
        if (!pinManager.hasPinConfigured()) {
            AlertDialog.Builder(this)
                .setTitle(R.string.lock_title_setup)
                .setMessage(R.string.lock_reset_warning)
                .setPositiveButton(R.string.action_grant) { _, _ ->
                    val intent = Intent(this, LockSetupActivity::class.java).apply {
                        putExtra(LockSetupActivity.EXTRA_THREAD_ID_TO_LOCK, conversation.threadId)
                        putExtra(LockSetupActivity.EXTRA_ALSO_HIDE, true)
                    }
                    startActivity(intent)
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
            return
        }
        viewModel.setLocked(conversation.threadId, true)
        viewModel.setHidden(conversation.threadId, true)
    }

    private fun confirmDelete(conversation: ConversationSummary) {
        AlertDialog.Builder(this)
            .setTitle(R.string.menu_delete_chat)
            .setMessage(conversation.displayName)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                viewModel.deleteThread(conversation.threadId)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.menu_conversation_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            R.id.action_hidden_chats -> {
                openHiddenChatsVault()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openHiddenChatsVault() {
        val intent = Intent(this, LockUnlockActivity::class.java).apply {
            putExtra(LockUnlockActivity.EXTRA_UNLOCK_INTENT, LockUnlockActivity.INTENT_OPEN_VAULT)
        }
        startActivity(intent)
    }
}
