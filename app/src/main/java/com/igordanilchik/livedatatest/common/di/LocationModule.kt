package com.igordanilchik.livedatatest.common.di

import android.content.Context
import android.location.Geocoder
import com.igordanilchik.livedatatest.flows.location.model.LocationProvider
import com.igordanilchik.livedatatest.flows.location.model.ILocationModel
import com.igordanilchik.livedatatest.flows.location.model.LocationModel
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module
class LocationModule {

    @Provides
    fun model(
        locationProvider: LocationProvider,
        geocoder: Geocoder
    ): ILocationModel =
        LocationModel(
            locationProvider,
            geocoder
        )

    @Provides
    fun provideGeocoder(context: Context): Geocoder = Geocoder(context)

    @Provides
    fun locationListener(context: Context): LocationProvider =
        LocationProvider(context)
}