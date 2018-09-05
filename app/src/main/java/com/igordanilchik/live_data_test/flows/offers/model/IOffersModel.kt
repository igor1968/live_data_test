package com.igordanilchik.live_data_test.flows.offers.model

import com.igordanilchik.live_data_test.data.Offers
import io.reactivex.Observable

/**
 * @author Igor Danilchik
 */
interface IOffersModel {
    fun loadOffers(): Observable<Offers>
}