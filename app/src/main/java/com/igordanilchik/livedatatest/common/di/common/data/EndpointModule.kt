package com.igordanilchik.livedatatest.common.di.common.data

import com.igordanilchik.livedatatest.data.service.DefaultEndpointProvider
import com.igordanilchik.livedatatest.data.service.EndpointProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Igor Danilchik
 */
@Module
object EndpointModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideEndpoint(): EndpointProvider = DefaultEndpointProvider()
}