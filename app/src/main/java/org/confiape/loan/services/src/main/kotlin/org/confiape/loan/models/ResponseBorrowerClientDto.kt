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

import org.confiape.loan.models.BasicLoanInformation
import org.confiape.loan.models.BorrowerClientDto
import org.confiape.loan.models.TagDto

import com.google.gson.annotations.SerializedName

/**
 * 
 *
 * @param tagDto 
 * @param borrowerClientDto 
 * @param basicLoanInformation 
 */


data class ResponseBorrowerClientDto (

    @SerializedName("tagDto")
    val tagDto: kotlin.collections.List<TagDto>? = null,

    @SerializedName("borrowerClientDto")
    val borrowerClientDto: BorrowerClientDto? = null,

    @SerializedName("basicLoanInformation")
    val basicLoanInformation: kotlin.collections.List<BasicLoanInformation>? = null

) {


}

