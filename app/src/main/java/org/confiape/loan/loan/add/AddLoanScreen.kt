package org.confiape.loan.loan.add

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
fun AddLoanScreen(
    show: Boolean, onClose: () -> Unit, loanViewModel: AddLoanViewModel,
    borrowersViewModel: BorrowersViewModel
) {
    if (show) {
        AlertDialog(onDismissRequest = { },
            title = { Text(text = "Nuevo Prestamo para \"${loanViewModel.borrower.name}\"") },
            text = {
                Column {
                    OutlinedTextField(
                        value = loanViewModel.amount,
                        onValueChange = { loanViewModel.onChangeAmount(it) },
                        label = { Text(text = "Monto") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = loanViewModel.interest,
                        onValueChange = {loanViewModel.onChangeInterest(it)},
                        label = { Text(text = "Interes") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = loanViewModel.numberDate,
                        onValueChange = {loanViewModel.onChangeNumberDate(it)},
                        label = { Text(text = "Numero de dias") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    loanViewModel.insert(borrowersViewModel)
                    onClose()
                }) {
                    Text("Crear")
                }
            },
            dismissButton = {
                Button(onClick = { onClose() }) {
                    Text("Cancelar")
                }
            })
    }

}