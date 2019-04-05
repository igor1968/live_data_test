package com.igordanilchik.livedatatest.data.common.prefs

/**
 * @author Igor Danilchik
 */
interface AppPreferences {
    fun putJSON(key: String, value: Any): AppPreferences
    fun <T> getJSON(key: String, cls: Class<T>): T?
    fun remove(key: String)
    fun getString(key: String, def: String): String
    fun <T> getAllObjects(prefix: String, clazz: Class<T>): List<T>
    fun removeAllKeys(prefix: String)
}
