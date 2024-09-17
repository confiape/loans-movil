package org.confiape.loan.login

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.navigation.NavHostController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.launch
import org.confiape.loan.core.Routes

@Composable
fun LoginScreen(viewModel: LoginViewModel, navigationController: NavHostController) {

    if(viewModel.IsAuthenticated()){
        navigationController.navigate(Routes.Borrower.route)
        return
    }

    val context = LocalContext.current;
    val coroutineScope = rememberCoroutineScope()
    val onCLick: () -> Unit = {
        val credentialsManger = CredentialManager.create(context);
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("968556857827-9k652ictsdusamhl8t48nqat2j90cnkr.apps.googleusercontent.com")
            .build();
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build();
        coroutineScope.launch {
            try {
                val result = credentialsManger.getCredential(
                    request = request,
                    context = context
                )
                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googlIdToken = googleIdTokenCredential.idToken

                val loans = viewModel.LoginByGoogleToken(googlIdToken)

                Toast.makeText(context, "You are signed ${loans!!.name}", Toast.LENGTH_SHORT).show()
                navigationController.navigate(Routes.Borrower.route)
            } catch (e: GetCredentialException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            } catch (e: GoogleIdTokenParsingException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }

    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onCLick) {
            Text("SigIn with Google")
        }
    }
}