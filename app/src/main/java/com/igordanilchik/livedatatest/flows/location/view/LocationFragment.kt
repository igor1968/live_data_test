package com.igordanilchik.livedatatest.flows.location.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.igordanilchik.livedatatest.R
import com.igordanilchik.livedatatest.common.di.ViewModelFactory
import com.igordanilchik.livedatatest.common.mvvm.view.BaseFragment
import com.igordanilchik.livedatatest.flows.location.viewmodel.LocationViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Igor Danilchik
 */
class LocationFragment : BaseFragment(), LocationView {

    @BindView(R.id.map_view)
    lateinit var mapView: MapView
    @BindView(R.id.address)
    lateinit var address: TextView

    private var map: GoogleMap? = null

    override val layoutResID: Int = R.layout.fragment_location

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, viewModelFactory).get(LocationViewModel::class.java)
    }

    override fun inject() {
        appComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestMap()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        MapsInitializer.initialize(activity)
        mapView.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()

        if (activity?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED
            || activity?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED) {

            map?.isMyLocationEnabled = false
        }
    }

    override fun requestMap() =
        mapView.getMapAsync {
            map = it
            requestPermissions()
        }

    @SuppressLint("MissingPermission")
    override fun updateMap(location: Location, address: String) {
        askPermission(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) {
            map?.uiSettings?.isZoomControlsEnabled = true
            map?.isMyLocationEnabled = true

            updateContent(location, address)
        }.onDeclined { e ->
            if (e.hasDenied()) {
            }

            if (e.hasForeverDenied()) {
            }
        }
    }

    private fun requestPermissions() {
        askPermission(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) {
            viewModel.location.observe(this, Observer { (location, address) ->
                updateMap(location, address)
            })
        }.onDeclined { e ->
            if (e.hasDenied()) {
            }

            if (e.hasForeverDenied()) {
            }
        }
    }

    private fun updateContent(location: Location, addressString: String) {
        Timber.d("updateContent")
        cameraSettings(LatLng(location.latitude, location.longitude))
        address.text = addressString
    }

    private fun cameraSettings(latLng: LatLng) {
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
        map?.animateCamera(cameraUpdate)
        setMarker(latLng)
    }

    private fun setMarker(latLng: LatLng) {
        map?.addMarker(MarkerOptions().position(latLng).draggable(false))?.title = getString(R.string.marker_title)
    }

    override fun showError(message: String?) {
        activity?.let {
            Snackbar.make(it.findViewById(android.R.id.content), "Error: $message", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
    }
}