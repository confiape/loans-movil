package org.confiape.loan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import org.confiape.loan.login.LoginScreen
import org.confiape.loan.login.LoginViewModel
import org.confiape.loan.ui.theme.LoanTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor(

) : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels();

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LoginScreen(loginViewModel)
                    }
                }
            }
        }

    }

}

