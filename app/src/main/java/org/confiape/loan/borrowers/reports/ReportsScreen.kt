package org.confiape.loan.borrowers.reports

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ReportsScreen(reportsViewModel: ReportsViewModel) {

    if(reportsViewModel.isLoading){
        CircularProgressIndicator()
    }else{
        Text("wa")
    }
}