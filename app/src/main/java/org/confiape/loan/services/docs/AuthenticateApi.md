# AuthenticateApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiAuthenticateAssignRoleToUserIdPost**](AuthenticateApi.md#apiAuthenticateAssignRoleToUserIdPost) | **POST** api/Authenticate/AssignRoleToUser/{id} |  |
| [**apiAuthenticateGetAuthorizationTokenPost**](AuthenticateApi.md#apiAuthenticateGetAuthorizationTokenPost) | **POST** api/Authenticate/GetAuthorizationToken |  |
| [**apiAuthenticateLogInPost**](AuthenticateApi.md#apiAuthenticateLogInPost) | **POST** api/Authenticate/LogIn |  |
| [**apiAuthenticateLogOutGet**](AuthenticateApi.md#apiAuthenticateLogOutGet) | **GET** api/Authenticate/LogOut |  |
| [**apiAuthenticateLoginWithAuthenticationTokenPost**](AuthenticateApi.md#apiAuthenticateLoginWithAuthenticationTokenPost) | **POST** api/Authenticate/LoginWithAuthenticationToken |  |
| [**apiAuthenticateLoginWithGoogleTokenPost**](AuthenticateApi.md#apiAuthenticateLoginWithGoogleTokenPost) | **POST** api/Authenticate/LoginWithGoogleToken |  |





### Example
```kotlin
// Import classes:
//import org.confiape.loan.*
//import org.confiape.loan.infrastructure.*
//import org.confiape.loan.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(AuthenticateApi::class.java)
val id : kotlin.String = id_example // kotlin.String | 
val requestBody : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | 

launch(Dispatchers.IO) {
    webService.apiAuthenticateAssignRoleToUserIdPost(id, requestBody)
}
```

### Parameters
| **id** | **kotlin.String**|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **requestBody** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)|  | [optional] |

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
val webService = apiClient.createWebservice(AuthenticateApi::class.java)

launch(Dispatchers.IO) {
    val result : LoginResponse = webService.apiAuthenticateGetAuthorizationTokenPost()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**LoginResponse**](LoginResponse.md)

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
val webService = apiClient.createWebservice(AuthenticateApi::class.java)
val loginDto : LoginDto =  // LoginDto | 

launch(Dispatchers.IO) {
    webService.apiAuthenticateLogInPost(loginDto)
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **loginDto** | [**LoginDto**](LoginDto.md)|  | [optional] |

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
val webService = apiClient.createWebservice(AuthenticateApi::class.java)

launch(Dispatchers.IO) {
    webService.apiAuthenticateLogOutGet()
}
```

### Parameters
This endpoint does not need any parameter.

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
val webService = apiClient.createWebservice(AuthenticateApi::class.java)

launch(Dispatchers.IO) {
    webService.apiAuthenticateLoginWithAuthenticationTokenPost()
}
```

### Parameters
This endpoint does not need any parameter.

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
val webService = apiClient.createWebservice(AuthenticateApi::class.java)
val tokenDto : TokenDto =  // TokenDto | 

launch(Dispatchers.IO) {
    webService.apiAuthenticateLoginWithGoogleTokenPost(tokenDto)
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **tokenDto** | [**TokenDto**](TokenDto.md)|  | [optional] |

### Return type

null (empty response body)

### Authorization


Configure Bearer:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: application/json, text/json, application/*+json
 - **Accept**: Not defined

