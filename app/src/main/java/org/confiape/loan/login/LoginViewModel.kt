package org.confiape.loan.login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.confiape.loan.apis.AuthenticateApi
import org.confiape.loan.apis.LoanApi
import org.confiape.loan.core.AppConstants
import org.confiape.loan.core.SharedService
import org.confiape.loan.models.CompleteLoanDto
import org.confiape.loan.models.TokenDto
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticateApi: AuthenticateApi,
    private val loanApi: LoanApi,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    suspend fun LoginByGoogleToken(googleToken: String): CompleteLoanDto? {
        authenticateApi.apiAuthenticateLoginWithGoogleTokenPost(TokenDto(googleToken))
        var response = loanApi.apiLoanIdGet(UUID.fromString("11df2392-2dd0-43e6-df67-08dcce043417"))
        Log.i(AppConstants.Tag, "response code ${response.code()}")
        return response.body()
    }

    fun Clean() {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun IsAuthenticated(): Boolean {
        return SharedService.getToken(AppConstants.AuthenticationToken, context).isNotEmpty()
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}