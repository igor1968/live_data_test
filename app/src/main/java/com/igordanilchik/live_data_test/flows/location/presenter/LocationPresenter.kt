package com.igordanilchik.live_data_test.flows.location.presenter

import com.arellomobile.mvp.InjectViewState
import com.igordanilchik.live_data_test.common.mvp.presenter.AppBasePresenter
import com.igordanilchik.live_data_test.flows.location.model.ILocationModel
import com.igordanilchik.live_data_test.flows.location.view.LocationView
import com.igordanilchik.live_data_test.repo.SchedulersSet
import com.vanniktech.rxpermission.Permission
import io.reactivex.Observable

/**
 * @author Igor Danilchik
 */
@InjectViewState
class LocationPresenter(
        schedulersSet: SchedulersSet,
        private val model: ILocationModel
) : AppBasePresenter<LocationView>(schedulersSet), ILocationPresenter {

    override fun attachView(view: LocationView?) {
        super.attachView(view)

        viewState.requestMap()
    }

    override fun onMapReady() {
        executeOn(ExecuteOn.IO_DETACH,
                model.requestPermissions(),
                { permissions ->
                    when (permissions.state()) {
                        Permission.State.GRANTED -> onPermissionsGranted()
                        else -> {
                        }
                    }
                },
                viewState::showError
        )
    }

    override fun onPermissionsGranted() {
        executeOn(ExecuteOn.IO_DETACH,
                Observable.concat(model.lastKnownLocation, model.updatableLocation)
                        .flatMap { location ->
                            model.getAddress(location)
                                    .map { address -> location to address }
                        },
                { (location, address) -> viewState.updateMap(location, address) },
                viewState::showError
        )
    }

}