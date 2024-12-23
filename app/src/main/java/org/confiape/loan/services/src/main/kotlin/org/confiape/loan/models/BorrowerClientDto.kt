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
 * @param avatar 
 * @param background 
 * @param name 
 * @param dni 
 * @param dniPath 
 * @param email 
 * @param phoneNumber 
 * @param title 
 * @param company 
 * @param birthday 
 * @param address 
 * @param notes 
 * @param status 
 * @param stringTags 
 */


data class BorrowerClientDto (

    @SerializedName("id")
    val id: java.util.UUID? = null,

    @SerializedName("avatar")
    val avatar: kotlin.String? = null,

    @SerializedName("background")
    val background: kotlin.String? = null,

    @SerializedName("name")
    val name: kotlin.String? = null,

    @SerializedName("dni")
    val dni: kotlin.String? = null,

    @SerializedName("dniPath")
    val dniPath: kotlin.String? = null,

    @SerializedName("email")
    val email: kotlin.String? = null,

    @SerializedName("phoneNumber")
    val phoneNumber: kotlin.String? = null,

    @SerializedName("title")
    val title: kotlin.String? = null,

    @SerializedName("company")
    val company: kotlin.String? = null,

    @SerializedName("birthday")
    val birthday: java.time.OffsetDateTime? = null,

    @SerializedName("address")
    val address: kotlin.String? = null,

    @SerializedName("notes")
    val notes: kotlin.String? = null,

    @SerializedName("status")
    val status: kotlin.String? = null,

    @SerializedName("stringTags")
    val stringTags: kotlin.String? = null

) {


}

