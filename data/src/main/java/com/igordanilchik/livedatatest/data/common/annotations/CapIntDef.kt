package com.igordanilchik.livedatatest.data.common.annotations

/**
 * @author Igor Danilchik
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class CapIntDef(
    vararg val value: Int = [],
    val flag: Boolean = false
)