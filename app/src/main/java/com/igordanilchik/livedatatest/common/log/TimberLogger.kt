package com.igordanilchik.livedatatest.common.log

import com.igordanilchik.livedatatest.data.common.logger.Logger
import timber.log.Timber

/**
 * @author Igor Danilchik
 */
class TimberLogger : Logger {

    private val tagLocal = ThreadLocal<String>()

    private fun pushTag(tag: String) = tagLocal.set(tag)

    private fun popTag(): String? {
        val tag = tagLocal.get()
        tagLocal.remove()
        return tag
    }

    override fun tag(tag: String): Logger {
        pushTag(tag)
        return this
    }

    override fun v(message: String?, vararg args: Any?) {
        Timber.tag(popTag()).v(message, *args)
    }

    override fun v(throwable: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(popTag()).v(throwable, message, *args)
    }

    override fun v(throwable: Throwable?) {
        Timber.tag(popTag()).v(throwable)
    }

    override fun d(message: String?, vararg args: Any?) {
        Timber.tag(popTag()).d(message, *args)
    }

    override fun d(throwable: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(popTag()).d(throwable, message, *args)
    }

    override fun d(throwable: Throwable?) {
        Timber.tag(popTag()).d(throwable)
    }

    override fun i(message: String?, vararg args: Any?) {
        Timber.tag(popTag()).i(message, *args)
    }

    override fun i(throwable: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(popTag()).i(throwable, message, *args)
    }

    override fun i(throwable: Throwable?) {
        Timber.tag(popTag()).i(throwable)
    }

    override fun w(message: String?, vararg args: Any?) {
        Timber.tag(popTag()).w(message, *args)
    }

    override fun w(throwable: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(popTag()).w(throwable, message, *args)
    }

    override fun w(throwable: Throwable?) {
        Timber.tag(popTag()).w(throwable)
    }

    override fun e(message: String?, vararg args: Any?) {
        Timber.tag(popTag()).e(message, *args)
    }

    override fun e(throwable: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(popTag()).e(throwable, message, *args)
    }

    override fun e(throwable: Throwable?) {
        Timber.tag(popTag()).e(throwable)
    }

    companion object {
        /**
         * Plant proper Timber for Logging for Debug
         * or Sending Errors as Crashes for Release
         * Pretends as Fail!
         */
        @JvmStatic
        fun initTimber(isDebug: Boolean) =
            if (isDebug) {
                Timber.plant(Timber.DebugTree())
            } else {
                Timber.plant(ReleaseTree())
            }
    }
}