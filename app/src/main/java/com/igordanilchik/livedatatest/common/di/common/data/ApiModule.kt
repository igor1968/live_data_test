package com.igordanilchik.livedatatest.common.di.common.data

import com.igordanilchik.livedatatest.data.common.api.ClientApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * @author Igor Danilchik
 */
@Module
object ApiModule {

    @JvmStatic
    @Provides
    fun provideCasApi(
        retrofit: Retrofit
    ): ClientApi =
        retrofit.create(ClientApi::class.java)
}