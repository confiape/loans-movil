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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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

@SuppressLint("DefaultLocale")
@Composable
fun ReportsScreen(reportsViewModel: ReportsViewModel) {
    if (reportsViewModel.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column {
            DatePickerDocked(defaultDate = reportsViewModel.currentOffsetDateTime
                ?: OffsetDateTime.now(),
                onChangeDate = {
                    reportsViewModel.onChangeCurrentDay(it)
                })
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = reportsViewModel.paymentByDayDto.detailsDto ?: listOf(),
                    itemContent = {
                        Row(Modifier.fillMaxWidth()) {
                            Text(text = it.name ?: "d", modifier = Modifier.weight(0.7f))
                            Text(text = "S./", modifier = Modifier.weight(0.1f))
                            Text(
                                text = String.format("%,.2f", it.payment),
                                modifier = Modifier.weight(0.2f),
                                textAlign = TextAlign.End
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

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(value = selectedDate,
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
