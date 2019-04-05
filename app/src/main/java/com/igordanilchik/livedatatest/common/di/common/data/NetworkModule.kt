package com.igordanilchik.livedatatest.common.di.common.data

import com.igordanilchik.livedatatest.common.di.common.data.LoggingInterceptorsModule.HTTP_LOGGING
import com.igordanilchik.livedatatest.common.di.common.data.LoggingInterceptorsModule.PROGRESS
import com.igordanilchik.livedatatest.data.service.EndpointProvider
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author Igor Danilchik
 */
@Module(
    includes = [
        LoggingInterceptorsModule::class
    ]
)
object NetworkModule {

    private const val STUB_URL = "https://example.com"

    @JvmStatic
    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        endpointProvider: EndpointProvider,
        rxJavaAdapterFactory: RxJava2CallAdapterFactory,
        jacksonConverterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(jacksonConverterFactory)
        .addCallAdapterFactory(rxJavaAdapterFactory)
        .baseUrl(STUB_URL)//required to build default Retrofit instance
        .build()

    @JvmStatic
    @Singleton
    @Provides
    fun provideOkHttpClient(
        @Named(HTTP_LOGGING) loggingInterceptor: Interceptor,
        @Named(PROGRESS) networkInterceptor: Interceptor
    ): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()

        okHttpBuilder.addInterceptor(loggingInterceptor)
        okHttpBuilder.addNetworkInterceptor(networkInterceptor)

        okHttpBuilder.connectionSpecs(
            listOf(
                ConnectionSpec.CLEARTEXT,
                ConnectionSpec.MODERN_TLS
            )
        )

        return okHttpBuilder.build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory =
        SimpleXmlConverterFactory.create()

    @JvmStatic
    @Singleton
    @Provides
    fun provideRxJavaAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory
        .createAsync()
}