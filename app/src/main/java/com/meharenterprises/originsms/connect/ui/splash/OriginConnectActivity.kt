package com.meharenterprises.originsms.connect.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.meharenterprises.originsms.connect.domain.model.AuthState
import com.meharenterprises.originsms.connect.ui.auth.ConnectAuthActivity
import com.meharenterprises.originsms.connect.ui.chat.ConnectChatListActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Entry point for Origin Connect.
 * Checks auth state → routes to Login or Chat list.
 */
@AndroidEntryPoint
class OriginConnectActivity : AppCompatActivity() {

    private val viewModel: ConnectSplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // No layout — pure routing screen

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.authState.collect { state ->
                    when (state) {
                        is AuthState.Loading -> { /* show splash */ }
                        is AuthState.Authenticated -> {
                            startActivity(Intent(this@OriginConnectActivity, ConnectChatListActivity::class.java))
                            finish()
                        }
                        is AuthState.Unauthenticated, is AuthState.Error -> {
                            startActivity(Intent(this@OriginConnectActivity, ConnectAuthActivity::class.java))
                            finish()
                        }
                    }
                }
            }
        }
    }
}
