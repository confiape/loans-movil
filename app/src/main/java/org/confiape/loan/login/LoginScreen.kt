package org.confiape.loan.login

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import org.confiape.loan.R
import org.confiape.loan.core.Routes

@Composable
fun LoginScreen(viewModel: LoginViewModel, navigationController: NavHostController) {
    val isAlreadyNavigating = remember { mutableStateOf(false) }
    if (viewModel.state.isAuthenticated && !isAlreadyNavigating.value) {
        isAlreadyNavigating.value = true
        navigationController.navigate(Routes.Borrower.route) {
            popUpTo(Routes.Login.route) { inclusive = true }
        }
        return
    }
    if (viewModel.state.isLogging) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        val context = LocalContext.current


        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    viewModel.authenticateWithGoogle(
                        context, navigationController
                    )
                }) {
                    Text("SigIn with Google")
                }


                OldGoogleSignInButton(context = context, viewModel, navigationController)
            }
        }
    }

}

@Composable
fun OldGoogleSignInButton(
    context: Context, viewModel: LoginViewModel, navigationController: NavHostController,
) {

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.web_client_id)).requestEmail().build()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.onOldLogin(
                    account.idToken ?: "", navigationController = navigationController
                )
            } catch (e: ApiException) {
                viewModel.onErrorLogin(e.message ?: "Error")
            }
        }

    Button(onClick = {
        val signInIntent = GoogleSignIn.getClient(context, gso).signInIntent
        launcher.launch(signInIntent)
    }) {
        Text(text = "Sign in with Google")
    }
}

