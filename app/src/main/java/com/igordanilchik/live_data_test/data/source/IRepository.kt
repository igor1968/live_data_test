package com.igordanilchik.live_data_test.data.source

import com.igordanilchik.live_data_test.data.Categories
import com.igordanilchik.live_data_test.data.Offers
import io.reactivex.Observable


/**
 * @author Igor Danilchik
 */
interface IRepository {
    val categories: Observable<Categories>
    val offers: Observable<Offers>
}