package org.confiape.loan.borrowers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.confiape.loan.apis.BorrowerApi
import org.confiape.loan.core.repositories.LoanRepository
import org.confiape.loan.core.repositories.TagRepository
import org.confiape.loan.models.BasicBorrowerClientWithTagsAndLoans
import org.confiape.loan.models.SimpleLoanDtoAndPayments
import org.confiape.loan.models.SimplePayments
import org.confiape.loan.models.TagDto
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BorrowersViewModel @Inject constructor(
    private val borrowerApi: BorrowerApi,
    private val tagRepository: TagRepository,
    private val loanRepository: LoanRepository,
) : ViewModel() {

    private var allBorrowers by mutableStateOf<List<BasicBorrowerClientWithTagsAndLoans>>(
        listOf()
    )

    private var selectedTags by mutableStateOf<List<String>>(listOf())

    var selectedLoan by mutableStateOf<SimpleLoanDtoAndPayments?>(null)
        private set

    var selectedBorrower by mutableStateOf<BasicBorrowerClientWithTagsAndLoans?>(null)
        private set


    var showAddBorrowerScreen by mutableStateOf(false)
        private set

    var showLoanScreen by mutableStateOf(false)
        private set

    var filterBorrowers by mutableStateOf<List<BasicBorrowerClientWithTagsAndLoans>>(listOf())
        private set

    var tags by mutableStateOf<List<TagDto>>(listOf())
        private set

    var searchText by mutableStateOf("")
        private set

    init {

        viewModelScope.launch {
            allBorrowers = borrowerApi.apiBorrowerGetAllWithLoansGet().body()!!
            filterBorrowers = allBorrowers
            tags = tagRepository.getTags()
        }
    }

    fun onSelectTag(id: String) {
        selectedTags = if (id in selectedTags) {
            selectedTags - id
        } else {
            selectedTags + id
        }

        filterBorrowers = if (selectedTags.isEmpty()) {
            allBorrowers
        } else {
            allBorrowers.filter { borrower ->
                selectedTags.any { tagId ->
                    borrower.tags!!.contains(tagId)
                }
            }
        }
    }

    fun activateAddBorrowerScreen(isActivate: Boolean) {
        showAddBorrowerScreen = isActivate
    }

    fun activateAddLoanScreen(isActivate: Boolean) {
        showLoanScreen = isActivate
    }


    fun selectLoan(item: SimpleLoanDtoAndPayments?) {
        selectedLoan = item
    }

    fun selectBorrower(item: BasicBorrowerClientWithTagsAndLoans?) {
        selectedBorrower = item
    }


    fun isSelectedTag(id: String?): Boolean {
        return id in selectedTags
    }

    fun onSearchTextChange(newText: String) {
        searchText = newText
        filterBorrowers = if (searchText.isEmpty()) {
            allBorrowers
        } else {
            allBorrowers.filter { borrower ->

                borrower.name!!.contains(searchText, ignoreCase = true)
            }
        }
    }

    fun onClickOnLoan(simpleLoanDto: SimpleLoanDtoAndPayments) {
        showLoanScreen = true
        viewModelScope.launch {
            loanRepository.update(simpleLoanDto.id)
        }
    }

    fun updateBorrowers() {
        viewModelScope.launch {
            allBorrowers = borrowerApi.apiBorrowerGetAllWithLoansGet().body()!!
            filterBorrowers = allBorrowers
        }
    }

    fun addLocalPayments(loanId: UUID, simplePayments: SimplePayments) {
        allBorrowers = allBorrowers.map { borrower ->
            if (borrower.loans!!.any { it.id == loanId }) {
                borrower.copy(loans = borrower.loans.map {
                    if (it.id == loanId) {
                        it.copy(
                            payments = listOf(simplePayments) + (it.payments ?: listOf())
                        )
                    } else {
                        it
                    }
                })
            } else {
                borrower
            }
        }
        filterBorrowers = filterBorrowers.map { borrower ->
            if (borrower.loans!!.any { it.id == loanId }) {
                borrower.copy(loans = borrower.loans.map {
                    if (it.id == loanId) {
                        it.copy(
                            payments = listOf(simplePayments) + (it.payments ?: listOf())
                        )
                    } else {
                        it
                    }
                })
            } else {
                borrower
            }
        }

        updateSelects()
    }

    fun updateSelects() {
        if (selectedLoan != null) {
            selectedLoan = allBorrowers.flatMap { it.loans!! }.find { it.id == selectedLoan!!.id }
        }
    }

    fun updateLocalPayments(id: UUID, simplePayments: SimplePayments) {
        allBorrowers = allBorrowers.map { borrower ->
            if (borrower.loans!!.any { it.payments!!.any { payment -> payment.id == id } }) {
                borrower.copy(loans = borrower.loans.map { loans->
                    loans.copy(
                        payments = loans.payments!!.map {
                            if(it.id==id){
                                it.copy(
                                    amount = simplePayments.amount,
                                    dateTime = simplePayments.dateTime,
                                    id = simplePayments.id
                                )
                            }else{
                                it
                            }
                        }
                    )

                })
            } else {
                borrower
            }
        }
        filterBorrowers = filterBorrowers.map { borrower ->
            if (borrower.loans!!.any { it.payments!!.any { payment -> payment.id == id } }) {
                borrower.copy(loans = borrower.loans.map { loans->
                    loans.copy(
                        payments = loans.payments!!.map {
                            if(it.id==id){
                                it.copy(
                                    amount = simplePayments.amount,
                                    dateTime = simplePayments.dateTime,
                                    id = simplePayments.id
                                )
                            }else{
                                it
                            }
                        }
                    )

                })
            } else {
                borrower
            }
        }
        updateSelects()
    }
}