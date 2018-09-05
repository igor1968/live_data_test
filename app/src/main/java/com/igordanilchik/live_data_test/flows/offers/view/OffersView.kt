package com.igordanilchik.live_data_test.flows.offers.view

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.igordanilchik.live_data_test.common.mvp.view.AppBaseView
import com.igordanilchik.live_data_test.data.Offers
import com.igordanilchik.live_data_test.flows.offers.router.OffersRouter

/**
 * @author Igor Danilchik
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface OffersView: AppBaseView, OffersRouter {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showOffers(offers: Offers)
    fun showError(throwable: Throwable)
    fun showProgress()
    fun hideProgress()
}