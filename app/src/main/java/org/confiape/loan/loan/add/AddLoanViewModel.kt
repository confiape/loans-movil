package org.confiape.loan.loan.add

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
import javax.inject.Inject

@HiltViewModel
class AddLoanViewModel @Inject constructor(
    private val loanApi: LoanApi
) : ViewModel() {

    var borrower: BasicBorrowerClientWithTagsAndLoans = BasicBorrowerClientWithTagsAndLoans();

    var amount by mutableStateOf("")
    var interest by mutableStateOf("8.4")
    var numberDate by mutableStateOf("27")

    fun insert(borrowersViewModel: BorrowersViewModel) {
        viewModelScope.launch {
            loanApi.apiLoanPost(
                amount = amount.toDouble(),
                interest = interest.toDouble(),
                numberDate = numberDate.toInt(),
                borrowerClientId = borrower.id
            )
            borrowersViewModel.updateBorrowers()
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
}