package com.meharenterprises.originsms.connect.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.connect.ui.chat.ConnectChatListActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConnectAuthActivity : AppCompatActivity() {

    private val viewModel: ConnectAuthViewModel by viewModels()

    private lateinit var layoutPhone: View
    private lateinit var layoutOtp: View
    private lateinit var editPhone: EditText
    private lateinit var editOtp: EditText
    private lateinit var editName: EditText
    private lateinit var btnSendOtp: com.google.android.material.button.MaterialButton
    private lateinit var btnVerify: com.google.android.material.button.MaterialButton
    private lateinit var progressBar: ProgressBar
    private lateinit var txtError: TextView
    private lateinit var btnBack: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_auth)

        layoutPhone = findViewById(R.id.layoutPhone)
        layoutOtp = findViewById(R.id.layoutOtp)
        editPhone = findViewById(R.id.editPhone)
        editOtp = findViewById(R.id.editOtp)
        editName = findViewById(R.id.editName)
        btnSendOtp = findViewById(R.id.btnSendOtp)
        btnVerify = findViewById(R.id.btnVerify)
        progressBar = findViewById(R.id.progressBar)
        txtError = findViewById(R.id.txtError)
        btnBack = findViewById(R.id.btnBack)

        btnSendOtp.setOnClickListener {
            viewModel.sendOtp(editPhone.text.toString().trim())
        }

        btnVerify.setOnClickListener {
            viewModel.verifyOtp(
                editOtp.text.toString().trim(),
                editName.text.toString().trim().ifEmpty { "User" }
            )
        }

        btnBack.setOnClickListener {
            layoutPhone.visibility = View.VISIBLE
            layoutOtp.visibility = View.GONE
            viewModel.resetState()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    progressBar.visibility = if (state is AuthUiState.Loading) View.VISIBLE else View.GONE
                    txtError.visibility = if (state is AuthUiState.Error) View.VISIBLE else View.GONE
                    if (state is AuthUiState.Error) txtError.text = state.message

                    when (state) {
                        is AuthUiState.OtpSent -> {
                            layoutPhone.visibility = View.GONE
                            layoutOtp.visibility = View.VISIBLE
                        }
                        is AuthUiState.Success -> {
                            startActivity(Intent(this@ConnectAuthActivity, ConnectChatListActivity::class.java))
                            finish()
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}
