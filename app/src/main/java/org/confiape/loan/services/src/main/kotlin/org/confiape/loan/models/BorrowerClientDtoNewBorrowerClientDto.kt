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

import org.confiape.loan.models.BorrowerClientDto

import com.google.gson.annotations.SerializedName

/**
 * 
 *
 * @param borrowerClientDto 
 * @param tagIdList 
 */


data class BorrowerClientDtoNewBorrowerClientDto (

    @SerializedName("borrowerClientDto")
    val borrowerClientDto: BorrowerClientDto? = null,

    @SerializedName("tagIdList")
    val tagIdList: kotlin.collections.List<java.util.UUID>? = null

) {


}

