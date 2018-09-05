package com.igordanilchik.live_data_test.flows.catalogue.model

import com.igordanilchik.live_data_test.data.Categories
import com.igordanilchik.live_data_test.data.source.IRepository
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * @author Igor Danilchik
 */
class CatalogueModel(val repository: IRepository) : ICatalogueModel {

    override fun loadCategories(): Observable<Categories> =
            repository.categories.debounce(400, TimeUnit.MILLISECONDS)

}