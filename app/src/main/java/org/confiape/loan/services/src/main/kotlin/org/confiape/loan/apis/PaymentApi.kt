package org.confiape.loan.apis

import org.confiape.loan.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.google.gson.annotations.SerializedName

import org.confiape.loan.models.NewPaymentDto
import org.confiape.loan.models.Payment
import org.confiape.loan.models.PaymentDto
import org.confiape.loan.models.PaymentDtoPaginationResponse

interface PaymentApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param pageSize  (optional)
     * @param currentPage  (optional)
     * @param orderBy  (optional)
     * @return [PaymentDtoPaginationResponse]
     */
    @GET("api/Payment")
    suspend fun apiPaymentGet(@Query("PageSize") pageSize: kotlin.Int? = null, @Query("CurrentPage") currentPage: kotlin.Int? = null, @Query("OrderBy") orderBy: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<PaymentDtoPaginationResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param id 
     * @return [PaymentDto]
     */
    @GET("api/Payment/{id}")
    suspend fun apiPaymentIdGet(@Path("id") id: java.util.UUID): Response<PaymentDto>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param id 
     * @param amount  (optional)
     * @param isYape  (optional)
     * @return [Payment]
     */
    @PUT("api/Payment/{id}")
    suspend fun apiPaymentIdPut(@Path("id") id: java.util.UUID, @Query("Amount") amount: kotlin.Double? = null, @Query("IsYape") isYape: kotlin.Boolean? = null): Response<Payment>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param newPaymentDto  (optional)
     * @return [PaymentDto]
     */
    @POST("api/Payment")
    suspend fun apiPaymentPost(@Body newPaymentDto: NewPaymentDto? = null): Response<PaymentDto>

}
