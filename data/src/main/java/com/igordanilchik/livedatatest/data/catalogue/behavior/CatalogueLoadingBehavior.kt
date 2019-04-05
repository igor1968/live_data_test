package com.igordanilchik.livedatatest.data.catalogue.behavior

import com.igordanilchik.livedatatest.data.catalogue.dto.entity.CategoryEntity
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import io.reactivex.Observable

/**
 * @author Igor Danilchik
 */
interface CatalogueLoadingBehavior {
    fun getCategories(): Observable<List<CategoryEntity>>
    fun getOffers(): Observable<List<OfferEntity>>
}