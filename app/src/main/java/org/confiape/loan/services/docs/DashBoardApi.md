# DashBoardApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiDashBoardGetAverageInterestByMonthGet**](DashBoardApi.md#apiDashBoardGetAverageInterestByMonthGet) | **GET** api/DashBoard/GetAverageInterestByMonth |  |
| [**apiDashBoardGetLoansByMonthGet**](DashBoardApi.md#apiDashBoardGetLoansByMonthGet) | **GET** api/DashBoard/GetLoansByMonth |  |
| [**apiDashBoardGetTotalLoanAmountByClientGet**](DashBoardApi.md#apiDashBoardGetTotalLoanAmountByClientGet) | **GET** api/DashBoard/GetTotalLoanAmountByClient |  |





### Example
```kotlin
// Import classes:
//import org.confiape.loan.*
//import org.confiape.loan.infrastructure.*
//import org.confiape.loan.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(DashBoardApi::class.java)

launch(Dispatchers.IO) {
    val result : kotlin.collections.List<AverageInterestByMonthDto> = webService.apiDashBoardGetAverageInterestByMonthGet()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**kotlin.collections.List&lt;AverageInterestByMonthDto&gt;**](AverageInterestByMonthDto.md)

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
val webService = apiClient.createWebservice(DashBoardApi::class.java)

launch(Dispatchers.IO) {
    val result : kotlin.collections.Map<kotlin.String, kotlin.collections.List<LoansByMonthDtoMetadata>> = webService.apiDashBoardGetLoansByMonthGet()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**kotlin.collections.Map&lt;kotlin.String, kotlin.collections.List&lt;LoansByMonthDtoMetadata&gt;&gt;**

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
val webService = apiClient.createWebservice(DashBoardApi::class.java)

launch(Dispatchers.IO) {
    val result : kotlin.collections.List<TotalLoanAmountByClientDto> = webService.apiDashBoardGetTotalLoanAmountByClientGet()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**kotlin.collections.List&lt;TotalLoanAmountByClientDto&gt;**](TotalLoanAmountByClientDto.md)

### Authorization


Configure Bearer:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain, application/json, text/json

