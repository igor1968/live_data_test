package com.igordanilchik.livedatatest.common.di

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.igordanilchik.livedatatest.common.preferences.AppPreferences
import com.igordanilchik.livedatatest.common.preferences.IAppPreferences
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module
class AppPreferencesModule {

    @Provides
    fun preferences(context: Context, objectMapper: ObjectMapper): IAppPreferences =
        AppPreferences(context, objectMapper)
}