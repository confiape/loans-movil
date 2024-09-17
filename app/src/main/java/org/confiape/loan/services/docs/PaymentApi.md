# PaymentApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiPaymentGet**](PaymentApi.md#apiPaymentGet) | **GET** api/Payment |  |
| [**apiPaymentIdGet**](PaymentApi.md#apiPaymentIdGet) | **GET** api/Payment/{id} |  |
| [**apiPaymentIdPut**](PaymentApi.md#apiPaymentIdPut) | **PUT** api/Payment/{id} |  |
| [**apiPaymentPost**](PaymentApi.md#apiPaymentPost) | **POST** api/Payment |  |





### Example
```kotlin
// Import classes:
//import org.confiape.loan.*
//import org.confiape.loan.infrastructure.*
//import org.confiape.loan.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(PaymentApi::class.java)
val pageSize : kotlin.Int = 56 // kotlin.Int | 
val currentPage : kotlin.Int = 56 // kotlin.Int | 
val orderBy : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | 

launch(Dispatchers.IO) {
    val result : PaymentDtoPaginationResponse = webService.apiPaymentGet(pageSize, currentPage, orderBy)
}
```

### Parameters
| **pageSize** | **kotlin.Int**|  | [optional] |
| **currentPage** | **kotlin.Int**|  | [optional] |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **orderBy** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)|  | [optional] |

### Return type

[**PaymentDtoPaginationResponse**](PaymentDtoPaginationResponse.md)

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
val webService = apiClient.createWebservice(PaymentApi::class.java)
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | 

launch(Dispatchers.IO) {
    val result : PaymentDto = webService.apiPaymentIdGet(id)
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**|  | |

### Return type

[**PaymentDto**](PaymentDto.md)

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
val webService = apiClient.createWebservice(PaymentApi::class.java)
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | 
val paymentDto : kotlin.Any = Object // kotlin.Any | 

launch(Dispatchers.IO) {
    val result : Payment = webService.apiPaymentIdPut(id, paymentDto)
}
```

### Parameters
| **id** | **java.util.UUID**|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **paymentDto** | [**kotlin.Any**](.md)|  | [optional] |

### Return type

[**Payment**](Payment.md)

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
val webService = apiClient.createWebservice(PaymentApi::class.java)
val newPaymentDto : NewPaymentDto =  // NewPaymentDto | 

launch(Dispatchers.IO) {
    val result : PaymentDto = webService.apiPaymentPost(newPaymentDto)
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **newPaymentDto** | [**NewPaymentDto**](NewPaymentDto.md)|  | [optional] |

### Return type

[**PaymentDto**](PaymentDto.md)

### Authorization


Configure Bearer:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: application/json, text/json, application/*+json
 - **Accept**: text/plain, application/json, text/json

