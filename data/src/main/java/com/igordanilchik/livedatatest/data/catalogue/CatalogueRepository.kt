package com.igordanilchik.livedatatest.data.catalogue

import com.igordanilchik.livedatatest.data.common.Constants.CatalogueLoadingBehaviorType
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.CategoryEntity
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import io.reactivex.Observable

/**
 * @author Igor Danilchik
 */
interface CatalogueRepository {
    fun clear()
    fun getCategories(@CatalogueLoadingBehaviorType behavior: Int): Observable<List<CategoryEntity>>
    fun getOffers(@CatalogueLoadingBehaviorType behavior: Int): Observable<List<OfferEntity>>
}