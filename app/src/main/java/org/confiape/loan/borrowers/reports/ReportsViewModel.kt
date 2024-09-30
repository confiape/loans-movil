package org.confiape.loan.borrowers.reports

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.confiape.loan.apis.ReportsApi
import org.confiape.loan.models.ReportPaymentByDayDto
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val reportsApi: ReportsApi
) : ViewModel() {

    var paymentByDayDto by mutableStateOf(ReportPaymentByDayDto())
        private set
    var isLoading by mutableStateOf(false)
        private set

    fun update() {
        viewModelScope.launch {
            isLoading = true
            paymentByDayDto = reportsApi.apiReportsReportPaymentByDayGet().body()!!
            isLoading = false
        }
    }


}