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

import org.confiape.loan.models.SimpleLoanDto

import com.google.gson.annotations.SerializedName

/**
 * 
 *
 * @param id 
 * @param name 
 * @param tags 
 * @param loans 
 */


data class BasicBorrowerClientWithTagsAndLoans (

    @SerializedName("id")
    val id: java.util.UUID? = null,

    @SerializedName("name")
    val name: kotlin.String? = null,

    @SerializedName("tags")
    val tags: kotlin.collections.List<kotlin.String>? = null,

    @SerializedName("loans")
    val loans: kotlin.collections.List<SimpleLoanDto>? = null

) {


}

