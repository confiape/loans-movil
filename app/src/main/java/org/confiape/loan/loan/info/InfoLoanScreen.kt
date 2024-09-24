package org.confiape.loan.loan.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import org.confiape.loan.borrowers.BorrowersViewModel

@Composable
fun InfoLoanScreen(borrowerViewModel: BorrowersViewModel) {

    if (borrowerViewModel.showInfoLoanScreen) {
        AlertDialog(onDismissRequest = { }, title = {
            Text(text = "${borrowerViewModel.selectedBorrower!!.name}")
            Text(text = "${borrowerViewModel.selectedLoan!!.dateTime}")
            Text(text = "${borrowerViewModel.selectedLoan!!.totalPayment}")
        }, text = {
            Column {

            }
        }, confirmButton = {
            Button(onClick = {
                loanViewModel.insert(borrowersViewModel)
                onClose()
            }) {
                Text("Crear")
            }
        }, dismissButton = {
            Button(onClick = { onClose() }) {
                Text("Cancelar")
            }
        })
    }

}