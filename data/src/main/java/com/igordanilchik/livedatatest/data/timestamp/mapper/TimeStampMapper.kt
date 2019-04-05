package com.igordanilchik.livedatatest.data.timestamp.mapper

import com.igordanilchik.livedatatest.data.common.Mapper
import com.igordanilchik.livedatatest.data.common.timestamp.TimeStamp
import com.igordanilchik.livedatatest.data.timestamp.dto.TimeStampJson

/**
 * @author Igor Danilchik
 */
class TimeStampMapper : Mapper<TimeStampJson, TimeStamp> {

    override fun map(
        from: TimeStampJson
    ): TimeStamp = from.let {
        TimeStamp(
            it.key,
            it.timeInMillis
        )
    }
}