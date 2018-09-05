package com.igordanilchik.live_data_test.flows.location.builder

import com.igordanilchik.live_data_test.flows.location.presenter.LocationPresenter
import dagger.Subcomponent

/**
 * @author Igor Danilchik
 */
@Subcomponent(modules = [LocationModule::class])
interface LocationComponent {
    fun presenter(): LocationPresenter
}