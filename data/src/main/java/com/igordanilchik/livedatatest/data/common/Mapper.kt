package com.igordanilchik.livedatatest.data.common

/**
 * @author Igor Danilchik
 */
interface Mapper<F, T> {
    fun map(from: F): T
}