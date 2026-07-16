package com.meharenterprises.originsms.connect.ui.splash

import androidx.lifecycle.ViewModel
import com.meharenterprises.originsms.connect.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConnectSplashViewModel @Inject constructor(
    authRepository: AuthRepository
) : ViewModel() {
    val authState = authRepository.authState
}
