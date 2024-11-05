package org.confiape.loan.apis

import org.confiape.loan.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.google.gson.annotations.SerializedName

import org.confiape.loan.models.LoginDto
import org.confiape.loan.models.LoginResponse
import org.confiape.loan.models.TokenDto
import org.confiape.loan.models.UserDto

interface AuthenticateApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param id 
     * @param requestBody  (optional)
     * @return [Unit]
     */
    @POST("api/Authenticate/AssignRoleToUser/{id}")
    suspend fun apiAuthenticateAssignRoleToUserIdPost(@Path("id") id: kotlin.String, @Body requestBody: kotlin.collections.List<kotlin.String>? = null): Response<Unit>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @return [kotlin.collections.List<UserDto>]
     */
    @POST("api/Authenticate/GetAllUsers")
    suspend fun apiAuthenticateGetAllUsersPost(): Response<kotlin.collections.List<UserDto>>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @return [LoginResponse]
     */
    @POST("api/Authenticate/GetAuthorizationToken")
    suspend fun apiAuthenticateGetAuthorizationTokenPost(): Response<LoginResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param loginDto  (optional)
     * @return [Unit]
     */
    @POST("api/Authenticate/LogIn")
    suspend fun apiAuthenticateLogInPost(@Body loginDto: LoginDto? = null): Response<Unit>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @return [Unit]
     */
    @GET("api/Authenticate/LogOut")
    suspend fun apiAuthenticateLogOutGet(): Response<Unit>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @return [Unit]
     */
    @POST("api/Authenticate/LoginWithAuthenticationToken")
    suspend fun apiAuthenticateLoginWithAuthenticationTokenPost(): Response<Unit>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param tokenDto  (optional)
     * @return [Unit]
     */
    @POST("api/Authenticate/LoginWithGoogleToken")
    suspend fun apiAuthenticateLoginWithGoogleTokenPost(@Body tokenDto: TokenDto? = null): Response<Unit>

}
