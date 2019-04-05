package com.igordanilchik.livedatatest.flows.offer.model

import com.igordanilchik.livedatatest.data.catalogue.CatalogueRepository
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import com.igordanilchik.livedatatest.data.common.Constants.CatalogueLoadingBehaviorType
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * @author Igor Danilchik
 */
class OfferModel(
    private val repository: CatalogueRepository
) : IOfferModel {

    override fun loadOffer(
        @CatalogueLoadingBehaviorType behavior: Int,
        offerId: Int
    ): Observable<OfferEntity> =
        repository.getOffers(behavior)
            .debounce(400, TimeUnit.MILLISECONDS)
            .map { offers -> offers.first { it.id == offerId } }
}