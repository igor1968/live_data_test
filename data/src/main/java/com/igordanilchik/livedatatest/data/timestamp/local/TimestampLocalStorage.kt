package com.igordanilchik.livedatatest.data.timestamp.local

import com.igordanilchik.livedatatest.data.common.timestamp.TimeStamp

/**
 * @author Igor Danilchik
 */
interface TimeStampLocalStorage {
    fun save(timeStamp: TimeStamp)
    fun get(key: String): TimeStamp?
    fun removeAll()
    fun remove(timeStamp: TimeStamp)
    fun remove(key: String)
    fun removeAllKeys(prefix: String)
}
