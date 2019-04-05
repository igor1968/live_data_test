package com.igordanilchik.livedatatest.common.di.common.data

import com.igordanilchik.livedatatest.data.common.api.ProgressInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author Igor Danilchik
 */
@Module
object LoggingInterceptorsModule {

    const val PROGRESS = "PROGRESS"
    const val HTTP_LOGGING = "HTTP_LOGGING"

    @Named(HTTP_LOGGING)
    @JvmStatic
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
    }

    @Named(PROGRESS)
    @JvmStatic
    @Singleton
    @Provides
    fun provideStethoInterceptor(): Interceptor = ProgressInterceptor()
}