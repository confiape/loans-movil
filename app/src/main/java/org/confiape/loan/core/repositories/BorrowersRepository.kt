package org.confiape.loan.core.repositories

import org.confiape.loan.apis.BorrowerApi
import org.confiape.loan.models.BasicBorrowerClientWithTagsAndLoans
import javax.inject.Inject

class BorrowersRepository @Inject constructor(
    private val borrowerApi: BorrowerApi,
) {
    private var borrowersCache: List<BasicBorrowerClientWithTagsAndLoans>? = null
    suspend fun getBorrowers(): List<BasicBorrowerClientWithTagsAndLoans> {
        return if (borrowersCache == null) {

            updateborrowersCache()
            borrowersCache!!
        } else {
            borrowersCache!!
        }
    }

    suspend fun updateborrowersCache() {
        borrowersCache = borrowerApi.apiBorrowerGetAllWithLoansGet().body()!!
    }
}