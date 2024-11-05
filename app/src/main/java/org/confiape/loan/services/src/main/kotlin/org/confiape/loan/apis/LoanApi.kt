package org.confiape.loan.apis

import org.confiape.loan.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.google.gson.annotations.SerializedName

import org.confiape.loan.models.BasicLoanDtoPaginationResponse
import org.confiape.loan.models.CompleteLoanDto
import org.confiape.loan.models.CreateLoanDto
import org.confiape.loan.models.RefinanceDto

interface LoanApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param latitude  (optional)
     * @param longitude  (optional)
     * @param pageSize  (optional)
     * @param currentPage  (optional)
     * @param orderBy  (optional)
     * @return [BasicLoanDtoPaginationResponse]
     */
    @GET("api/Loan")
    suspend fun apiLoanGet(@Query("Latitude") latitude: kotlin.Double? = null, @Query("Longitude") longitude: kotlin.Double? = null, @Query("PageSize") pageSize: kotlin.Int? = null, @Query("CurrentPage") currentPage: kotlin.Int? = null, @Query("OrderBy") orderBy: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<BasicLoanDtoPaginationResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param id 
     * @return [Unit]
     */
    @DELETE("api/Loan/{id}")
    suspend fun apiLoanIdDelete(@Path("id") id: java.util.UUID): Response<Unit>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param id 
     * @return [CompleteLoanDto]
     */
    @GET("api/Loan/{id}")
    suspend fun apiLoanIdGet(@Path("id") id: java.util.UUID): Response<CompleteLoanDto>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param id 
     * @param amount  (optional)
     * @param interest  (optional)
     * @param numberDate  (optional)
     * @param borrowerClientId  (optional)
     * @return [Unit]
     */
    @PUT("api/Loan/{id}")
    suspend fun apiLoanIdPut(@Path("id") id: java.util.UUID, @Query("Amount") amount: kotlin.Double? = null, @Query("Interest") interest: kotlin.Double? = null, @Query("NumberDate") numberDate: kotlin.Int? = null, @Query("BorrowerClientId") borrowerClientId: java.util.UUID? = null): Response<Unit>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param createLoanDto  (optional)
     * @return [Unit]
     */
    @POST("api/Loan")
    suspend fun apiLoanPost(@Body createLoanDto: CreateLoanDto? = null): Response<Unit>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param refinanceDto  (optional)
     * @return [Unit]
     */
    @POST("api/Loan/Refinance")
    suspend fun apiLoanRefinancePost(@Body refinanceDto: RefinanceDto? = null): Response<Unit>

}
