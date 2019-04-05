package com.igordanilchik.livedatatest.data.catalogue.dto.json

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author Igor Danilchik
 */
data class OfferJson(
    @JsonProperty("id") val id: Int,
    @JsonProperty("url") val url: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("price") val price: String,
    @JsonProperty("description") val description: String?,
    @JsonProperty("pictureUrl") val pictureUrl: String?,
    @JsonProperty("categoryId") val categoryId: Int,
    @JsonProperty("properties") val parameters: List<Param>?
) {
    data class Param(
        @JsonProperty("name") val name: String,
        @JsonProperty("value") val value: String
    )
}
