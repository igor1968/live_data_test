package com.igordanilchik.live_data_test.flows.location.model

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.vanniktech.rxpermission.Permission
import com.vanniktech.rxpermission.RealRxPermission
import io.reactivex.Observable
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider
import java.util.concurrent.TimeUnit

/**
 * @author Igor Danilchik
 */
class LocationModel(
        private val locationProvider: ReactiveLocationProvider,
        private val permissionProvider: RealRxPermission
) : ILocationModel {

    companion object {
        private val LOCATION_TIMEOUT_IN_SECONDS = TimeUnit.SECONDS.toMillis(5)
        private val LOCATION_UPDATE_INTERVAL = TimeUnit.SECONDS.toMillis(60)
        private val SUFFICIENT_ACCURACY = 500f
        private val MAX_ADDRESSES = 1
    }


    override val lastKnownLocation: Observable<Location>
        @SuppressLint("MissingPermission")
        get() = locationProvider.lastKnownLocation


    override val updatableLocation: Observable<Location>
        @SuppressLint("MissingPermission")
        get() {
            val req = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setExpirationDuration(LOCATION_TIMEOUT_IN_SECONDS)
                    .setInterval(LOCATION_UPDATE_INTERVAL)


            return locationProvider.getUpdatedLocation(req)
                    .filter { location -> location.accuracy < SUFFICIENT_ACCURACY }
                    .timeout(LOCATION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS, Observable.empty())
        }

    override fun getAddress(location: Location): Observable<String> =
            locationProvider
                    .getReverseGeocodeObservable(location.latitude, location.longitude, MAX_ADDRESSES)
                    .filter { it.isNotEmpty() }
                    .map { it.first() }
                    .map { address ->

                        val addressString = StringBuilder()
                        for (i in 0..address.maxAddressLineIndex) {
                            addressString.append(address.getAddressLine(i)).append(" ")
                        }
                        return@map addressString.toString()
                    }

    override fun requestPermissions(): Observable<Permission> =
            permissionProvider
                    .requestEach(Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION)

}