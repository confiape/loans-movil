package org.confiape.loan.borrowers.sorReports

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.confiape.loan.models.LoanAndDate
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Composable
fun SortLoans(sortLoansViewModel: SorLoansViewModels= hiltViewModel()) {
    if(sortLoansViewModel.loanAndDates.isNotEmpty())
    GroupedLoansScreen(
        sortLoansViewModel.loanAndDates
    )
}


@Composable
fun GroupedLoansScreen(loans: List<LoanAndDate>) {
    val groupedLoans =
        remember { mutableStateOf(loans.groupBy { it.dateTime!!.toLocalDate() }) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        groupedLoans.value.forEach { (date, loansForDate) ->
            item {
                Text(
                    text = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(loansForDate, key = { it.loanId!! }) { loan ->
                LoanItem(
                    loan = loan,
                    onMoveUp = {
                        moveItem(loansForDate, loan, -1, groupedLoans)
                    },
                    onMoveDown = {
                        moveItem(loansForDate, loan, 1, groupedLoans)
                    }
                )
            }
        }
    }
}

@Composable
fun LoanItem(loan: LoanAndDate, onMoveUp: () -> Unit, onMoveDown: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = loan.name!!,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = "Amount: ${loan.name}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )
            }

            IconButton(onClick = onMoveUp) {
                Icon(
                    imageVector = Icons.Default.ArrowUpward,
                    contentDescription = "Move Up",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = onMoveDown) {
                Icon(
                    imageVector = Icons.Default.ArrowDownward,
                    contentDescription = "Move Down",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

fun moveItem(
    loans: List<LoanAndDate>,
    loan: LoanAndDate,
    direction: Int,
    groupedLoans: MutableState<Map<LocalDate, List<LoanAndDate>>>
) {
    val currentIndex = loans.indexOf(loan)
    val newIndex = currentIndex + direction

    if (newIndex in loans.indices) {
        val mutableList = loans.toMutableList()
        mutableList.removeAt(currentIndex)
        mutableList.add(newIndex, loan)

        groupedLoans.value = groupedLoans.value.toMutableMap().apply {
            put(loan.dateTime!!.toLocalDate(), mutableList)
        }
    }
}