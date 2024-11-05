package org.confiape.loan.borrowers.updateBorrower

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import org.confiape.loan.borrowers.BorrowersViewModel
import org.confiape.loan.borrowers.addborrower.UpdateBorrowerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateBorrowerScreen(
    borrowersViewModel: BorrowersViewModel,
    viewModel: UpdateBorrowerViewModel = hiltViewModel(),
) {
    if (borrowersViewModel.showUpdateBorrowerScreen) {
        val options = viewModel.tags
        viewModel.updateModel(
            id = borrowersViewModel.selectedBorrower?.id!!,
            borrowersViewModel.selectedBorrower?.tags ?: listOf(),
            borrowersViewModel.selectedBorrower?.name ?: "",
            borrowersViewModel.selectedBorrower?.dni ?: "",
            borrowersViewModel.selectedBorrower?.title ?: ""
        )

        val selectedFile = remember { mutableStateOf<Uri?>(null) }
        val filePickerLauncher =
            rememberLauncherForActivityResult(contract = androidx.activity.result.contract.ActivityResultContracts.OpenDocument(),
                onResult = { uri ->
                    if (uri != null) {
                        selectedFile.value = uri
                        viewModel.uploadFile(uri)

                    }
                })
        AlertDialog(onDismissRequest = { }, title = { Text(text = "Actualizar Cliente") }, text = {
            Column {
                MultiChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index, count = options.size
                            ), onCheckedChange = {
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
                    maxLines = 1,
                    singleLine = true,
                    label = { Text(text = "Nombre") },
                )
                OutlinedTextField(
                    value = viewModel.alias,
                    onValueChange = { viewModel.onChangeAlias(it) },
                    maxLines = 1,
                    singleLine = true,
                    label = { Text(text = "Alias") },
                )
                OutlinedTextField(value = viewModel.dni,
                    maxLines = 1,
                    singleLine = true,
                    onValueChange = { viewModel.onChangeDni(it) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = "DNI") })
//                Button(onClick = {
//                    filePickerLauncher.launch(arrayOf("*/*"))
//                }, modifier = Modifier.fillMaxWidth()) {
//                    Text(text = selectedFile.value?.lastPathSegment ?: "Seleccionar archivo")
//                }

            }
        }, confirmButton = {
            Button(onClick = {
                viewModel.saveBorrower(borrowersViewModel)
                borrowersViewModel.activateUpdateBorrowerScreen(false)
            }) {
                Text("Crear")
            }
        }, dismissButton = {
            TextButton(onClick = {
                borrowersViewModel.activateUpdateBorrowerScreen(false)
            }) {
                Text("Cancelar")
            }
        })
    }

}