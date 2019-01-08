package com.igordanilchik.livedatatest.flows.location.model

import android.location.Location
import androidx.lifecycle.LiveData

/**
 * @author Igor Danilchik
 */
interface ILocationModel {
    val updatableLocation: LiveData<Location>
    fun getAddress(location: Location): String
}