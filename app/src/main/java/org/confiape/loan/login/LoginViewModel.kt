package org.confiape.loan.login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTDecodeException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import org.confiape.loan.R
import org.confiape.loan.apis.AuthenticateApi
import org.confiape.loan.core.AppConstants
import org.confiape.loan.core.Routes
import org.confiape.loan.core.SharedService
import org.confiape.loan.models.TokenDto
import java.security.MessageDigest
import java.util.Date
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticateApi: AuthenticateApi,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    var state by mutableStateOf(LoginState())


    init {
        val currentToken = SharedService.getToken(AppConstants.AuthenticationToken, context)
        if (currentToken.isNotEmpty()) {
            state = state.copy(
                isLogging = true
            )
            state = if (isJwtExpired(currentToken.removePrefix("AuthenticationToken="))) {
                state.copy(
                    isLogging = false, isAuthenticated = false
                )
            } else {
                state.copy(
                    isLogging = false, isAuthenticated = true
                )
            }
        } else {
            state = state.copy(
                isLogging = false
            )
        }


    }

    private fun isJwtExpired(token: String): Boolean {
        return try {
            val decodedJWT = JWT.decode(token)
            val expiration = decodedJWT.expiresAt ?: return true
            expiration.before(Date())
        } catch (e: JWTDecodeException) {
            true
        }
    }

    private fun clean() {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun authenticateWithGoogle(context: Context, navigationController: NavHostController) {
        state = state.copy(
            isLogging = true
        )
        val credentialsManger = CredentialManager.create(context)

        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it ->
            str + "%02x".format(it)
        }
        val googleIdOption: GetGoogleIdOption =
            GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.web_client_id))
                .setAutoSelectEnabled(false)
                .setNonce(hashedNonce)

                .build()
        val request: GetCredentialRequest =
            GetCredentialRequest.Builder()

                .addCredentialOption(googleIdOption).build()

        viewModelScope.launch {
            try {
                val result = credentialsManger.getCredential(
                    request = request, context = context
                )
                val credential = result.credential


                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken
                onSuccessLogin(googleIdToken, navigationController)
                state = state.copy(
                    isLogging = false
                )
            } catch (e: Exception) {
                onErrorLogin(e.message ?: "Error")
                state = state.copy(
                    isLogging = false
                )
            }
        }
    }

    fun onOldLogin(googleIdToken: String, navigationController: NavHostController) {
        viewModelScope.launch {
            onSuccessLogin(googleIdToken, navigationController)
        }
    }

    private suspend fun onSuccessLogin(
        googleIdToken: String,
        navigationController: NavHostController,
    ) {
        val firstAuthResponse =
            authenticateApi.apiAuthenticateLoginWithGoogleTokenPost(TokenDto(googleIdToken))
        if (firstAuthResponse.code() != 200) {
            Log.i(AppConstants.Tag, "Sesión finalizada")
            clean()
            throw Exception("Sesión finalizada")
        }

        state = state.copy(
            message = "Correcto",
            isLogging = false
        )

        navigationController.navigate(Routes.Borrower.route)

    }

    fun onErrorLogin(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        state = state.copy(
            isLogging = false
        )
    }

}


data class LoginState(
    val isLogging: Boolean = true,
    val isAuthenticated: Boolean = false,
    val message: String = "",
)