package com.igordanilchik.livedatatest.flows.offers.model

import com.igordanilchik.livedatatest.data.catalogue.CatalogueRepository
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import com.igordanilchik.livedatatest.data.common.Constants.CatalogueLoadingBehaviorType
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * @author Igor Danilchik
 */
class OffersModel(
    private val repository: CatalogueRepository
) : IOffersModel {

    override fun loadSubcategory(
        @CatalogueLoadingBehaviorType behavior: Int,
        categoryId: Int
    ): Observable<List<OfferEntity>> =
        repository.getOffers(behavior)
            .debounce(400, TimeUnit.MILLISECONDS)
            .map { offers -> offers.filter { offer -> offer.categoryId == categoryId } }
            .onErrorReturn { emptyList() }
}