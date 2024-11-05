# FileApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiFilePost**](FileApi.md#apiFilePost) | **POST** api/File |  |





### Example
```kotlin
// Import classes:
//import org.confiape.loan.*
//import org.confiape.loan.infrastructure.*
//import org.confiape.loan.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(FileApi::class.java)
val file : java.io.File = BINARY_DATA_HERE // java.io.File | 

launch(Dispatchers.IO) {
    webService.apiFilePost(file)
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **file** | **java.io.File**|  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure Bearer:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: Not defined

