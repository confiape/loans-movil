# ReportsApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiReportsGetReportsByDayGet**](ReportsApi.md#apiReportsGetReportsByDayGet) | **GET** api/Reports/GetReportsByDay |  |
| [**apiReportsReportPaymentByDayGet**](ReportsApi.md#apiReportsReportPaymentByDayGet) | **GET** api/Reports/ReportPaymentByDay |  |
| [**apiReportsReportPaymentByLoanGet**](ReportsApi.md#apiReportsReportPaymentByLoanGet) | **GET** api/Reports/ReportPaymentByLoan |  |





### Example
```kotlin
// Import classes:
//import org.confiape.loan.*
//import org.confiape.loan.infrastructure.*
//import org.confiape.loan.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(ReportsApi::class.java)
val dateTime : java.time.OffsetDateTime = 2013-10-20T19:20:30+01:00 // java.time.OffsetDateTime | 

launch(Dispatchers.IO) {
    val result : java.io.File = webService.apiReportsGetReportsByDayGet(dateTime)
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **dateTime** | **java.time.OffsetDateTime**|  | [optional] |

### Return type

[**java.io.File**](java.io.File.md)

### Authorization


Configure Bearer:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain, application/json, text/json




### Example
```kotlin
// Import classes:
//import org.confiape.loan.*
//import org.confiape.loan.infrastructure.*
//import org.confiape.loan.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(ReportsApi::class.java)
val dateTime : java.time.OffsetDateTime = 2013-10-20T19:20:30+01:00 // java.time.OffsetDateTime | 

launch(Dispatchers.IO) {
    val result : ReportPaymentByDayDto = webService.apiReportsReportPaymentByDayGet(dateTime)
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **dateTime** | **java.time.OffsetDateTime**|  | [optional] |

### Return type

[**ReportPaymentByDayDto**](ReportPaymentByDayDto.md)

### Authorization


Configure Bearer:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain, application/json, text/json




### Example
```kotlin
// Import classes:
//import org.confiape.loan.*
//import org.confiape.loan.infrastructure.*
//import org.confiape.loan.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(ReportsApi::class.java)
val loanId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | 

launch(Dispatchers.IO) {
    val result : ReportPaymentByLoanDto = webService.apiReportsReportPaymentByLoanGet(loanId)
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **loanId** | **java.util.UUID**|  | [optional] |

### Return type

[**ReportPaymentByLoanDto**](ReportPaymentByLoanDto.md)

### Authorization


Configure Bearer:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain, application/json, text/json

