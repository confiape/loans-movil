package org.confiape.loan.login

import android.util.Log
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
import org.confiape.loan.core.AppConstants
import org.confiape.loan.core.Routes

@Composable
fun LoginScreen(viewModel: LoginViewModel, navigationController: NavHostController) {
    Log.i(AppConstants.Tag,"Init login screen")
    val isAlreadyNavigating = remember { mutableStateOf(false) }
    if (viewModel.state.isAuthenticated && !isAlreadyNavigating.value) {
        isAlreadyNavigating.value = true
        navigationController.navigate(Routes.Borrower.route){
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
                Button(onClick = { viewModel.authenticateWithGoogle(context,navigationController) }) {
                    Text("SigIn with Google")
                }
            }
        }
    }

}
