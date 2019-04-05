package com.igordanilchik.livedatatest.flows.offers.view

import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import com.igordanilchik.livedatatest.flows.offers.router.OffersRouter

/**
 * @author Igor Danilchik
 */
interface OffersView: OffersRouter {
    fun showOffers(offers: List<OfferEntity>)
    fun showError(message: String?)
    fun showProgress()
    fun hideProgress()
}