package org.confiape.loan.borrowers.sorReports

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.confiape.loan.apis.LoanApi
import org.confiape.loan.models.LoanAndDate
import javax.inject.Inject

@HiltViewModel
class SorLoansViewModels @Inject constructor(
    private val loanApi: LoanApi
) : ViewModel() {
    var loanAndDates by mutableStateOf<List<LoanAndDate>>(listOf())
        private set

    init {
        viewModelScope.launch {
            loanAndDates = loanApi.apiLoanGetLoanGroupByDateGet().body()!!

        }

    }
}

g