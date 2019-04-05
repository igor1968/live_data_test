package com.igordanilchik.livedatatest.flows.offers.model

import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import com.igordanilchik.livedatatest.data.common.Constants.CatalogueLoadingBehaviorType
import io.reactivex.Observable

/**
 * @author Igor Danilchik
 */
interface IOffersModel {
    fun loadSubcategory(@CatalogueLoadingBehaviorType behavior: Int, categoryId: Int): Observable<List<OfferEntity>>
}