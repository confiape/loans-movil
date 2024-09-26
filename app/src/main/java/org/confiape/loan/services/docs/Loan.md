
# Loan

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) |  |  [optional] |
| **changeBy** | [**java.util.UUID**](java.util.UUID.md) |  |  [optional] |
| **changeDate** | [**java.time.OffsetDateTime**](java.time.OffsetDateTime.md) |  |  [optional] |
| **isDeleted** | **kotlin.Boolean** |  |  [optional] |
| **amount** | **kotlin.Double** |  |  [optional] |
| **interest** | **kotlin.Double** |  |  [optional] |
| **dateTime** | [**java.time.OffsetDateTime**](java.time.OffsetDateTime.md) |  |  [optional] |
| **numberDate** | **kotlin.Int** |  |  [optional] |
| **loanType** | [**LoanType**](LoanType.md) |  |  [optional] |
| **suggestedDays** | **kotlin.collections.List&lt;kotlin.String&gt;** |  |  [optional] |
| **borrowerClientId** | [**java.util.UUID**](java.util.UUID.md) |  |  [optional] |
| **borrowerClient** | [**BorrowerClient**](BorrowerClient.md) |  |  [optional] |
| **payments** | [**kotlin.collections.List&lt;Payment&gt;**](Payment.md) |  |  [optional] |
| **currentLocation** | [**PointDto**](PointDto.md) |  |  [optional] |



