package com.igordanilchik.livedatatest.data.catalogue

import com.igordanilchik.livedatatest.data.common.Constants.CatalogueLoadingBehaviorType
import com.igordanilchik.livedatatest.data.common.KeyValueFactory
import com.igordanilchik.livedatatest.data.catalogue.behavior.CatalogueClearingBehavior
import com.igordanilchik.livedatatest.data.catalogue.behavior.CatalogueLoadingBehavior
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.CategoryEntity
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import io.reactivex.Observable

/**
 * @author Igor Danilchik
 */
class DefaultCatalogueRepository(
    private val loadingBehaviorFactory: KeyValueFactory<Int, CatalogueLoadingBehavior>,
    private val clearingBehavior: CatalogueClearingBehavior
) : CatalogueRepository {

    override fun getCategories(
        @CatalogueLoadingBehaviorType behavior: Int
    ): Observable<List<CategoryEntity>> = loadingBehaviorFactory
        .getInstance(behavior)
        .getCategories()

    override fun getOffers(
        @CatalogueLoadingBehaviorType behavior: Int
    ): Observable<List<OfferEntity>> = loadingBehaviorFactory
        .getInstance(behavior)
        .getOffers()

    override fun clear() = clearingBehavior.clear()

}