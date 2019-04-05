package com.igordanilchik.livedatatest.data.common.logger

/**
 * @author Igor Danilchik
 */
object DumbLogger: Logger {

    override fun tag(tag: String): Logger = DumbLogger

    override fun v(message: String?, vararg args: Any?) {}

    override fun v(throwable: Throwable?, message: String?, vararg args: Any?) {}

    override fun v(throwable: Throwable?) {}

    override fun d(message: String?, vararg args: Any?) {}

    override fun d(throwable: Throwable?, message: String?, vararg args: Any?) {}

    override fun d(throwable: Throwable?) {}

    override fun i(message: String?, vararg args: Any?) {}

    override fun i(throwable: Throwable?, message: String?, vararg args: Any?) {}

    override fun i(throwable: Throwable?) {}

    override fun w(message: String?, vararg args: Any?) {}

    override fun w(throwable: Throwable?, message: String?, vararg args: Any?) {}

    override fun w(throwable: Throwable?) {}

    override fun e(message: String?, vararg args: Any?) {}

    override fun e(throwable: Throwable?, message: String?, vararg args: Any?) {}

    override fun e(throwable: Throwable?) {}

}
