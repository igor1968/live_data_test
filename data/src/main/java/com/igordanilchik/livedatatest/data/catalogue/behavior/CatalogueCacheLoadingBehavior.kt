package com.igordanilchik.livedatatest.data.catalogue.behavior

import com.igordanilchik.livedatatest.data.catalogue.dto.entity.CategoryEntity
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import com.igordanilchik.livedatatest.data.catalogue.local.CatalogueLocalStorage
import io.reactivex.Observable

/**
 * @author Igor Danilchik
 */
class CatalogueCacheLoadingBehavior(
    private val localStorage: CatalogueLocalStorage
): CatalogueLoadingBehavior {

    override fun getCategories(): Observable<List<CategoryEntity>> = localStorage.getCategories()

    override fun getOffers(): Observable<List<OfferEntity>> = localStorage.getOffers()
}