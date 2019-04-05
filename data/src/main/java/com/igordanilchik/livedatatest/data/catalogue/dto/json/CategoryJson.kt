package com.igordanilchik.livedatatest.data.catalogue.dto.json

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author Igor Danilchik
 */
data class CategoryJson(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("pictureUrl") val pictureUrl: String?
)
