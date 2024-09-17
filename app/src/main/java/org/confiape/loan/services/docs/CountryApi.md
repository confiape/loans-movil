# CountryApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiCountryGet**](CountryApi.md#apiCountryGet) | **GET** api/Country |  |





### Example
```kotlin
// Import classes:
//import org.confiape.loan.*
//import org.confiape.loan.infrastructure.*
//import org.confiape.loan.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(CountryApi::class.java)

launch(Dispatchers.IO) {
    val result : kotlin.collections.List<CountryDto> = webService.apiCountryGet()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**kotlin.collections.List&lt;CountryDto&gt;**](CountryDto.md)

### Authorization


Configure Bearer:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain, application/json, text/json

