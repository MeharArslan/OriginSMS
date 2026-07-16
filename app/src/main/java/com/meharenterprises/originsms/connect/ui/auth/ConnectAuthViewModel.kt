package com.meharenterprises.originsms.connect.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meharenterprises.originsms.connect.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object OtpSent : AuthUiState()
    object Success : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

@HiltViewModel
class ConnectAuthViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    var pendingPhone = ""

    fun sendOtp(phone: String) {
        if (phone.length < 8) {
            _uiState.value = AuthUiState.Error("Enter a valid phone number")
            return
        }
        pendingPhone = phone
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            authRepo.sendOtp(phone).fold(
                onSuccess = { _uiState.value = AuthUiState.OtpSent },
                onFailure = { _uiState.value = AuthUiState.Error(it.message ?: "Failed") }
            )
        }
    }

    fun verifyOtp(otp: String, displayName: String) {
        if (otp.length < 4) {
            _uiState.value = AuthUiState.Error("Enter valid OTP")
            return
        }
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            authRepo.verifyOtp(pendingPhone, otp, displayName).fold(
                onSuccess = { _uiState.value = AuthUiState.Success },
                onFailure = { _uiState.value = AuthUiState.Error(it.message ?: "OTP incorrect") }
            )
        }
    }

    fun resetState() { _uiState.value = AuthUiState.Idle }
}
