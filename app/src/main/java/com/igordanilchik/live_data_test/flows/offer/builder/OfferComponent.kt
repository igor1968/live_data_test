package com.igordanilchik.live_data_test.flows.offer.builder

import com.igordanilchik.live_data_test.flows.offer.presenter.OfferPresenter
import dagger.Subcomponent

/**
 * @author Igor Danilchik
 */
@Subcomponent(modules = [OfferModule::class])
interface OfferComponent {
    fun presenter(): OfferPresenter
}