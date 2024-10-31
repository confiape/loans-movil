@file:OptIn(
    ExperimentalMaterial3Api::class
)

package org.confiape.loan.borrowers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.confiape.loan.borrowers.addborrower.AddBorrowerScreen
import org.confiape.loan.borrowers.loan.add.AddLoanScreen
import org.confiape.loan.borrowers.loan.add.AddLoanViewModel
import org.confiape.loan.borrowers.loan.info.InfoLoanScreen
import org.confiape.loan.borrowers.loan.info.InfoLoanViewModel
import org.confiape.loan.borrowers.reports.ReportsScreen
import org.confiape.loan.borrowers.reports.ReportsViewModel
import org.confiape.loan.borrowers.updateBorrower.UpdateBorrowerScreen
import org.confiape.loan.core.Routes
import org.confiape.loan.models.BasicBorrowerClientWithTagsAndLoans
import java.time.format.DateTimeFormatter


@Composable
fun BorrowerScreen(
    borrowerViewModel: BorrowersViewModel,
    addLoanViewModel: AddLoanViewModel,
    infoLoanViewModel: InfoLoanViewModel,
    reportsViewModel: ReportsViewModel,
) {
    val navigationController = rememberNavController()
    val currentBackStackEntry by navigationController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(topBar = { TopBar(navigationController) }, floatingActionButton = {
        if (currentRoute == Routes.Loans.route) {
            AddBorrower(borrowerViewModel)
        }
    }) {

        NavHost(
            modifier = Modifier.padding(it),
            navController = navigationController,
            startDestination = Routes.Loans.route
        ) {
            composable(Routes.Loans.route) {
                Content(borrowerViewModel, addLoanViewModel, infoLoanViewModel)
            }
            composable(Routes.Reports.route) {
                LaunchedEffect(Unit) { reportsViewModel.update() }
                ReportsScreen(reportsViewModel)
            }
        }
    }
}


@Composable
fun AddDialogs(
    borrowerViewModel: BorrowersViewModel,
    addLoanViewModel: AddLoanViewModel,
    infoLoanViewModel: InfoLoanViewModel
) {
    AddBorrowerScreen(borrowersViewModel = borrowerViewModel)
    AddLoanScreen(loanViewModel = addLoanViewModel, borrowersViewModel = borrowerViewModel)
    InfoLoanScreen(borrowerViewModel, infoLoanViewModel)
    UpdateBorrowerScreen(borrowerViewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    borrowerViewModel: BorrowersViewModel,
    addLoanViewModel: AddLoanViewModel,
    infoLoanViewModel: InfoLoanViewModel
) {
    AddDialogs(borrowerViewModel, addLoanViewModel, infoLoanViewModel)
    Column(
        modifier = Modifier


            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        MultiChoiceSegmentedButtonRow(
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            borrowerViewModel.tags.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index, count = borrowerViewModel.tags.size
                    ),
                    onCheckedChange = {
                        label.title?.let { it1 -> borrowerViewModel.onSelectTag(it1) }
                    },
                    checked = borrowerViewModel.isSelectedTag(label.title),
                ) {
                    Text(
                        text = label.title ?: "", modifier = Modifier.fillMaxWidth(), maxLines = 1
                    )
                }
            }
        }
        TextField(
            value = borrowerViewModel.searchText,
            onValueChange = { newText -> borrowerViewModel.onSearchTextChange(newText) },
            placeholder = {
                Text(text = "Buscar", color = MaterialTheme.colorScheme.onSurfaceVariant)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        ClientList(borrowerViewModel, addLoanViewModel)

    }
}


@Composable
fun TopBar(navigationController: NavHostController) {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("Prestamos", "Reporte del dia")

    TopAppBar(colors = topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.primary,
    ), title = {
        PrimaryTabRow(
            selectedTabIndex = state, modifier = Modifier.fillMaxWidth()
        ) {
            titles.forEachIndexed { index, title ->
                Tab(selected = state == index, onClick = {
                    state = index
                    if (index == 0) {
                        navigationController.navigate(Routes.Loans.route)
                    }
                    if (index == 1) {
                        navigationController.navigate(Routes.Reports.route)
                    }
                }, text = {
                    Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                })
            }
        }
    })
}

@Composable
fun BottomBar() {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
//        modifier = Modifier.height(64.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Bottom app bar",
        )
    }
}

@Composable
fun AddBorrower(borrowerViewModel: BorrowersViewModel) {

    FloatingActionButton(
        onClick = { borrowerViewModel.activateAddBorrowerScreen(true) },
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add")
    }
}

@Composable
fun ClientList(borrowerViewModel: BorrowersViewModel, addLoanViewModel: AddLoanViewModel) {


    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = borrowerViewModel.filterBorrowers, itemContent = {
            BorrowerListItem(borrower = it, borrowerViewModel, addLoanViewModel)
        })
    }
}

@Composable
fun BorrowerListItem(
    borrower: BasicBorrowerClientWithTagsAndLoans,
    borrowerViewModel: BorrowersViewModel,
    addLoanViewModel: AddLoanViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            Modifier.fillMaxWidth()
            , horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = borrower.name ?: "",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = borrower.tags!!.firstOrNull() ?: "",
                modifier = Modifier.align(Alignment.CenterVertically),
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(
                onClick = {
                    borrowerViewModel.selectBorrower(borrower)
                    borrowerViewModel.activateUpdateBorrowerScreen(true)

                }, modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            LazyRow(
                modifier = Modifier.weight(1f)
            ) {
                items(items = borrower.loans!!, itemContent = {
                    val amount = it.amount!!.plus(it.amount * it.interest!! / 100)
                    TextButton(onClick = {
                        borrowerViewModel.selectBorrower(borrower)
                        borrowerViewModel.selectLoan(it)
                    }) {
                        Column {
                            Text(text = "S/. $amount")
                            Text(
                                text = DateTimeFormatter.ofPattern("dd MMMM").format(it.dateTime),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                })
            }

            OutlinedButton(modifier = Modifier.padding(start = 8.dp), onClick = {
                addLoanViewModel.borrower = borrower
                borrowerViewModel.activateAddLoanScreen(true)
            }) {
                Text(text = "+")
            }
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))


    }
}