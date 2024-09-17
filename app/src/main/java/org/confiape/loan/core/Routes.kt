package org.confiape.loan.core

sealed class Routes(val route: String) {
    object Login : Routes("login");
    object Borrower : Routes("borrower");
}