package org.confiape.loan.loan

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoanScreen(show: Boolean,onClose: () -> Unit, loanViewModel: LoanViewModel = hiltViewModel()) {
    if (show) {
        AlertDialog(onDismissRequest = { },
            title = { Text(text = "Informacion del prestamo") },
            text = {
                Column {
                    OutlinedTextField(
                        value = loanViewModel.complete.name ?: "",

                        onValueChange = {},
                        label = { Text(text = "Nombre") },
                    )

                }
            },
            confirmButton = {
                Button(onClick = {onClose() }) {
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