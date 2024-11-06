package org.confiape.loan.borrowers.reports

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun ReportsScreen(reportsViewModel: ReportsViewModel) {
    val context = LocalContext.current

    if (reportsViewModel.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Text(SharedService.getUserName(context))
                Button(
                    onClick = { printReport(context, reportsViewModel) },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(text = "Imprimir Reporte")
                }

                DatePickerDocked(
                    defaultDate = reportsViewModel.currentOffsetDateTime ?: OffsetDateTime.now(),
                    onChangeDate = {
                        reportsViewModel.onChangeCurrentDay(it)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            MultiChoiceSegmentedButtonRow(
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                reportsViewModel.tags.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index, count = reportsViewModel.tags.size
                        ),
                        onCheckedChange = {
                            label.id?.let { it1 -> reportsViewModel.onSelectTag(it1) }
                        },
                        checked = reportsViewModel.isSelectedTag(label.id),
                    ) {
                        Text(
                            text = label.title ?: "",
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1
                        )
                    }
                }
            }
            MultiChoiceSegmentedButtonRow(
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                reportsViewModel.users.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index, count = reportsViewModel.users.size
                        ),
                        onCheckedChange = {
                            label.id?.let { it1 -> reportsViewModel.onSelectUser(it1) }
                        },
                        checked = reportsViewModel.isSelectedUser(label.id),
                    ) {
                        Text(
                            text = label.name!!.first().uppercaseChar()
                                .toString() + label.name.substring(1, 3),
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1
                        )
                    }
                }
                IconButton(
                    onClick = { reportsViewModel.toggleSortOrder() },
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Icon(
                        imageVector = if (reportsViewModel.currentSortOrder == SortOrder.BY_DATE) Icons.Default.DateRange
                        else if (reportsViewModel.currentSortOrder == SortOrder.BY_NAME) Icons.Default.SortByAlpha
                        else Icons.Default.AttachMoney,
                        contentDescription = "Ordenar lista",
                        tint = MaterialTheme.colorScheme.primary
                    )

                }
                IconButton(
                    onClick = { printReportFromList(context,reportsViewModel.filteredPaymentsByDayDto,reportsViewModel.tags) },
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Print,
                        contentDescription = "Ordenar lista",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = reportsViewModel.filteredPaymentsByDayDto, itemContent = {
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            text = it.title ?: "",
                            modifier = Modifier.weight(0.7f),
                            color = if (it.isYape == true) Color(0xFF5f0772) else Color.Unspecified
                        )
                        Text(
                            text = "S./",
                            modifier = Modifier.weight(0.1f),
                            color = if (it.isYape == true) Color(0xFF5f0772) else Color.Unspecified
                        )
                        Text(
                            text = String.format("%,.2f", it.payment),
                            modifier = Modifier.weight(0.2f),
                            textAlign = TextAlign.End,
                            color = if (it.isYape == true) Color(0xFF5f0772) else Color.Unspecified
                        )
                    }

                })
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total pagado:",
                            modifier = Modifier.weight(0.6f),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "S./",
                            modifier = Modifier.weight(0.1f),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = String.format("%,.2f", reportsViewModel.totalPayment),
                            modifier = Modifier.weight(0.3f),
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(
    onChangeDate: (OffsetDateTime) -> Unit,
    defaultDate: OffsetDateTime = OffsetDateTime.now(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    val defaultMillis = defaultDate.toInstant().toEpochMilli()
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = defaultMillis)

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let {
            val selectedOffsetDateTime = convertMillisToDateTimeOffset(it)
            onChangeDate(selectedOffsetDateTime)
            showDatePicker = false
        }
    }

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("Elige el día") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select date")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            Popup(onDismissRequest = { showDatePicker = false }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState, showModeToggle = true
                    )
                }
            }
        }
    }
}


fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale("es", "PE"))
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(Date(millis))
}

fun convertMillisToDateTimeOffset(millis: Long): OffsetDateTime {
    val instant = Instant.ofEpochMilli(millis)
    return OffsetDateTime.ofInstant(
        instant, ZoneOffset.UTC
    )
}


