package com.igordanilchik.livedatatest.flows.offer.model

import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import com.igordanilchik.livedatatest.data.common.Constants.CatalogueLoadingBehaviorType
import io.reactivex.Observable

/**
 * @author Igor Danilchik
 */
interface IOfferModel {
    fun loadOffer(@CatalogueLoadingBehaviorType behavior: Int, offerId: Int): Observable<OfferEntity>
}