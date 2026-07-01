package com.meharenterprises.originsms.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.core.ContactsHelper
import com.meharenterprises.originsms.data.db.BlockedNumberEntity
import com.meharenterprises.originsms.data.db.OriginDatabase
import kotlinx.coroutines.launch

class BlockedNumbersActivity : AppCompatActivity() {

    private lateinit var adapter: BlockedNumberAdapter
    private val database by lazy { OriginDatabase.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blocked_numbers)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.setDisplayShowTitleEnabled(false)

        adapter = BlockedNumberAdapter(onUnblock = { entity -> unblock(entity) })
        findViewById<RecyclerView>(R.id.recyclerBlocked).apply {
            layoutManager = LinearLayoutManager(this@BlockedNumbersActivity)
            adapter = this@BlockedNumbersActivity.adapter
        }

        findViewById<FloatingActionButton>(R.id.fabAddBlocked).setOnClickListener {
            showAddBlockedDialog()
        }

        observeBlockedNumbers()
    }

    private fun observeBlockedNumbers() {
        database.blockedNumberDao().observeAll().let { flow ->
            lifecycleScope.launch {
                flow.collect { list ->
                    adapter.submitList(list)
                    findViewById<View>(R.id.emptyState).visibility =
                        if (list.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }

    private fun showAddBlockedDialog() {
        val input = EditText(this).apply {
            hint = "Phone number"
            inputType = android.text.InputType.TYPE_CLASS_PHONE
        }
        AlertDialog.Builder(this)
            .setTitle(R.string.settings_blocked_numbers)
            .setView(input)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val raw = input.text?.toString()?.trim().orEmpty()
                if (raw.isNotBlank()) {
                    blockNumber(raw)
                }
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun blockNumber(rawNumber: String) {
        val normalized = ContactsHelper.normalize(rawNumber)
        lifecycleScope.launch {
            database.blockedNumberDao().block(
                BlockedNumberEntity(
                    normalizedNumber = normalized,
                    displayNumber = rawNumber,
                    blockedAtMillis = System.currentTimeMillis()
                )
            )
        }
    }

    private fun unblock(entity: BlockedNumberEntity) {
        lifecycleScope.launch {
            database.blockedNumberDao().unblock(entity.normalizedNumber)
        }
    }
}
