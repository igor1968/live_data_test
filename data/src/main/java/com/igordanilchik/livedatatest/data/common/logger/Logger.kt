package com.igordanilchik.livedatatest.data.common.logger

/**
 * @author Igor Danilchik
 */
interface Logger {

    fun tag(tag: String): Logger

    /** Log a verbose message with optional format args.  */
    fun v(message: String?, vararg args: Any?)

    /** Log a verbose exception and a message with optional format args.  */
    fun v(throwable: Throwable?, message: String?, vararg args: Any?)

    /** Log a verbose exception.  */
    fun v(throwable: Throwable?)

    /** Log a debug message with optional format args.  */
    fun d(message: String?, vararg args: Any?)

    /** Log a debug exception and a message with optional format args.  */
    fun d(throwable: Throwable?, message: String?, vararg args: Any?)

    /** Log a debug exception.  */
    fun d(throwable: Throwable?)

    /** Log an info message with optional format args.  */
    fun i(message: String?, vararg args: Any?)

    /** Log an info exception and a message with optional format args.  */
    fun i(throwable: Throwable?, message: String?, vararg args: Any?)

    /** Log an info exception.  */
    fun i(throwable: Throwable?)

    /** Log a warning message with optional format args.  */
    fun w(message: String?, vararg args: Any?)

    /** Log a warning exception and a message with optional format args.  */
    fun w(throwable: Throwable?, message: String?, vararg args: Any?)

    /** Log a warning exception.  */
    fun w(throwable: Throwable?)

    /** Log an error message with optional format args.  */
    fun e(message: String?, vararg args: Any?)

    /** Log an error exception and a message with optional format args.  */
    fun e(throwable: Throwable?, message: String?, vararg args: Any?)

    /** Log an error exception.  */
    fun e(throwable: Throwable?)
}
