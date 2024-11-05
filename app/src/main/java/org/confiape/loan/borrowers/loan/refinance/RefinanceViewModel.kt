package org.confiape.loan.borrowers.loan.refinance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.confiape.loan.apis.LoanApi
import org.confiape.loan.borrowers.BorrowersViewModel
import org.confiape.loan.models.BasicBorrowerClientWithTagsAndLoans
import org.confiape.loan.models.LoanType
import org.confiape.loan.models.RefinanceDto
import javax.inject.Inject

@HiltViewModel
class RefinanceViewModel @Inject constructor(
    private val loanApi: LoanApi,
) : ViewModel() {


    var amount by mutableStateOf("")
    var interest by mutableStateOf("10")
    var numberDate by mutableStateOf("27")

    var selectedTypeLoan by mutableStateOf("Diario")

    fun insert(borrowersViewModel: BorrowersViewModel) {
        viewModelScope.launch {
            val loanDto = RefinanceDto(
                amount = if(amount!="") amount.toDouble() else 0.0,
                interest = interest.toDouble(),
                numberDate = numberDate.toInt(),
                loanType = when (selectedTypeLoan) {
                    "Diario" -> {
                        LoanType.Daily
                    }

                    "Semanal" -> {
                        LoanType.Weekly
                    }

                    else -> {
                        LoanType.Monthly
                    }
                },
                loanId = borrowersViewModel.selectedLoan!!.id
            )
            val response = loanApi.apiLoanRefinancePost(
                loanDto
            )
            if (response.code() == 200) {
                borrowersViewModel.updateBorrowers()
            }

            amount = ""
            interest = "8.4"
            numberDate = "27"
        }
    }

    fun onChangeAmount(text: String) {
        amount = text
    }

    fun onChangeInterest(text: String) {
        interest = text
    }

    fun onChangeNumberDate(text: String) {
        numberDate = text
    }

    fun onOptionSelected(text: String) {
        selectedTypeLoan = text

    }
}