package com.igordanilchik.livedatatest.common.di.common.data

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.fasterxml.jackson.databind.ObjectMapper
import com.igordanilchik.livedatatest.common.preferences.DefaultAppPreferences
import com.igordanilchik.livedatatest.data.common.prefs.AppPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Igor Danilchik
 */
@Module
object PreferencesModule {

    @JvmStatic
    @Singleton
    @Provides
    fun preferences(sharedPreferences: SharedPreferences, objectMapper: ObjectMapper): AppPreferences =
        DefaultAppPreferences(sharedPreferences, objectMapper)

    @JvmStatic
    @Singleton
    @Provides
    fun provideDefaultPlatformPreferences(application: Application): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(application.applicationContext)

}