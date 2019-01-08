package com.igordanilchik.livedatatest.flows.offer.view

import com.igordanilchik.livedatatest.data.Offers

/**
 * @author Igor Danilchik
 */
interface OfferView {
    fun showOffer(offer: Offers.Offer)
    fun showProgress()
    fun hideProgress()
    fun showError(message: String?)
}