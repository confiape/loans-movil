package org.confiape.loan.core.network

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.Modifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.confiape.loan.apis.AuthenticateApi
import org.confiape.loan.apis.BorrowerApi
import org.confiape.loan.apis.LoanApi
import org.confiape.loan.apis.PaymentApi
import org.confiape.loan.apis.TagApi
import org.confiape.loan.core.AppConstants
import org.confiape.loan.core.SharedService
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

    @Provides
    @Singleton
    fun provideBorrowerApi(apiClient: ApiClient): BorrowerApi {
        return apiClient.createService(BorrowerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTagApi(apiClient: ApiClient): TagApi {
        return apiClient.createService(TagApi::class.java)
    }
    @Provides
    @Singleton
    fun providePaymentApi(apiClient: ApiClient): PaymentApi {
        return apiClient.createService(PaymentApi::class.java)
    }
}


class AuthorizationInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder().addHeader(
            "Authorization",
            "Bearer " + SharedService.getToken(AppConstants.AuthorizationToken, context)
        ).build()
        Log.i(
            AppConstants.Tag, "Start Interceptor with token ${
                SharedService.getToken(
                    AppConstants.AuthorizationToken, context
                )
            }"
        )
        val originalResponse = chain.proceed(request)


        Log.i(
            AppConstants.Tag,
            "First request with uri ${originalResponse.request.url} status ${originalResponse.code} "
        )
        if ((chain.request().url.toString()
                .contains("/api/Authenticate/LoginWithGoogleToken") || chain.request().url.toString()
                .contains("/api/Authenticate/LoginWithAuthenticationToken")) && originalResponse.headers(
                "Set-Cookie"
            ).isNotEmpty()
            && originalResponse.code == 200
        ) {

            val cookies = originalResponse.headers("Set-Cookie")

            for (cookie in cookies) {
                if (cookie.startsWith("AuthenticationToken=")) {
                    val token = cookie.split(";")[0]
                    SharedService.saveToken(context, AppConstants.AuthenticationToken, token)
                    Log.i(
                        AppConstants.Tag, "is LoginWithGoogleToken with token $token "
                    )
                }
            }
        }


        if (originalResponse.code == 401) {
            Log.i(
                AppConstants.Tag, "Is code 401"
            )
            val service = ApiClient(AppConstants.BaseUrl,
                OkHttpClient.Builder().addInterceptor { internalChain ->
                    internalChain.proceed(
                        internalChain.request().newBuilder().addHeader(
                            "Cookie",
                            SharedService.getToken(AppConstants.AuthenticationToken, context)
                        ).build()
                    )
                }).createService(AuthenticateApi::class.java)

            val newToken = runBlocking {
                service.apiAuthenticateGetAuthorizationTokenPost()
            }
            if(newToken.code()==200){
                SharedService.saveToken(
                    context, AppConstants.AuthorizationToken, newToken.body()!!.accessToken ?: ""
                )
                originalResponse.close()

                val res = chain.proceed(
                    chain.request().newBuilder().addHeader(
                        "Authorization",
                        "Bearer " + SharedService.getToken(AppConstants.AuthorizationToken, context)
                    ).build()
                )
                return res
            }

        }

        if (originalResponse.code == 200 && originalResponse.request.method=="POST") {
            Handler(Looper.getMainLooper()).post {
                Toasty.success(context, "This is correct.", Toast.LENGTH_SHORT, true).show()
            }
        }else{
            if(originalResponse.code!=200 ){
                Handler(Looper.getMainLooper()).post {
                    Toasty.error(context, "Error . ${originalResponse.code} ${originalResponse.message} ${originalResponse.message}", Toast.LENGTH_SHORT, true).show()
                }
            }
        }
        return originalResponse
    }
}
