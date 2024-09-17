package org.confiape.loan.apis

import org.confiape.loan.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.google.gson.annotations.SerializedName

import org.confiape.loan.models.AverageInterestByMonthDto
import org.confiape.loan.models.LoansByMonthDtoMetadata
import org.confiape.loan.models.TotalLoanAmountByClientDto

interface DashBoardApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @return [kotlin.collections.List<AverageInterestByMonthDto>]
     */
    @GET("api/DashBoard/GetAverageInterestByMonth")
    suspend fun apiDashBoardGetAverageInterestByMonthGet(): Response<kotlin.collections.List<AverageInterestByMonthDto>>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @return [kotlin.collections.Map<kotlin.String, kotlin.collections.List<LoansByMonthDtoMetadata>>]
     */
    @GET("api/DashBoard/GetLoansByMonth")
    suspend fun apiDashBoardGetLoansByMonthGet(): Response<kotlin.collections.Map<kotlin.String, kotlin.collections.List<LoansByMonthDtoMetadata>>>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @return [kotlin.collections.List<TotalLoanAmountByClientDto>]
     */
    @GET("api/DashBoard/GetTotalLoanAmountByClient")
    suspend fun apiDashBoardGetTotalLoanAmountByClientGet(): Response<kotlin.collections.List<TotalLoanAmountByClientDto>>

}
