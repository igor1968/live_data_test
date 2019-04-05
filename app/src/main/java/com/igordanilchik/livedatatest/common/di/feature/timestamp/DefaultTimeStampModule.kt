package com.igordanilchik.livedatatest.common.di.feature.timestamp

import com.igordanilchik.livedatatest.common.di.common.Private
import com.igordanilchik.livedatatest.data.common.Mapper
import com.igordanilchik.livedatatest.data.common.prefs.AppPreferences
import com.igordanilchik.livedatatest.data.common.timestamp.TimeStamp
import com.igordanilchik.livedatatest.data.timestamp.dto.TimeStampJson
import com.igordanilchik.livedatatest.data.timestamp.local.PreferenceTimeStampStorage
import com.igordanilchik.livedatatest.data.timestamp.local.TimeStampLocalStorage
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module(includes = [TimeStampMapperModule::class])
object DefaultTimeStampModule {

    @Private
    @JvmStatic
    @Provides
    fun provideTimeStampStorage(
        appPreference: AppPreferences,
        timeStampMapper: Mapper<TimeStampJson, TimeStamp>,
        timeStampJsonMapper: Mapper<TimeStamp, TimeStampJson>
    ): TimeStampLocalStorage =
        PreferenceTimeStampStorage(
            appPreference,
            timeStampMapper,
            timeStampJsonMapper
        )
}