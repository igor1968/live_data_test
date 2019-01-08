package com.igordanilchik.livedatatest.flows.offers.view

import com.igordanilchik.livedatatest.data.Offers
import com.igordanilchik.livedatatest.flows.offers.router.OffersRouter

/**
 * @author Igor Danilchik
 */
interface OffersView: OffersRouter {
    fun showOffers(offers: Offers)
    fun showError(message: String?)
    fun showProgress()
    fun hideProgress()
}