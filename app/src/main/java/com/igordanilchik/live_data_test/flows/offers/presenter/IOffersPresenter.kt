package com.igordanilchik.live_data_test.flows.offers.presenter

import com.igordanilchik.live_data_test.data.Offers

/**
 * @author Igor Danilchik
 */
interface IOffersPresenter {
    fun onOfferClicked(offer: Offers.Offer)
}