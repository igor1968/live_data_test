package com.igordanilchik.livedatatest.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * @author Igor Danilchik
 */
fun <T> LiveData<T>.filter(func: (T) -> Boolean): LiveData<T> {
    val result = MediatorLiveData<T>()
    result.addSource(this) { if (func(it as T)) result.value = it }
    return result
}
