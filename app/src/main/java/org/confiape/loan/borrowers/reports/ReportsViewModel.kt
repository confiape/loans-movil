package org.confiape.loan.borrowers.reports

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.confiape.loan.apis.ReportsApi
import org.confiape.loan.core.repositories.TagRepository
import org.confiape.loan.models.ReportPaymentByDayDto
import org.confiape.loan.models.ReportPaymentsDto
import org.confiape.loan.models.TagDto
import java.time.OffsetDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val reportsApi: ReportsApi,
    private val tagRepository: TagRepository
) : ViewModel() {

    var paymentByDayDto by mutableStateOf(ReportPaymentByDayDto())
        private set

    var filteredPaymentsByDayDto by mutableStateOf<List<ReportPaymentsDto>>(listOf())
        private set

    var currentOffsetDateTime: OffsetDateTime? = null
        private set


    var totalPayment by mutableStateOf(0.0)
        private set

    var isLoading by mutableStateOf(false)
        private set
    var tags by mutableStateOf<List<TagDto>>(listOf())
        private set

    private var selectedTags by mutableStateOf<List<UUID>>(listOf())


    init {
        viewModelScope.launch {
            tags = tagRepository.getTags()
        }
    }

    fun update(defaultDate: OffsetDateTime = OffsetDateTime.now()) {

        if (currentOffsetDateTime != defaultDate) {
            currentOffsetDateTime = defaultDate
            viewModelScope.launch {
                isLoading = true
                try {
                    reportsApi.apiReportsGetReportsByDayGet().body()

                    val response = reportsApi.apiReportsReportPaymentByDayGet(defaultDate)
                    paymentByDayDto = response.body() ?: ReportPaymentByDayDto()
                    totalPayment = response.body()!!.detailsDto!!.sumOf { e ->
                        e.payment ?: 0.0
                    }
                    filteredPaymentsByDayDto = response.body()!!.detailsDto ?: listOf()
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    isLoading = false
                }
            }
        }
    }


    fun onChangeCurrentDay(offsetDateTime: OffsetDateTime) {
        update(offsetDateTime)
    }

    fun onSelectTag(id: UUID) {
        selectedTags = if (id in selectedTags) {
            selectedTags - id
        } else {
            selectedTags + id
        }

        filteredPaymentsByDayDto = if (selectedTags.isEmpty()) {
            paymentByDayDto.detailsDto ?: listOf()
        } else {
            (paymentByDayDto.detailsDto ?: listOf()).filter { paymentsDto ->
                selectedTags.any { tagId ->
                    paymentsDto.tagId == tagId
                }
            }
        }
        totalPayment = filteredPaymentsByDayDto.sumOf { it.payment ?: 0.0 }
    }

    fun isSelectedTag(id: UUID?): Boolean {
        return id in selectedTags
    }
}
