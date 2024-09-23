package org.confiape.loan.loan

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.confiape.loan.core.repositories.LoanRepository
import org.confiape.loan.models.CompleteLoanDto
import javax.inject.Inject

@HiltViewModel
class LoanViewModel @Inject constructor(
    private val loanRepository: LoanRepository,
) : ViewModel() {

    var complete: CompleteLoanDto = CompleteLoanDto();

    init {
        complete = loanRepository.loan
    }
}