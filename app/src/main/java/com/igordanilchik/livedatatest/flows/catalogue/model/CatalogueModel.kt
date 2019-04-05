package com.igordanilchik.livedatatest.flows.catalogue.model

import com.igordanilchik.livedatatest.data.catalogue.CatalogueRepository
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.CategoryEntity
import com.igordanilchik.livedatatest.data.common.Constants
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * @author Igor Danilchik
 */
class CatalogueModel(
    private val repository: CatalogueRepository
) : ICatalogueModel {

    override fun loadCategories(@Constants.CatalogueLoadingBehaviorType behavior: Int): Observable<List<CategoryEntity>> =
        repository.getCategories(behavior)
            .debounce(400, TimeUnit.MILLISECONDS)
}