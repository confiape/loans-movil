package org.confiape.loan.apis

import org.confiape.loan.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.google.gson.annotations.SerializedName

import org.confiape.loan.models.CountryDto

interface CountryApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @return [kotlin.collections.List<CountryDto>]
     */
    @GET("api/Country")
    suspend fun apiCountryGet(): Response<kotlin.collections.List<CountryDto>>

}
