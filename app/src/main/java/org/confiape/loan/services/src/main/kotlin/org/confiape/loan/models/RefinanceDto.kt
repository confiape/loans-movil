/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package org.confiape.loan.models

import org.confiape.loan.models.LoanType

import com.google.gson.annotations.SerializedName

/**
 * 
 *
 * @param loanId 
 * @param amount 
 * @param interest 
 * @param numberDate 
 * @param suggestedDays 
 * @param loanType 
 */


data class RefinanceDto (

    @SerializedName("loanId")
    val loanId: java.util.UUID? = null,

    @SerializedName("amount")
    val amount: kotlin.Double? = null,

    @SerializedName("interest")
    val interest: kotlin.Double? = null,

    @SerializedName("numberDate")
    val numberDate: kotlin.Int? = null,

    @SerializedName("suggestedDays")
    val suggestedDays: kotlin.collections.List<kotlin.String>? = null,

    @SerializedName("loanType")
    val loanType: LoanType? = null

) {


}

