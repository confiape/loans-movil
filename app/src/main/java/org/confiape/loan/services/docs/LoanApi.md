# LoanApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiLoanGet**](LoanApi.md#apiLoanGet) | **GET** api/Loan |  |
| [**apiLoanIdDelete**](LoanApi.md#apiLoanIdDelete) | **DELETE** api/Loan/{id} |  |
| [**apiLoanIdGet**](LoanApi.md#apiLoanIdGet) | **GET** api/Loan/{id} |  |
| [**apiLoanIdPut**](LoanApi.md#apiLoanIdPut) | **PUT** api/Loan/{id} |  |
| [**apiLoanPost**](LoanApi.md#apiLoanPost) | **POST** api/Loan |  |





### Example
```kotlin
// Import classes:
//import org.confiape.loan.*
//import org.confiape.loan.infrastructure.*
//import org.confiape.loan.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(LoanApi::class.java)
val latitude : kotlin.Double = 1.2 // kotlin.Double | 
val longitude : kotlin.Double = 1.2 // kotlin.Double | 
val pageSize : kotlin.Int = 56 // kotlin.Int | 
val currentPage : kotlin.Int = 56 // kotlin.Int | 
val orderBy : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | 

launch(Dispatchers.IO) {
    val result : BasicLoanDtoPaginationResponse = webService.apiLoanGet(latitude, longitude, pageSize, currentPage, orderBy)
}
```

### Parameters
| **latitude** | **kotlin.Double**|  | [optional] |
| **longitude** | **kotlin.Double**|  | [optional] |
| **pageSize** | **kotlin.Int**|  | [optional] |
| **currentPage** | **kotlin.Int**|  | [optional] |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **orderBy** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)|  | [optional] |

### Return type

[**BasicLoanDtoPaginationResponse**](BasicLoanDtoPaginationResponse.md)

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
val webService = apiClient.createWebservice(LoanApi::class.java)
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | 

launch(Dispatchers.IO) {
    webService.apiLoanIdDelete(id)
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**|  | |

### Return type

null (empty response body)

### Authorization


Configure Bearer:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined




### Example
```kotlin
// Import classes:
//import org.confiape.loan.*
//import org.confiape.loan.infrastructure.*
//import org.confiape.loan.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(LoanApi::class.java)
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | 

launch(Dispatchers.IO) {
    val result : CompleteLoanDto = webService.apiLoanIdGet(id)
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**|  | |

### Return type

[**CompleteLoanDto**](CompleteLoanDto.md)

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
val webService = apiClient.createWebservice(LoanApi::class.java)
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | 
val amount : kotlin.Double = 1.2 // kotlin.Double | 
val interest : kotlin.Double = 1.2 // kotlin.Double | 
val numberDate : kotlin.Int = 56 // kotlin.Int | 
val borrowerClientId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | 

launch(Dispatchers.IO) {
    webService.apiLoanIdPut(id, amount, interest, numberDate, borrowerClientId)
}
```

### Parameters
| **id** | **java.util.UUID**|  | |
| **amount** | **kotlin.Double**|  | [optional] |
| **interest** | **kotlin.Double**|  | [optional] |
| **numberDate** | **kotlin.Int**|  | [optional] |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **borrowerClientId** | **java.util.UUID**|  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure Bearer:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined




### Example
```kotlin
// Import classes:
//import org.confiape.loan.*
//import org.confiape.loan.infrastructure.*
//import org.confiape.loan.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(LoanApi::class.java)
val createLoanDto : CreateLoanDto =  // CreateLoanDto | 

launch(Dispatchers.IO) {
    webService.apiLoanPost(createLoanDto)
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createLoanDto** | [**CreateLoanDto**](CreateLoanDto.md)|  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure Bearer:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: application/json, text/json, application/*+json
 - **Accept**: Not defined

