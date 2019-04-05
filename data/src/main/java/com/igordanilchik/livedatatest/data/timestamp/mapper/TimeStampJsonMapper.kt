package com.igordanilchik.livedatatest.data.timestamp.mapper

import com.igordanilchik.livedatatest.data.common.Mapper
import com.igordanilchik.livedatatest.data.common.timestamp.TimeStamp
import com.igordanilchik.livedatatest.data.timestamp.dto.TimeStampJson

/**
 * @author Igor Danilchik
 */
class TimeStampJsonMapper : Mapper<TimeStamp, TimeStampJson> {

    override fun map(
        from: TimeStamp
    ): TimeStampJson = from.let {
        TimeStampJson(
            it.key,
            it.timeInMillis
        )
    }
}