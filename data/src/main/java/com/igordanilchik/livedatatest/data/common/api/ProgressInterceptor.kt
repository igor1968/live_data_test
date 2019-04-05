package com.igordanilchik.livedatatest.data.common.api

import com.igordanilchik.livedatatest.data.common.logger.CapLogger
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.Okio
import okio.Source
import java.io.IOException

/**
 * @author Igor Danilchik
 */
class ProgressInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        return originalResponse.newBuilder()
            .body(originalResponse.body()?.let {
                ProgressResponseBody(
                    it,
                    progressListener
                )
            })
            .build()
    }

    private val progressListener = object :
        ProgressListener {

        override fun update(bytesRead: Long, contentLength: Long, done: Boolean) {
            update(bytesRead, done)
            CapLogger.d("Content length: $contentLength%s")
            CapLogger.d("${100 * bytesRead / contentLength}%% done\n")
        }

        override fun update(bytesRead: Long, done: Boolean) {
            CapLogger.d("Bytes read: $bytesRead")
            CapLogger.d("Done: $done")
        }
    }

    private class ProgressResponseBody internal constructor(
        private val responseBody: ResponseBody,
        private val progressListener: ProgressListener
    ) : ResponseBody() {

        private val bufferedSource: BufferedSource by lazy { Okio.buffer(source(responseBody.source())) }

        override fun contentType(): MediaType? = responseBody.contentType()

        override fun contentLength(): Long = responseBody.contentLength()

        override fun source(): BufferedSource = bufferedSource

        private fun source(source: Source): Source {
            return object : ForwardingSource(source) {
                var totalBytesRead = 0L

                @Throws(IOException::class)
                override fun read(sink: Buffer, byteCount: Long): Long {
                    val bytesRead = super.read(sink, byteCount)
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += if (bytesRead != -1L) bytesRead else 0L
                    if (responseBody.contentLength() > 0) {
                        progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1L)
                    } else {
                        progressListener.update(totalBytesRead, bytesRead == -1L)
                    }
                    return bytesRead
                }
            }
        }
    }

    internal interface ProgressListener {
        //Known content length
        fun update(bytesRead: Long, contentLength: Long, done: Boolean)

        //Unknown content length
        fun update(bytesRead: Long, done: Boolean)
    }
}