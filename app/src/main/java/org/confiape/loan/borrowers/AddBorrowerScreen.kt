package org.confiape.loan.borrowers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBorrowerScreen(
    show: Boolean,
    onClose: () -> Unit,
    viewModel: BorrowerViewModel = hiltViewModel(),
) {

    if (show) {
        val options = viewModel.tags
        AlertDialog(onDismissRequest = { }, title = { Text(text = "Nuevo Cliente") }, text = {
            Column {
                MultiChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index, count = options.size
                            ),

                            onCheckedChange = {
                                viewModel.onSelectedTags(label)
                            }, checked = viewModel.isSelectedTag(label)
                        ) {
                            Text(
                                text = label.title ?: "",
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = viewModel.name,
                    onValueChange = { viewModel.onChangeName(it) },
                    label = { Text(text = "Nombre") },
                )
                OutlinedTextField(value = viewModel.dni,
                    onValueChange = { viewModel.onChangeDni(it) },
                    label = { Text(text = "Descripción del préstamo") })

            }
        }, confirmButton = {
            Button(onClick = { onClose() }) {
                Text("Crear")
            }
        }, dismissButton = {
            Button(onClick = { onClose() }) {
                Text("Cancelar")
            }
        })
    }

}