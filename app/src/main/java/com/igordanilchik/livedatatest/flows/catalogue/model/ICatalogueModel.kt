package com.igordanilchik.livedatatest.flows.catalogue.model

import com.igordanilchik.livedatatest.data.catalogue.dto.entity.CategoryEntity
import com.igordanilchik.livedatatest.data.common.Constants.*
import io.reactivex.Observable

/**
 * @author Igor Danilchik
 */
interface ICatalogueModel {
    fun loadCategories(@CatalogueLoadingBehaviorType behavior: Int): Observable<List<CategoryEntity>>
}