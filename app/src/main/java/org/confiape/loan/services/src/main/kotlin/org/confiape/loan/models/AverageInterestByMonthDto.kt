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
 * @param year 
 * @param month 
 * @param averageInterest 
 */


data class AverageInterestByMonthDto (

    @SerializedName("year")
    val year: kotlin.Int? = null,

    @SerializedName("month")
    val month: kotlin.Int? = null,

    @SerializedName("averageInterest")
    val averageInterest: kotlin.Double? = null

) {


}

