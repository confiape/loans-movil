package org.confiape.loan.borrowers.loan.add

import android.util.Log
import android.widget.Toast
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
import org.confiape.loan.models.CreateLoanDto
import org.confiape.loan.models.LoanType
import javax.inject.Inject

@HiltViewModel
class AddLoanViewModel @Inject constructor(
    private val loanApi: LoanApi,
) : ViewModel() {

    var borrower: BasicBorrowerClientWithTagsAndLoans = BasicBorrowerClientWithTagsAndLoans();

    var amount by mutableStateOf("")
    var interest by mutableStateOf("8.4")
    var numberDate by mutableStateOf("27")

    var selectedTypeLoan by mutableStateOf("Diario")

    fun insert(borrowersViewModel: BorrowersViewModel) {
        viewModelScope.launch {
            val loanDto= CreateLoanDto(
                amount = amount.toDouble(),
                interest = interest.toDouble(),
                numberDate = numberDate.toInt(),
                borrowerClientId = borrower.id,
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
                }
            )
            var response=loanApi.apiLoanPost(
                loanDto
            )
            if(response.code()==200){
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