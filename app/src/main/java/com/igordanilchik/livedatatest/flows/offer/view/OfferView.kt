package com.igordanilchik.livedatatest.flows.offer.view

import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity

/**
 * @author Igor Danilchik
 */
interface OfferView {
    fun showOffer(offer: OfferEntity)
    fun showProgress()
    fun hideProgress()
    fun showError(message: String?)
}