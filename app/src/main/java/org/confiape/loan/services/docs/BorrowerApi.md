# BorrowerApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiBorrowerGet**](BorrowerApi.md#apiBorrowerGet) | **GET** api/Borrower |  |
| [**apiBorrowerGetAllWithLoansGet**](BorrowerApi.md#apiBorrowerGetAllWithLoansGet) | **GET** api/Borrower/GetAllWithLoans |  |
| [**apiBorrowerIdDelete**](BorrowerApi.md#apiBorrowerIdDelete) | **DELETE** api/Borrower/{id} |  |
| [**apiBorrowerIdGet**](BorrowerApi.md#apiBorrowerIdGet) | **GET** api/Borrower/{id} |  |
| [**apiBorrowerIdPut**](BorrowerApi.md#apiBorrowerIdPut) | **PUT** api/Borrower/{id} |  |
| [**apiBorrowerPost**](BorrowerApi.md#apiBorrowerPost) | **POST** api/Borrower |  |





### Example
```kotlin
// Import classes:
//import org.confiape.loan.*
//import org.confiape.loan.infrastructure.*
//import org.confiape.loan.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(BorrowerApi::class.java)
val pageSize : kotlin.Int = 56 // kotlin.Int | 
val currentPage : kotlin.Int = 56 // kotlin.Int | 
val orderBy : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | 

launch(Dispatchers.IO) {
    val result : BasicBorrowerClientWithTagsPaginationResponse = webService.apiBorrowerGet(pageSize, currentPage, orderBy)
}
```

### Parameters
| **pageSize** | **kotlin.Int**|  | [optional] |
| **currentPage** | **kotlin.Int**|  | [optional] |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **orderBy** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)|  | [optional] |

### Return type

[**BasicBorrowerClientWithTagsPaginationResponse**](BasicBorrowerClientWithTagsPaginationResponse.md)

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
val webService = apiClient.createWebservice(BorrowerApi::class.java)

launch(Dispatchers.IO) {
    val result : kotlin.collections.List<BasicBorrowerClientWithTagsAndLoans> = webService.apiBorrowerGetAllWithLoansGet()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**kotlin.collections.List&lt;BasicBorrowerClientWithTagsAndLoans&gt;**](BasicBorrowerClientWithTagsAndLoans.md)

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
val webService = apiClient.createWebservice(BorrowerApi::class.java)
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | 

launch(Dispatchers.IO) {
    val result : kotlin.Boolean = webService.apiBorrowerIdDelete(id)
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**|  | |

### Return type

**kotlin.Boolean**

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
val webService = apiClient.createWebservice(BorrowerApi::class.java)
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | 

launch(Dispatchers.IO) {
    val result : ResponseBorrowerClientDto = webService.apiBorrowerIdGet(id)
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **java.util.UUID**|  | |

### Return type

[**ResponseBorrowerClientDto**](ResponseBorrowerClientDto.md)

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
val webService = apiClient.createWebservice(BorrowerApi::class.java)
val id : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | 
val updateBorrowerClientDtoNewBorrowerClientDto : UpdateBorrowerClientDtoNewBorrowerClientDto =  // UpdateBorrowerClientDtoNewBorrowerClientDto | 

launch(Dispatchers.IO) {
    webService.apiBorrowerIdPut(id, updateBorrowerClientDtoNewBorrowerClientDto)
}
```

### Parameters
| **id** | **java.util.UUID**|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **updateBorrowerClientDtoNewBorrowerClientDto** | [**UpdateBorrowerClientDtoNewBorrowerClientDto**](UpdateBorrowerClientDtoNewBorrowerClientDto.md)|  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure Bearer:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: application/json, text/json, application/*+json
 - **Accept**: Not defined




### Example
```kotlin
// Import classes:
//import org.confiape.loan.*
//import org.confiape.loan.infrastructure.*
//import org.confiape.loan.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(BorrowerApi::class.java)
val borrowerClientDtoNewBorrowerClientDto : BorrowerClientDtoNewBorrowerClientDto =  // BorrowerClientDtoNewBorrowerClientDto | 

launch(Dispatchers.IO) {
    val result : BorrowerClientDto = webService.apiBorrowerPost(borrowerClientDtoNewBorrowerClientDto)
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **borrowerClientDtoNewBorrowerClientDto** | [**BorrowerClientDtoNewBorrowerClientDto**](BorrowerClientDtoNewBorrowerClientDto.md)|  | [optional] |

### Return type

[**BorrowerClientDto**](BorrowerClientDto.md)

### Authorization


Configure Bearer:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: application/json, text/json, application/*+json
 - **Accept**: text/plain, application/json, text/json

