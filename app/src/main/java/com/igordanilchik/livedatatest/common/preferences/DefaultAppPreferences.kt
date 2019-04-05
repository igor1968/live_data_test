package com.igordanilchik.livedatatest.common.preferences

import android.content.SharedPreferences
import com.fasterxml.jackson.databind.ObjectMapper
import com.igordanilchik.livedatatest.data.common.logger.CapLogger
import com.igordanilchik.livedatatest.data.common.prefs.AppPreferences

/**
 * @author Igor Danilchik
 */
class DefaultAppPreferences(
    private val preferences: SharedPreferences,
    private val objectMapper: ObjectMapper
) : AppPreferences {

    override fun getString(key: String, def: String): String =
        try {
            preferences.getString(key, def) ?: def
        } catch (e: Exception) {
            CapLogger.e(e, "Cannot get string for key: %s from prefs", key)
            def
        }

    override fun putJSON(key: String, value: Any): AppPreferences {
        try {
            preferences.edit().putString(key, objectMapper.writeValueAsString(value)).apply()
        } catch (e: Exception) {
            CapLogger.e(e, "Cannot save object: %s with key: %s to prefs", value, key)
        }

        return this
    }

    override fun <T> getJSON(key: String, cls: Class<T>): T? {
        val jsonString = getString(key, "")
        if (jsonString.isNotEmpty()) {
            try {
                return objectMapper.readValue<T>(jsonString, cls)
            } catch (e: Exception) {
                CapLogger.e(e, "Cannot load object with key = %s from prefs", key)
            }
        }
        return null
    }

    override fun remove(key: String) {
        try {
            preferences.edit().remove(key).apply()
        } catch (e: Exception) {
            CapLogger.e(e, "Cannot remove key = %s from prefs", key)
        }
    }

    override fun <T> getAllObjects(prefix: String, clazz: Class<T>): List<T> {
        try {
            val result = ArrayList<T>()

            for (key in getAllKeys(prefix)) {
                getJSON(key, clazz)?.let { result.add(it) }
            }

            return result
        } catch (e: Exception) {
            CapLogger.e(e, "Cannot get all objects with prefix: = %s from prefs", prefix)
        }

        return emptyList()
    }

    private fun getAllKeys(prefix: String): Set<String> {
        try {
            if (prefix.isNotEmpty()) {
                val allKeys = HashSet<String>()

                for (key in preferences.all.keys) {
                    if (key.startsWith(prefix)) {
                        allKeys.add(key)
                    }
                }

                return allKeys
            }
        } catch (e: Exception) {
            CapLogger.e(e, "Cannot get all keys with prefix: = %s from prefs", prefix)
        }

        return emptySet()
    }

    override fun removeAllKeys(prefix: String) {
        for (key in getAllKeys(prefix)) {
            remove(key)
        }
    }
}