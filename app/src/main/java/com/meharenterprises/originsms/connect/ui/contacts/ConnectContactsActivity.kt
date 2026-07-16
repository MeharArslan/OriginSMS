package com.meharenterprises.originsms.connect.ui.contacts

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.connect.ui.chat.ConnectChatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConnectContactsActivity : AppCompatActivity() {

    private val viewModel: ConnectContactsViewModel by viewModels()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) loadAndSyncContacts()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_contacts)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "New Message"
        toolbar.setNavigationOnClickListener { finish() }

        val recycler = findViewById<RecyclerView>(R.id.recyclerContacts)
        val progress = findViewById<ProgressBar>(R.id.progressBar)
        val emptyText = findViewById<TextView>(R.id.txtEmpty)

        val adapter = ConnectContactsAdapter(
            onChat = { user ->
                viewModel.openOrCreateConversation(user.id) { conv ->
                    startActivity(Intent(this, ConnectChatActivity::class.java).apply {
                        putExtra("CONVERSATION_ID", conv.id)
                        putExtra("PARTICIPANT_ID", conv.participantId)
                        putExtra("PARTICIPANT_NAME", conv.participantName)
                        putExtra("PARTICIPANT_AVATAR", conv.participantAvatar)
                    })
                    finish()
                }
            }
        )
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contacts.collect { contacts ->
                    progress.visibility = View.GONE
                    adapter.submitList(contacts)
                    emptyText.visibility = if (contacts.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            == PackageManager.PERMISSION_GRANTED) {
            loadAndSyncContacts()
        } else {
            permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }
    }

    private fun loadAndSyncContacts() {
        val phones = mutableListOf<String>()
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
            null, null, null
        )
        cursor?.use { c ->
            val col = c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
            while (c.moveToNext()) phones.add(c.getString(col) ?: "")
        }
        viewModel.syncContacts(phones.distinct().filter { it.isNotBlank() })
    }
}
