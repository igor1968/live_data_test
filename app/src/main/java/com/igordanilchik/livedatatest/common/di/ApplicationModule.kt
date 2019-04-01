package com.igordanilchik.livedatatest.common.di

import android.app.Application
import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.igordanilchik.livedatatest.common.factory.FragmentFactory
import com.igordanilchik.livedatatest.common.factory.ObjectMapperFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, ViewModelFactoryModule::class])
class ApplicationModule(val context: Application) {

    @Provides
    @Singleton
    internal fun provideContext(): Context = context.applicationContext

    @Provides
    @Singleton
    internal fun provideApplication(): Application = context

    @Provides
    @Singleton
    internal fun provideObjectMapper(): ObjectMapper = ObjectMapperFactory.newInstance()

    @Provides
    @Singleton
    internal fun provideFragmentFactory(): FragmentFactory = FragmentFactory
}
