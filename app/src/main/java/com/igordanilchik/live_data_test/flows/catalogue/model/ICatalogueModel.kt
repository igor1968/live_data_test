package com.igordanilchik.live_data_test.flows.catalogue.model

import com.igordanilchik.live_data_test.data.Categories
import io.reactivex.Observable


/**
 * @author Igor Danilchik
 */
interface ICatalogueModel {
    fun loadCategories(): Observable<Categories>
}