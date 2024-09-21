package org.confiape.loan.core.repositories

import org.confiape.loan.apis.LoanApi
import org.confiape.loan.models.CompleteLoanDto
import java.util.UUID
import javax.inject.Inject

class LoanRepository @Inject constructor(
    private val loanApi: LoanApi,
) {
    var loan: CompleteLoanDto = CompleteLoanDto()
        private set

    suspend fun update(loanId: UUID?) {
        if (loanId != null) {
            loan = loanApi.apiLoanIdGet(loanId).body()!!
        }
    }
}