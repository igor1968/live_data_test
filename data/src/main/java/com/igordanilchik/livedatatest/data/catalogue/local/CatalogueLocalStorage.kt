package com.igordanilchik.livedatatest.data.catalogue.local

import com.igordanilchik.livedatatest.data.catalogue.dto.entity.CategoryEntity
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import io.reactivex.Observable

/**
 * @author Igor Danilchik
 */
interface CatalogueLocalStorage {
    fun saveCategories(categories: List<CategoryEntity>)
    fun saveOffers(offers: List<OfferEntity>)
    fun getCategories(): Observable<List<CategoryEntity>>
    fun getOffers(): Observable<List<OfferEntity>>
    fun clear()
}