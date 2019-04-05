package com.igordanilchik.livedatatest.data.common.logger

/**
 * @author Igor Danilchik
 */
object CapLogger {

    private var delegate: Logger? = null

    private val logger: Logger
        get() = delegate ?: DumbLogger

    @JvmStatic
    fun setDelegate(logger: Logger) {
        delegate = logger
    }

    @JvmStatic
    fun tag(tag: String): Logger = logger.tag(tag)

    @JvmStatic
    fun v(message: String?, vararg args: Any?) = logger.v(message, *args)

    @JvmStatic
    fun v(throwable: Throwable?, message: String?, vararg args: Any?)
            = logger.v(throwable, message, *args)

    @JvmStatic
    fun v(throwable: Throwable?) = logger.v(throwable)

    @JvmStatic
    fun d(message: String?, vararg args: Any?) = logger.d(message, *args)

    @JvmStatic
    fun d(throwable: Throwable?, message: String?, vararg args: Any?)
            = logger.d(throwable, message, *args)

    @JvmStatic
    fun d(throwable: Throwable?) = logger.d(throwable)

    @JvmStatic
    fun i(message: String?, vararg args: Any?) = logger.i(message, *args)

    @JvmStatic
    fun i(throwable: Throwable?, message: String?, vararg args: Any?)
            = logger.i(throwable, message, *args)

    @JvmStatic
    fun i(throwable: Throwable?) = logger.i(throwable)

    @JvmStatic
    fun w(message: String?, vararg args: Any?) = logger.w(message, *args)

    @JvmStatic
    fun w(throwable: Throwable?, message: String?, vararg args: Any?)
            = logger.w(throwable, message, *args)

    @JvmStatic
    fun w(throwable: Throwable?) = logger.w(throwable)

    @JvmStatic
    fun e(message: String?, vararg args: Any?) = logger.e(message, *args)

    @JvmStatic
    fun e(throwable: Throwable?, message: String?, vararg args: Any = emptyArray())
            = logger.e(throwable, message, *args)

    @JvmStatic
    fun e(throwable: Throwable?) = logger.e(throwable)
}
