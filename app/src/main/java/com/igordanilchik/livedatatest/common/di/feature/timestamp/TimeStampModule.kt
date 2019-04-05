package com.igordanilchik.livedatatest.common.di.feature.timestamp

import com.igordanilchik.livedatatest.data.timestamp.local.TimeStampLocalStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Igor Danilchik
 */
@Module(subcomponents = [TimeStampComponent::class])
object TimeStampModule {

    @JvmStatic
    @Singleton
    @Provides
    internal fun provideStorage(
        componentBuilder: TimeStampComponent.Builder
    ): TimeStampLocalStorage = componentBuilder
        .build()
        .storage()
}