package com.igordanilchik.livedatatest.flows.location.model

/**
 * @author Igor Danilchik
 */

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class LocationProvider @SuppressLint("MissingPermission")
constructor(appContext: Context) : LiveData<Location>() {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(appContext)

    private val locationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                if (location != null) {
                    value = location
                }
            }
        }
    }
    private var locationRequest: LocationRequest? = null

    init {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                value = location
            }
        }
    }

    fun getUpdatedLocation(req: LocationRequest): LiveData<Location> {
        locationRequest = req
        return this
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        locationRequest?.let { fusedLocationClient.requestLocationUpdates(it, locationCallback, null) }
    }

    override fun onInactive() {
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}