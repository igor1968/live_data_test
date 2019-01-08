package com.igordanilchik.livedatatest.data.source.local

import androidx.lifecycle.LiveData
import com.igordanilchik.livedatatest.data.Categories
import com.igordanilchik.livedatatest.data.Offers

/**
 * @author Igor Danilchik
 */
interface ILocalDataSource {
    fun saveCategories(categories: Categories)
    fun saveOffers(offers: Offers)

    fun categories(): LiveData<Categories>
    fun offers(): LiveData<Offers>
    fun clear()
}