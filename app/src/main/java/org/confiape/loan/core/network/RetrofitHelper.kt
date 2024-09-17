package org.confiape.loan.core.network

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.confiape.loan.apis.AuthenticateApi
import org.confiape.loan.apis.LoanApi
import org.confiape.loan.core.AppConstants
import org.confiape.loan.infrastructure.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiClient(@ApplicationContext context: Context): ApiClient {

        val okHttpClient =
            OkHttpClient.Builder().addInterceptor(ReceivedCookiesInterceptor(context))
                .addInterceptor(AuthorizationInterceptor(context))

        val response = ApiClient(
            baseUrl = AppConstants.BaseUrl, okHttpClient
        )
        return response
    }

    @Provides
    @Singleton
    fun provideAuthenticateApi(apiClient: ApiClient): AuthenticateApi {
        return apiClient.createService(AuthenticateApi::class.java)
    }
    @Provides
    @Singleton
    fun provideLoanApi(apiClient: ApiClient): LoanApi {
        return apiClient.createService(LoanApi::class.java)
    }

}


class ReceivedCookiesInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalResponse = chain.proceed(chain.request())
        if (chain.request().url.toString()
                .contains("/api/Authenticate/LogIn") && originalResponse.headers("Set-Cookie")
                .isNotEmpty()
        ) {
            val cookies = originalResponse.headers("Set-Cookie")

            for (cookie in cookies) {
                if (cookie.startsWith("AuthenticationToken=")) {
                    val token = cookie.split(";")[0]
                    saveToken(context, AppConstants.AuthenticationToken, token)

                }
            }
        }

        return originalResponse
    }


}

fun saveToken(context: Context, name: String, token: String) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(name, token)
    editor.apply()
}

fun getToken(name: String, context: Context): String {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    return sharedPreferences.getString(AppConstants.AuthenticationToken, null) ?: ""
}

class AuthorizationInterceptor(private val context: Context) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(
                "Authorization",
                "Bearer " + getToken(AppConstants.AuthorizationToken, context)
            )
            .build()

        val originalResponse = chain.proceed(request)
        if (originalResponse.code == 401) {

            val service =
                ApiClient(
                    AppConstants.BaseUrl,
                    OkHttpClient.Builder().addInterceptor { internalChain ->
                        internalChain.proceed(
                            internalChain.request().newBuilder()
                                .addHeader(
                                    "Cookie",
                                    getToken(AppConstants.AuthenticationToken, context)
                                )
                                .build()
                        )
                    }).createService(AuthenticateApi::class.java)

            val newToken = runBlocking {
                service.apiAuthenticateGetAuthorizationTokenPost()
            }
            saveToken(context, AppConstants.AuthorizationToken, newToken.body()!!.accessToken ?: "")

            originalResponse.close()

            return chain.proceed(chain.request()).newBuilder()
                .removeHeader("Authorization")
                .addHeader(
                    "Authorization",
                    "Bearer " + getToken(AppConstants.AuthorizationToken, context)
                )
                .build()
        }
        return originalResponse;
    }
}
