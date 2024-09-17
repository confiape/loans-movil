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

import org.confiape.loan.models.BasicBorrowerClientWithTags

import com.google.gson.annotations.SerializedName

/**
 * 
 *
 * @param pageSize 
 * @param currentPage 
 * @param orderBy 
 * @param totalItems 
 * @param result 
 */


data class BasicBorrowerClientWithTagsPaginationResponse (

    @SerializedName("pageSize")
    val pageSize: kotlin.Int? = null,

    @SerializedName("currentPage")
    val currentPage: kotlin.Int? = null,

    @SerializedName("orderBy")
    val orderBy: kotlin.collections.List<kotlin.String>? = null,

    @SerializedName("totalItems")
    val totalItems: kotlin.Long? = null,

    @SerializedName("result")
    val result: kotlin.collections.List<BasicBorrowerClientWithTags>? = null

) {


}

