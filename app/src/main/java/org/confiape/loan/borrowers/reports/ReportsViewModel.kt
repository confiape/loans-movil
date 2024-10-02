package org.confiape.loan.borrowers.reports

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.confiape.loan.apis.ReportsApi
import org.confiape.loan.models.ReportPaymentByDayDto
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val reportsApi: ReportsApi
) : ViewModel() {

    var currentOffsetDateTime: OffsetDateTime? = null
        private set

    var paymentByDayDto by mutableStateOf(ReportPaymentByDayDto())
        private set

    var totalPayment by mutableStateOf(0.0)
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun update(defaultDate: OffsetDateTime = OffsetDateTime.now()) {
        if (currentOffsetDateTime != defaultDate) {
            currentOffsetDateTime = defaultDate
            viewModelScope.launch {
                isLoading = true
                try {
                    val response = reportsApi.apiReportsReportPaymentByDayGet(defaultDate)
                    paymentByDayDto = response.body() ?: ReportPaymentByDayDto()
                    totalPayment=response.body()!!.detailsDto!!.sumOf { e ->
                        e.payment ?: 0.0
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    isLoading = false
                }
            }
        }
    }

    // Función que se llama cuando se selecciona una nueva fecha
    fun onChangeCurrentDay(offsetDateTime: OffsetDateTime) {
        update(offsetDateTime)
    }
}
