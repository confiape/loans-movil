package org.confiape.loan.borrowers.sortLoans

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.confiape.loan.models.LoanAndDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortLoans(
    sortLoansViewModel: SorLoansViewModels = hiltViewModel(),
) {
    if (sortLoansViewModel.filterGroupedLoans.isNotEmpty())


        Column {
            MultiChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                sortLoansViewModel.tags.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index, count = sortLoansViewModel.tags.size
                        ),
                        onCheckedChange = {
                            label.title?.let { it1 -> sortLoansViewModel.onSelectTag(it1) }
                        },
                        checked = sortLoansViewModel.isSelectedTag(label.title),
                    ) {
                        Text(
                            text = label.title ?: "", modifier = Modifier.fillMaxWidth(), maxLines = 1
                        )
                    }
                }
            }
            GroupedLoansScreen(
                sortLoansViewModel
            )
        }

}


@Composable
fun GroupedLoansScreen(sortLoansViewModel: SorLoansViewModels) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {

        sortLoansViewModel.filterGroupedLoans.forEach { (date, loansForDate) ->
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 8.dp)
                    )
                    Button(onClick = { sortLoansViewModel.update(loansForDate) }, enabled = true) {
                        Text(text = "aceptar")
                    }
                }

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(loansForDate, key = { it.loanId!! }) { loan ->
                LoanItem(loan = loan, onMoveUp = {
                    sortLoansViewModel.moveItem(loansForDate, loan, -1)
                }, onMoveDown = {
                    sortLoansViewModel.moveItem(loansForDate, loan, 1)
                })
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
                    text = loan.title ?: "", style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = "Monto total: S./ ${(loan.amount!!)}",
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

