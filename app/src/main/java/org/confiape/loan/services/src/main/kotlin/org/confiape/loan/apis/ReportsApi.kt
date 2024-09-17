package org.confiape.loan.apis

import org.confiape.loan.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.google.gson.annotations.SerializedName

import org.confiape.loan.models.ReportPaymentByDayDto
import org.confiape.loan.models.ReportPaymentByLoanDto

interface ReportsApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param dateTime  (optional)
     * @return [ReportPaymentByDayDto]
     */
    @GET("api/Reports/ReportPaymentByDay")
    suspend fun apiReportsReportPaymentByDayGet(@Query("dateTime") dateTime: java.time.OffsetDateTime? = null): Response<ReportPaymentByDayDto>

    /**
     * 
     * 
     * Responses:
     *  - 200: Success
     *
     * @param loanId  (optional)
     * @return [ReportPaymentByLoanDto]
     */
    @GET("api/Reports/ReportPaymentByLoan")
    suspend fun apiReportsReportPaymentByLoanGet(@Query("loanId") loanId: java.util.UUID? = null): Response<ReportPaymentByLoanDto>

}
