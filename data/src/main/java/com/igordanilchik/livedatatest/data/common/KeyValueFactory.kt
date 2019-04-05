package com.igordanilchik.livedatatest.data.common

/**
 * @author Igor Danilchik
 */
interface KeyValueFactory<KEY, VALUE> {
    fun getInstance(key: KEY): VALUE
}