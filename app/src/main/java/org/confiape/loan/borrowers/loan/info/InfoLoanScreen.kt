package org.confiape.loan.borrowers.loan.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.confiape.loan.borrowers.BorrowersViewModel
import org.confiape.loan.models.SimpleLoanDtoAndPayments
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun InfoLoanScreen(borrowerViewModel: BorrowersViewModel, infoLoanViewModel: InfoLoanViewModel) {

    if (borrowerViewModel.selectedLoan != null) {
        var checked by remember { mutableStateOf(false) }


        AlertDialog(onDismissRequest = {
            borrowerViewModel.selectLoan(null)

        }, title = {
            Text(text = "${borrowerViewModel.selectedBorrower!!.name} S./${borrowerViewModel.selectedLoan!!.totalAmount}")
        }, text = {
            Column {
                Row {
                    Text(
                        modifier = Modifier.weight(1f), text = "Fecha:"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = DateTimeFormatter.ofPattern("dd MMMM")
                            .format(borrowerViewModel.selectedLoan!!.dateTime)
                    )
                }
                Row {
                    Text(modifier = Modifier.weight(1f), text = "Total:")
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "S./ ${borrowerViewModel.selectedLoan!!.totalAmount!!}"
                    )
                }
                Row {
                    Text(modifier = Modifier.weight(1f), text = "Falta Pagar:")
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "S./ ${borrowerViewModel.selectedLoan!!.totalAmount!! - borrowerViewModel.selectedLoan!!.totalPayment!!}"
                    )
                }
                Row {
                    Text(modifier = Modifier.weight(1f), text = "Interes:")
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "S./ ${borrowerViewModel.selectedLoan!!.interest}"
                    )
                }
                Text(text = "Pagos")
                Row {
                    OutlinedTextField(modifier = Modifier.weight(1.5f),
                        value = infoLoanViewModel.amountToPAy,
                        label = { Text(text = "Monto") },
                        singleLine = true,
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { infoLoanViewModel.OnChangeAmountToPay(it) })
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(
                            "Yape",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Checkbox(
                            checked = checked,
                            onCheckedChange = { checked = it }
                        )
                    }
                    Button(modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                        enabled = !infoLoanViewModel.isDisablePayButton,
                        onClick = {

                            val pay = borrowerViewModel.selectedLoan!!.payments!!.find {
                                it.dateTime!!.toLocalDate().isEqual(LocalDate.now())
                            }
                            if (pay == null) {
                                infoLoanViewModel.pay(
                                    borrowerViewModel.selectedLoan!!.id!!,
                                    borrowerViewModel,
                                    checked
                                )
                            } else {
                                infoLoanViewModel.editPay(
                                    pay.id,
                                    borrowerViewModel,
                                    checked
                                )
                            }

                        }) {
                        if (borrowerViewModel.selectedLoan!!.payments!!.any {
                                it.dateTime!!.toLocalDate().isEqual(LocalDate.now())
                            }) {
                            Text("Editar")
                        } else {
                            Text("Pagar")
                        }

                    }
                }
                LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
                    items(items = (borrowerViewModel.selectedLoan ?: (SimpleLoanDtoAndPayments(
                        payments = listOf()
                    ))).payments!!, itemContent = {
                        Row {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = DateTimeFormatter.ofPattern("dd MMMM").format(it.dateTime)
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "S./ ${it.amount.toString()}"
                            )

                        }
                        HorizontalDivider()
                    })
                }
            }

        }, confirmButton = {}, dismissButton = {
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                borrowerViewModel.selectLoan(null)
            }) {
                Text("Cancelar")
            }
        })
    }
}

