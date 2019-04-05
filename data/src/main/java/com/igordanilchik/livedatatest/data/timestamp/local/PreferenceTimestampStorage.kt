package com.igordanilchik.livedatatest.data.timestamp.local

import com.igordanilchik.livedatatest.data.common.Mapper
import com.igordanilchik.livedatatest.data.common.prefs.AppPreferences
import com.igordanilchik.livedatatest.data.common.timestamp.TimeStamp
import com.igordanilchik.livedatatest.data.timestamp.dto.TimeStampJson

/**
 * @author Igor Danilchik
 */
const val KEY_LOCAL_TIME_STAMP_DATA = "key_local_time_stamp_data_"

class PreferenceTimeStampStorage(
    private val preference: AppPreferences,
    private val timeStampMapper: Mapper<TimeStampJson, TimeStamp>,
    private val timeStampJsonMapper: Mapper<TimeStamp, TimeStampJson>
) : TimeStampLocalStorage {

    override fun save(timeStamp: TimeStamp) {
        preference.putJSON(
            KEY_LOCAL_TIME_STAMP_DATA + timeStamp.key,
            timeStampJsonMapper.map(timeStamp)
        )
    }

    override fun get(key: String): TimeStamp? {
        val timeStampJson = preference.getJSON(
            KEY_LOCAL_TIME_STAMP_DATA + key,
            TimeStampJson::class.java
        )

        return timeStampJson?.let { timeStampMapper.map(it) }
    }

    override fun removeAll() = preference.removeAllKeys(KEY_LOCAL_TIME_STAMP_DATA)

    override fun remove(timeStamp: TimeStamp) = preference.remove(KEY_LOCAL_TIME_STAMP_DATA + timeStamp.key)

    override fun remove(key: String) = preference.remove(KEY_LOCAL_TIME_STAMP_DATA + key)

    override fun removeAllKeys(prefix: String) = preference.removeAllKeys(KEY_LOCAL_TIME_STAMP_DATA + prefix)
}
