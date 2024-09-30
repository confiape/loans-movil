package org.confiape.loan.core

sealed class Routes(val route: String) {
    data object Login : Routes("login");
    data object Borrower : Routes("borrower");

    data object Loans : Routes("Loans");
    data object Reports : Routes("Reports");

}