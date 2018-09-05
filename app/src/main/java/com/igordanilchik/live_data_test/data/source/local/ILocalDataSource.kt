package com.igordanilchik.live_data_test.data.source.local

import com.igordanilchik.live_data_test.data.Categories
import com.igordanilchik.live_data_test.data.Offers
import io.reactivex.Observable

/**
 * @author Igor Danilchik
 */
interface ILocalDataSource {
    fun saveCategories(categories: Categories)
    fun saveOffers(offers: Offers)
    fun getCategories(): Observable<Categories>
    fun getOffers(): Observable<Offers>
}