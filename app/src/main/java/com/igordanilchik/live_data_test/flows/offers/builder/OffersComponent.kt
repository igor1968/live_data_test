package com.igordanilchik.live_data_test.flows.offers.builder

import com.igordanilchik.live_data_test.flows.offers.presenter.OffersPresenter
import dagger.Subcomponent

/**
 * @author Igor Danilchik
 */
@Subcomponent(modules = [OffersModule::class])
interface OffersComponent {
    fun presenter(): OffersPresenter
}