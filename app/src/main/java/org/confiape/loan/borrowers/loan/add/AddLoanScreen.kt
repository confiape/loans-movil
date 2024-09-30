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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
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
 loanViewModel: AddLoanViewModel,
    borrowersViewModel: BorrowersViewModel,
) {
    if (borrowersViewModel.showLoanScreen) {
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
                        onValueChange = { loanViewModel.onChangeInterest(it) },
                        label = { Text(text = "Interes") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = loanViewModel.numberDate,
                        onValueChange = { loanViewModel.onChangeNumberDate(it) },
                        label = { Text(text = "Numero de dias") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    SelectTypeOfLoan(loanViewModel)
                }
            },
            confirmButton = {
                Button(onClick = {
                    loanViewModel.insert(borrowersViewModel)
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
fun SelectTypeOfLoan(loanViewModel: AddLoanViewModel) {
    val radioOptions = listOf("Diario", "Semanal", "Mensual")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    Column(Modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            Row(
                Modifier.fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == loanViewModel.selectedTypeLoan),
                        onClick = { loanViewModel.onOptionSelected(text) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == loanViewModel.selectedTypeLoan),
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
            "Lunes",
            "Martes",
            "Miércoles",
            "Jueves",
            "Viernes",
            "Sábado"
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
                Checkbox(
                    checked = selectedDays.contains(day),
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            selectedDays.add(day)
                        } else {
                            selectedDays.remove(day)
                        }
                    }
                )
                Text(text = day)
            }
        }
    }
}

