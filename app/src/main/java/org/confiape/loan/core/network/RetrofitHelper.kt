package org.confiape.loan.core.network

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
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

        val okHttpClient = OkHttpClient.Builder().addInterceptor(AuthorizationInterceptor(context))

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

    return sharedPreferences.getString(name, null) ?: ""
}

class AuthorizationInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder().addHeader(
            "Authorization", "Bearer " + getToken(AppConstants.AuthorizationToken, context)
        ).build()
        Log.i(AppConstants.Tag, "Start Interceptor with token ${getToken(AppConstants.AuthorizationToken, context)}")
        val originalResponse = chain.proceed(request)

        Log.i(
            AppConstants.Tag,
            "First request with uri ${originalResponse.request.url} status ${originalResponse.code} "
        )
        if (chain.request().url.toString()
                .contains("/api/Authenticate/LoginWithGoogleToken") && originalResponse.headers("Set-Cookie")
                .isNotEmpty()
        ) {

            val cookies = originalResponse.headers("Set-Cookie")

            for (cookie in cookies) {
                if (cookie.startsWith("AuthenticationToken=")) {
                    val token = cookie.split(";")[0]
                    saveToken(context, AppConstants.AuthenticationToken, token)
                    Log.i(
                        AppConstants.Tag,
                        "is LoginWithGoogleToken with token $token "
                    )
                }
            }
        }


        if (originalResponse.code == 401) {
            Log.i(
                AppConstants.Tag,
                "Is code 401"
            )
            val service = ApiClient(AppConstants.BaseUrl,
                OkHttpClient.Builder().addInterceptor { internalChain ->
                    internalChain.proceed(
                        internalChain.request().newBuilder().addHeader(
                            "Cookie", getToken(AppConstants.AuthenticationToken, context)
                        ).build()
                    )
                }).createService(AuthenticateApi::class.java)

            val newToken = runBlocking {
                service.apiAuthenticateGetAuthorizationTokenPost()
            }

            saveToken(context, AppConstants.AuthorizationToken, newToken.body()!!.accessToken ?: "")

            originalResponse.close()
            Log.i(
                AppConstants.Tag,
                "New token ${"Bearer " + getToken(AppConstants.AuthorizationToken, context)}"
            )

            val res= chain.proceed(chain.request().newBuilder()
                .addHeader(
                    "Authorization", "Bearer " + getToken(AppConstants.AuthorizationToken, context)
                ).build())
            return res;
        }
        return originalResponse;
    }
}
