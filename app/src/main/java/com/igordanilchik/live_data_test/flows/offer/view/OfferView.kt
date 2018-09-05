package com.igordanilchik.live_data_test.flows.offer.view

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.igordanilchik.live_data_test.common.mvp.view.AppBaseView
import com.igordanilchik.live_data_test.data.Offers
import com.igordanilchik.live_data_test.flows.offer.router.OfferRouter

/**
 * @author Igor Danilchik
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface OfferView: AppBaseView, OfferRouter {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showOffer(offer: Offers.Offer)
    fun showProgress()
    fun hideProgress()
    fun showError(throwable: Throwable)
}