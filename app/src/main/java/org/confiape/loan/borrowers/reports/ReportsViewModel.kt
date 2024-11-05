package org.confiape.loan.borrowers.reports

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.confiape.loan.apis.AuthenticateApi
import org.confiape.loan.apis.ReportsApi
import org.confiape.loan.core.repositories.TagRepository
import org.confiape.loan.models.ReportPaymentByDayDto
import org.confiape.loan.models.ReportPaymentsDto
import org.confiape.loan.models.TagDto
import org.confiape.loan.models.UserDto
import java.time.OffsetDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val reportsApi: ReportsApi,
    private val tagRepository: TagRepository,
    private val authenticateApi: AuthenticateApi
) : ViewModel() {

    var paymentByDayDto by mutableStateOf(ReportPaymentByDayDto())
        private set

    var filteredPaymentsByDayDto by mutableStateOf<List<ReportPaymentsDto>>(listOf())
        private set

    var currentOffsetDateTime: OffsetDateTime? = null
        private set


    var totalPayment by mutableDoubleStateOf(0.0)
        private set

    var isLoading by mutableStateOf(false)
        private set
    var tags by mutableStateOf<List<TagDto>>(listOf())
        private set
    var users by mutableStateOf<List<UserDto>>(listOf())
        private set
    private var selectedTags by mutableStateOf<List<UUID>>(listOf())
    private var selectedUsers by mutableStateOf<List<UUID>>(listOf())


    init {
        viewModelScope.launch {
            tags = tagRepository.getTags()
            users = authenticateApi.apiAuthenticateGetAllUsersPost().body()!!
                .filter { it.id != UUID.fromString("6D7EBE1A-607A-4819-0DAA-08DCCB4FB94D") }
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
                    filteredPaymentsByDayDto =
                        filterPayments(response.body()!!.detailsDto ?: listOf())
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

        filteredPaymentsByDayDto = filterPayments(if (selectedTags.isEmpty()) {
            paymentByDayDto.detailsDto ?: listOf()
        } else {
            (paymentByDayDto.detailsDto ?: listOf()).filter { paymentsDto ->
                selectedTags.any { tagId ->
                    paymentsDto.tagId == tagId
                }
            }
        })
        totalPayment = filteredPaymentsByDayDto.sumOf { it.payment ?: 0.0 }
    }

    fun isSelectedTag(id: UUID?): Boolean {
        return id in selectedTags
    }

    private fun filterPayments(list: List<ReportPaymentsDto>): List<ReportPaymentsDto> {
        return list.filter {
            (it.payment ?: 0.0) > 0
        }.sortedBy {
            it.title
        };
    }

    fun onSelectUser(id: UUID) {
        selectedUsers = if (id in selectedUsers) {
            selectedUsers - id
        } else {
            selectedUsers + id
        }

        filteredPaymentsByDayDto = filterPayments(if (selectedUsers.isEmpty()) {
            paymentByDayDto.detailsDto ?: listOf()
        } else {
            (paymentByDayDto.detailsDto ?: listOf()).filter { paymentsDto ->
                selectedUsers.any { id ->
                    paymentsDto.changeBy == id
                }
            }
        })
        totalPayment = filteredPaymentsByDayDto.sumOf { it.payment ?: 0.0 }
    }

    fun isSelectedUser(id: UUID?): Boolean {
        return id in selectedUsers
    }
}
