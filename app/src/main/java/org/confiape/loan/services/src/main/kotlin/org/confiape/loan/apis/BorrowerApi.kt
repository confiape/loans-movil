package org.confiape.loan.apis

import org.confiape.loan.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.google.gson.annotations.SerializedName

import org.confiape.loan.models.BasicBorrowerClientWithTagsAndLoans
import org.confiape.loan.models.BasicBorrowerClientWithTagsPaginationResponse
import org.confiape.loan.models.BorrowerClientDto
import org.confiape.loan.models.BorrowerClientDtoNewBorrowerClientDto
import org.confiape.loan.models.ResponseBorrowerClientDto
import org.confiape.loan.models.UpdateBorrowerClientDtoNewBorrowerClientDto

interface BorrowerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param pageSize  (optional)
     * @param currentPage  (optional)
     * @param orderBy  (optional)
     * @return [BasicBorrowerClientWithTagsPaginationResponse]
     */
    @GET("api/Borrower")
    suspend fun apiBorrowerGet(@Query("PageSize") pageSize: kotlin.Int? = null, @Query("CurrentPage") currentPage: kotlin.Int? = null, @Query("OrderBy") orderBy: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<BasicBorrowerClientWithTagsPaginationResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @return [kotlin.collections.List<BasicBorrowerClientWithTagsAndLoans>]
     */
    @GET("api/Borrower/GetAllWithLoans")
    suspend fun apiBorrowerGetAllWithLoansGet(): Response<kotlin.collections.List<BasicBorrowerClientWithTagsAndLoans>>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param id 
     * @return [kotlin.Boolean]
     */
    @DELETE("api/Borrower/{id}")
    suspend fun apiBorrowerIdDelete(@Path("id") id: java.util.UUID): Response<kotlin.Boolean>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param id 
     * @return [ResponseBorrowerClientDto]
     */
    @GET("api/Borrower/{id}")
    suspend fun apiBorrowerIdGet(@Path("id") id: java.util.UUID): Response<ResponseBorrowerClientDto>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param id 
     * @param updateBorrowerClientDtoNewBorrowerClientDto  (optional)
     * @return [Unit]
     */
    @PUT("api/Borrower/{id}")
    suspend fun apiBorrowerIdPut(@Path("id") id: java.util.UUID, @Body updateBorrowerClientDtoNewBorrowerClientDto: UpdateBorrowerClientDtoNewBorrowerClientDto? = null): Response<Unit>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param borrowerClientDtoNewBorrowerClientDto  (optional)
     * @return [BorrowerClientDto]
     */
    @POST("api/Borrower")
    suspend fun apiBorrowerPost(@Body borrowerClientDtoNewBorrowerClientDto: BorrowerClientDtoNewBorrowerClientDto? = null): Response<BorrowerClientDto>

}
