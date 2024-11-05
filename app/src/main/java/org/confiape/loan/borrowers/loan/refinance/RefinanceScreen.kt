package org.confiape.loan.borrowers.loan.refinance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import org.confiape.loan.borrowers.BorrowersViewModel
import org.confiape.loan.borrowers.loan.add.SelectTypeOfLoan
import kotlin.math.ceil

@Composable
fun RefinanceScreen(
    borrowerViewModel: BorrowersViewModel, refinanceViewModel: RefinanceViewModel
) {
    if (borrowerViewModel.selectedLoan != null &&
        borrowerViewModel.selectedBorrower != null &&
        borrowerViewModel.showRefinanceScreen
    ) {
        AlertDialog(onDismissRequest = { },
            title = { Text(text = "Refinanciar Prestamo para \"${borrowerViewModel.selectedBorrower!!.name}\"") },
            text = {
                Column {
                    Row {
                        Text("Deuda a faltante   ")
                        Text(
                            "S/. ${
                                ceil(borrowerViewModel.selectedLoan!!.amount!! + borrowerViewModel.selectedLoan!!.amount!! * borrowerViewModel.selectedLoan!!.interest!! / 100 - borrowerViewModel.selectedLoan!!.totalPayment!!)
                            }", fontSize = 20.sp
                        )
                    }

                    OutlinedTextField(
                        value = refinanceViewModel.amount,
                        onValueChange = { refinanceViewModel.onChangeAmount(it) },
                        label = { Text(text = "Aumentar Monto?") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = refinanceViewModel.interest,
                        onValueChange = { refinanceViewModel.onChangeInterest(it) },
                        label = { Text(text = "Interes") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = refinanceViewModel.numberDate,
                        onValueChange = { refinanceViewModel.onChangeNumberDate(it) },
                        label = { Text(text = "Numero de dias") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    SelectTypeOfLoan(
                        refinanceViewModel.selectedTypeLoan,
                        refinanceViewModel::onOptionSelected
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    refinanceViewModel.insert(borrowerViewModel)
                    borrowerViewModel.activateShowRefinanceScreen(false)
                }) {
                    Text("Crear")
                }
            },
            dismissButton = {
                Button(onClick = {
                    borrowerViewModel.activateShowRefinanceScreen(false)
                }) {
                    Text("Cancelar")
                }
            })
    }

}