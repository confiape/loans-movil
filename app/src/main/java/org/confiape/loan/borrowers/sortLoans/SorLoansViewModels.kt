package org.confiape.loan.borrowers.sortLoans

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.fastJoinToString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.confiape.loan.apis.LoanApi
import org.confiape.loan.apis.TagApi
import org.confiape.loan.models.LoanAndDate
import org.confiape.loan.models.TagDto
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SorLoansViewModels @Inject constructor(
    private val loanApi: LoanApi,
    private val tagApi: TagApi,
) : ViewModel() {
    var filterGroupedLoans by mutableStateOf<Map<LocalDate, List<LoanAndDate>>>(mapOf())
        private set

    var groupedLoans: Map<LocalDate, List<LoanAndDate>> = mapOf();
    private var selectedTags by mutableStateOf<List<String>>(listOf())
    var tags by mutableStateOf<List<TagDto>>(listOf())
        private set

    init {
        viewModelScope.launch {
            tags = tagApi.apiTagGet().body()!!.result!!
            Log.i("warren", tags.fastJoinToString())
            val response =
                loanApi.apiLoanGetLoanGroupByDateGet().body()!!.sortedByDescending { it.dateTime }
                    .groupBy {
                        it.dateTime!!.toLocalDate()
                    }
            filterGroupedLoans = response.mapValues { it.value.toList() }.toMap()
            groupedLoans = response.mapValues { it.value.toList() }.toMap()
        }

    }

    fun onSelectTag(id: String) {
        selectedTags = if (id in selectedTags) {
            selectedTags - id
        } else {
            selectedTags + id
        }

        filterGroupedLoans = if (selectedTags.isEmpty()) {
            groupedLoans
        } else {
            groupedLoans.mapValues {
                it.value.filter { item ->
                    selectedTags.contains(item.tag)
                }
            }.filter { it.value.isNotEmpty() }
        }
    }

    fun moveItem(
        loans: List<LoanAndDate>,
        loan: LoanAndDate,
        direction: Int,
    ) {
        val currentIndex = loans.indexOf(loan)
        val newIndex = currentIndex + direction

        if (newIndex in loans.indices) {
            val mutableList = loans.toMutableList()
            mutableList.removeAt(currentIndex)
            mutableList.add(newIndex, loan)

            filterGroupedLoans = filterGroupedLoans.toMutableMap().apply {
                put(loan.dateTime!!.toLocalDate(), mutableList)
            }
        }
    }

    fun update(loansForDate: List<LoanAndDate>) {
        viewModelScope.launch {
            val response = loanApi.apiLoanUpdateHoursPost(loansForDate.reversed())

        }
    }

    fun isSelectedTag(id: String?): Boolean {
        return id in selectedTags
    }
}
