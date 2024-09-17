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

import org.confiape.loan.models.PointDto

import com.google.gson.annotations.SerializedName

/**
 * 
 *
 * @param loanId 
 * @param amount 
 * @param location 
 */


data class NewPaymentDto (

    @SerializedName("loanId")
    val loanId: java.util.UUID? = null,

    @SerializedName("amount")
    val amount: kotlin.Double? = null,

    @SerializedName("location")
    val location: PointDto? = null

) {


}

