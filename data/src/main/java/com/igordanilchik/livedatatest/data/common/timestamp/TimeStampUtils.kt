package com.igordanilchik.livedatatest.data.common.timestamp

import com.igordanilchik.livedatatest.data.common.Constants.ValidationTimeType
import java.util.Calendar

/**
 * @author Igor Danilchik
 */
object TimeStampUtils {

    private const val DEFAULT_CACHE_VALID_HOURS = 24

    @JvmStatic
    fun isValid(time: Long, referenceTime: Int, @ValidationTimeType validationTimeType: Byte): Boolean {
        val calendar = Calendar.getInstance()
        val calendarStartTimeStamp = Calendar.getInstance()
        val calendarCurrentTimeStamp = Calendar.getInstance()
        //Time when cache was saved
        calendarStartTimeStamp.timeInMillis = time

        //Time when cache was saved
        calendarCurrentTimeStamp.timeInMillis = time
        //Time when cache should expire
        calendarCurrentTimeStamp.add(validationTimeType.toInt(), referenceTime)

        return calendar.before(calendarCurrentTimeStamp) && !calendar.before(calendarStartTimeStamp)
    }

    @JvmStatic
    @JvmOverloads
    fun isValid(time: Long, hoursValid: Int = DEFAULT_CACHE_VALID_HOURS): Boolean {
        val calendar = Calendar.getInstance()
        val calendarTimeStamp = Calendar.getInstance()

        calendarTimeStamp.timeInMillis = time
        calendarTimeStamp.add(Calendar.HOUR_OF_DAY, hoursValid)

        return calendar.before(calendarTimeStamp)
    }

    @JvmStatic
    val current: Long
        get() = Calendar.getInstance().timeInMillis
}
