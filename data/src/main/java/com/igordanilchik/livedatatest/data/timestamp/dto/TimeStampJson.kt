package com.igordanilchik.livedatatest.data.timestamp.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author Igor Danilchik
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class TimeStampJson @JsonCreator constructor(
    @field:JsonProperty("key") val key: String,
    @field:JsonProperty("timeStamp") val timeInMillis: Long
)