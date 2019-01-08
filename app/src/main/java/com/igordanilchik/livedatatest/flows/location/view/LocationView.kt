package com.igordanilchik.livedatatest.flows.location.view

import android.location.Location

/**
 * @author Igor Danilchik
 */
interface LocationView {
    fun requestMap()
    fun showError(message: String?)
    fun updateMap(location: Location, address: String)
}