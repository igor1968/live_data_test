package com.igordanilchik.livedatatest.data.common.timestamp

import com.igordanilchik.livedatatest.data.common.Constants

/**
 * @author Igor Danilchik
 */
data class TimeStamp(
    @Constants.TimeStampType val key: String,
    val timeInMillis: Long = TimeStampUtils.current
)