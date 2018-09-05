package com.igordanilchik.live_data_test.flows.offer.model

import com.igordanilchik.live_data_test.data.Offers
import io.reactivex.Observable

/**
 * @author Igor Danilchik
 */
interface IOfferModel {
    fun loadOffer(): Observable<Offers.Offer>
}