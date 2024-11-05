package org.confiape.loan.borrowers.loan.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.confiape.loan.borrowers.BorrowersViewModel


@Composable
fun AddLoanScreen(
    addLoanViewModel: AddLoanViewModel,
    borrowersViewModel: BorrowersViewModel,
) {
    if (borrowersViewModel.showLoanScreen) {
        AlertDialog(onDismissRequest = { },
            title = { Text(text = "Nuevo Prestamo para \"${borrowersViewModel.selectedBorrower!!.name}\"") },
            text = {
                Column {
                    OutlinedTextField(
                        value = addLoanViewModel.amount,
                        onValueChange = { addLoanViewModel.onChangeAmount(it) },
                        label = { Text(text = "Monto") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = addLoanViewModel.interest,
                        onValueChange = { addLoanViewModel.onChangeInterest(it) },
                        label = { Text(text = "Interes") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = addLoanViewModel.numberDate,
                        onValueChange = { addLoanViewModel.onChangeNumberDate(it) },
                        label = { Text(text = "Numero de dias") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    SelectTypeOfLoan(
                        addLoanViewModel.selectedTypeLoan, addLoanViewModel::onOptionSelected
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    addLoanViewModel.insert(borrowersViewModel)
                    borrowersViewModel.activateAddLoanScreen(false)
                }) {
                    Text("Crear")
                }
            },
            dismissButton = {
                Button(onClick = {
                    borrowersViewModel.activateAddLoanScreen(false)
                }) {
                    Text("Cancelar")
                }
            })
    }

}

@Composable
fun DropdownMenuSample() {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
    ) {

        IconButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(Icons.Outlined.Settings, contentDescription = "Localized description")
                Text(text = "Avanzado")
            }
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {

//            SelectTypeOfLoan(loanViewModel)
//            DaySelection()
        }
    }
}


@Composable
fun SelectTypeOfLoan(selectedTypeLoan: String, onOptionSelect: (String) -> Unit) {
    val radioOptions = listOf("Diario", "Semanal", "Mensual")

    Column(Modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedTypeLoan),
                        onClick = { onOptionSelect(text) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedTypeLoan),
                    onClick = null // null recommended for accessibility with screenreaders
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun DaySelection() {
    val daysOfWeek =
        listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
    val selectedDays = remember {
        mutableStateListOf(
            "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
        )
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Selecciona los días de la semana")

        Spacer(modifier = Modifier.height(8.dp))

        daysOfWeek.forEach { day ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = selectedDays.contains(day), onCheckedChange = { isChecked ->
                    if (isChecked) {
                        selectedDays.add(day)
                    } else {
                        selectedDays.remove(day)
                    }
                })
                Text(text = day)
            }
        }
    }
}

