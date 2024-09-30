package org.confiape.loan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.confiape.loan.borrowers.BorrowerScreen
import org.confiape.loan.borrowers.BorrowersViewModel
import org.confiape.loan.core.Routes
import org.confiape.loan.borrowers.loan.add.AddLoanViewModel
import org.confiape.loan.borrowers.loan.info.InfoLoanViewModel
import org.confiape.loan.borrowers.reports.ReportsViewModel
import org.confiape.loan.login.LoginScreen
import org.confiape.loan.login.LoginViewModel
import org.confiape.loan.ui.theme.LoanTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor(

) : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels();
    private val borrowerViewModel: BorrowersViewModel by viewModels();
    private val addLoanViewModel: AddLoanViewModel by viewModels();
    private val infoLoanViewModel: InfoLoanViewModel by viewModels();
    private val reportsViewModel: ReportsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoanTheme {
                val navigationController = rememberNavController()
                NavHost(
                    navController = navigationController,
                    startDestination = Routes.Login.route
                ) {
                    composable(Routes.Login.route) {
                        LoginScreen(
                            loginViewModel,
                            navigationController
                        )
                    }
                    composable(Routes.Borrower.route) {
                        BorrowerScreen(
                            borrowerViewModel,
                            addLoanViewModel,
                            infoLoanViewModel,
                            reportsViewModel
                        )
                    }
                }

            }
        }

    }

}

