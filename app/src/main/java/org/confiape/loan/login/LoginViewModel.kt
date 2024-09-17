package org.confiape.loan.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.confiape.loan.LoanApp
import org.confiape.loan.apis.AuthenticateApi
import org.confiape.loan.apis.LoanApi
import org.confiape.loan.infrastructure.ApiClient
import org.confiape.loan.models.BasicLoanDtoPaginationResponse
import org.confiape.loan.models.LoginDto
import org.confiape.loan.models.TokenDto
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticateApi: AuthenticateApi,
    private val loanApi: LoanApi
) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    suspend fun LoginByGoogleToken(googleToken: String): BasicLoanDtoPaginationResponse? {
        val response=authenticateApi.apiAuthenticateLoginWithGoogleTokenPost(TokenDto(googleToken))
        return loanApi.apiLoanGet().body()
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}