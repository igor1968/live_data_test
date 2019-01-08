package com.igordanilchik.livedatatest.flows.location.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.igordanilchik.livedatatest.flows.location.model.ILocationModel
import javax.inject.Inject

/**
 * @author Igor Danilchik
 */
class LocationViewModel @Inject constructor(private val model: ILocationModel) : ViewModel() {

    val location: LiveData<Pair<Location, String>>
        get() = Transformations.map(model.updatableLocation) { location ->
            return@map location to model.getAddress(location)
        }
}