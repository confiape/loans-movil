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

import org.confiape.loan.models.TagBorrowerClient

import com.google.gson.annotations.SerializedName

/**
 * 
 *
 * @param id 
 * @param changeBy 
 * @param changeDate 
 * @param isDeleted 
 * @param title 
 * @param person 
 */


data class Tag (

    @SerializedName("id")
    val id: java.util.UUID? = null,

    @SerializedName("changeBy")
    val changeBy: java.util.UUID? = null,

    @SerializedName("changeDate")
    val changeDate: java.time.OffsetDateTime? = null,

    @SerializedName("isDeleted")
    val isDeleted: kotlin.Boolean? = null,

    @SerializedName("title")
    val title: kotlin.String? = null,

    @SerializedName("person")
    val person: kotlin.collections.List<TagBorrowerClient>? = null

) {


}

