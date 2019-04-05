package com.igordanilchik.livedatatest.common.di.common.app

import android.content.Context
import android.location.Geocoder
import com.igordanilchik.livedatatest.flows.location.model.ILocationModel
import com.igordanilchik.livedatatest.flows.location.model.LocationModel
import com.igordanilchik.livedatatest.flows.location.model.LocationProvider
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module
object LocationModule {

    @JvmStatic
    @Provides
    fun model(
        locationProvider: LocationProvider,
        geocoder: Geocoder
    ): ILocationModel =
        LocationModel(
            locationProvider,
            geocoder
        )

    @JvmStatic
    @Provides
    fun provideGeocoder(context: Context): Geocoder = Geocoder(context)

    @JvmStatic
    @Provides
    fun locationListener(context: Context): LocationProvider =
        LocationProvider(context)
}