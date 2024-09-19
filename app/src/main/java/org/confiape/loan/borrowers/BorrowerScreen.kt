@file:OptIn(
    ExperimentalMaterial3Api::class
)

package org.confiape.loan.borrowers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.confiape.loan.models.BasicBorrowerClientWithTagsAndLoans
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun BorrowerScreen(borrowerViewModel: BorrowerViewModel) {
    val options = borrowerViewModel.tags

    Scaffold(topBar = {
        TopAppBar(colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ), title = {
            Text("Prestamos")
        })
    }, bottomBar = { BottomBar() },
        floatingActionButton = { FloatingActionButtonToAddBorrower() }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {


            MultiChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                options.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index, count = options.size
                        ),

                        onCheckedChange = {
                            label.title?.let { it1 -> borrowerViewModel.onSelectTag(it1) }
                        },
                        checked = borrowerViewModel.isSelectedTag(label.title)
                    ) {
                        Text(
                            text = label.title ?: "",
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1
                        )
                    }
                }
            }
            TextField(
                value = borrowerViewModel.searchText,
                onValueChange = { newText -> borrowerViewModel.onSearchTextChange(newText) },
                placeholder = {
                    Text(text = "Buscar")
                },
                modifier = Modifier.fillMaxWidth()
            )
            ClientList(borrowerViewModel)

        }
    }
}

@Composable
fun BottomBar() {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Bottom app bar",
        )
    }
}

@Composable
fun FloatingActionButtonToAddBorrower() {
    FloatingActionButton(onClick = { }) {
        Icon(Icons.Default.Add, contentDescription = "Add")
    }
}

@Composable
fun ClientList(borrowerViewModel: BorrowerViewModel) {


    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
        items(items = borrowerViewModel.filterBorrowers, itemContent = {
            BorrowerListItem(borrower = it)
        })
    }
}

@Composable
fun BorrowerListItem(borrower: BasicBorrowerClientWithTagsAndLoans) {
    Column {
        Row(Modifier.fillMaxWidth()) {
            Text(text = borrower.name ?: "", modifier = Modifier.weight(1f))
            Text(
                text = borrower.tags!!.firstOrNull() ?: "",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            borrower.loans!!.forEachIndexed { _, loan ->
                val amount = loan.amount!!.plus(loan.amount * loan.interest!! / 100)
                TextButton(onClick = { /*TODO*/ }) {
                    Column {
                        Text(text = "S/. $amount")
                        Text(text = DateTimeFormatter.ofPattern("dd MMMM").format(loan.dateTime))
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "+")
            }
        }
        HorizontalDivider()

    }
}