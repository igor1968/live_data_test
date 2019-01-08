package com.igordanilchik.livedatatest.flows.location.model

import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationRequest
import com.igordanilchik.livedatatest.extensions.filter
import java.util.concurrent.TimeUnit

/**
 * @author Igor Danilchik
 */
class LocationModel(
    private val locationProvider: LocationProvider,
    private val geocoder: Geocoder
) : ILocationModel {

    companion object {
        private val LOCATION_TIMEOUT_IN_SECONDS = TimeUnit.SECONDS.toMillis(5)
        private val LOCATION_UPDATE_INTERVAL = TimeUnit.SECONDS.toMillis(60)
        private const val SUFFICIENT_ACCURACY = 500f
        private const val MAX_ADDRESSES = 1
    }

    override val updatableLocation: LiveData<Location>
        get() {
            val req = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setExpirationDuration(LOCATION_TIMEOUT_IN_SECONDS)
                .setInterval(LOCATION_UPDATE_INTERVAL)

            return locationProvider.getUpdatedLocation(req)
                .filter { location -> location.accuracy < SUFFICIENT_ACCURACY }
        }

    override fun getAddress(location: Location): String =
        geocoder.getFromLocation(location.latitude, location.longitude, MAX_ADDRESSES).firstOrNull()?.let { address ->

            val addressString = StringBuilder()
            for (i in 0..address.maxAddressLineIndex) {
                addressString.append(address.getAddressLine(i)).append(" ")
            }
            return@let addressString.toString()
        } ?: "Address unknown"
}