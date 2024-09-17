package org.confiape.loan.apis

import org.confiape.loan.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.google.gson.annotations.SerializedName

import org.confiape.loan.models.TagDto
import org.confiape.loan.models.TagDtoPaginationResponse
import org.confiape.loan.models.UpdateTagDto

interface TagApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param pageSize  (optional)
     * @param currentPage  (optional)
     * @param orderBy  (optional)
     * @return [TagDtoPaginationResponse]
     */
    @GET("api/Tag")
    suspend fun apiTagGet(@Query("PageSize") pageSize: kotlin.Int? = null, @Query("CurrentPage") currentPage: kotlin.Int? = null, @Query("OrderBy") orderBy: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<TagDtoPaginationResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param id 
     * @return [kotlin.Boolean]
     */
    @DELETE("api/Tag/{id}")
    suspend fun apiTagIdDelete(@Path("id") id: java.util.UUID): Response<kotlin.Boolean>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param id 
     * @return [TagDto]
     */
    @GET("api/Tag/{id}")
    suspend fun apiTagIdGet(@Path("id") id: java.util.UUID): Response<TagDto>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param id 
     * @param updateTagDto  (optional)
     * @return [Unit]
     */
    @PUT("api/Tag/{id}")
    suspend fun apiTagIdPut(@Path("id") id: java.util.UUID, @Body updateTagDto: UpdateTagDto? = null): Response<Unit>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param tagDto  (optional)
     * @return [TagDto]
     */
    @POST("api/Tag")
    suspend fun apiTagPost(@Body tagDto: TagDto? = null): Response<TagDto>

}
