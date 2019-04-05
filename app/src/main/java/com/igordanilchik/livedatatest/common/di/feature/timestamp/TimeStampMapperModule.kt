package com.igordanilchik.livedatatest.common.di.feature.timestamp

import com.igordanilchik.livedatatest.data.common.Mapper
import com.igordanilchik.livedatatest.data.common.timestamp.TimeStamp
import com.igordanilchik.livedatatest.data.timestamp.dto.TimeStampJson
import com.igordanilchik.livedatatest.data.timestamp.mapper.TimeStampJsonMapper
import com.igordanilchik.livedatatest.data.timestamp.mapper.TimeStampMapper
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module
object TimeStampMapperModule {

    @JvmStatic
    @Provides
    fun provideTimeStampMapper(): Mapper<TimeStampJson, TimeStamp> = TimeStampMapper()

    @JvmStatic
    @Provides
    fun provideTimeStampJsonMapper(): Mapper<TimeStamp, TimeStampJson> = TimeStampJsonMapper()
}