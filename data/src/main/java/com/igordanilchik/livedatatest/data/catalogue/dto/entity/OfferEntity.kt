package com.igordanilchik.livedatatest.data.catalogue.dto.entity

/**
 * @author Igor Danilchik
 */
data class OfferEntity(
    val id: Int,
    val url: String,
    val name: String,
    val price: String,
    val description: String?,
    val pictureUrl: String?,
    val categoryId: Int,
    val properties: List<Property>?
) {

    data class Property(
        val name: String,
        val value: String
    )
}

fun OfferEntity.getParamByKey(key: String): String? = this.properties?.find { it.name == key }?.value

