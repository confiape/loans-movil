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


import com.google.gson.annotations.SerializedName

/**
 * 
 *
 * @param id 
 * @param changeBy 
 * @param name 
 * @param title 
 * @param tagId 
 * @param loanDate 
 * @param amount 
 * @param payment 
 * @param isYape 
 */


data class ReportPaymentsDto (

    @SerializedName("id")
    val id: java.util.UUID? = null,

    @SerializedName("changeBy")
    val changeBy: java.util.UUID? = null,

    @SerializedName("name")
    val name: kotlin.String? = null,

    @SerializedName("title")
    val title: kotlin.String? = null,

    @SerializedName("tagId")
    val tagId: java.util.UUID? = null,

    @SerializedName("loanDate")
    val loanDate: java.time.OffsetDateTime? = null,

    @SerializedName("amount")
    val amount: kotlin.Double? = null,

    @SerializedName("payment")
    val payment: kotlin.Double? = null,

    @SerializedName("isYape")
    val isYape: kotlin.Boolean? = null

) {


}

