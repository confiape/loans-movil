package org.confiape.loan.apis

import org.confiape.loan.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.google.gson.annotations.SerializedName


import okhttp3.MultipartBody

interface FileApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param file  (optional)
     * @return [Unit]
     */
    @Multipart
    @POST("api/File")
    suspend fun apiFilePost(@Part file: MultipartBody.Part? = null): Response<Unit>

}
