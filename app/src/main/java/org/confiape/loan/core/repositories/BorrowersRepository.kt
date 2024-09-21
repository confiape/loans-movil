package org.confiape.loan.core.repositories

import org.confiape.loan.apis.BorrowerApi
import org.confiape.loan.models.BasicBorrowerClientWithTagsAndLoans
import org.confiape.loan.models.BorrowerClientDtoNewBorrowerClientDto
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
        var response=borrowerApi.apiBorrowerGetAllWithLoansGet()
        borrowersCache = response.body()?: listOf()
    }
    suspend fun createBorrower(dto: BorrowerClientDtoNewBorrowerClientDto){
        borrowerApi.apiBorrowerPost(dto)
        updateborrowersCache()
    }
}