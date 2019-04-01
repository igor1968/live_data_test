package com.igordanilchik.livedatatest.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.igordanilchik.livedatatest.common.preferences.IAppPreferences
import com.igordanilchik.livedatatest.data.Categories
import com.igordanilchik.livedatatest.data.Offers

/**
 * @author Igor Danilchik
 */
private const val KEY_CATEGORY = "key_category_"
private const val KEY_OFFER = "key_offer_"

class LocalDataSource(private val preferences: IAppPreferences): ILocalDataSource {

    private val categories = MutableLiveData<Categories>()
    private val offers = MutableLiveData<Offers>()

    override fun saveCategories(categories: Categories) =
            categories.categories.forEach { category -> preferences.putJSON(KEY_CATEGORY + category.id, category) }

    override fun saveOffers(offers: Offers) =
            offers.offers.forEach { offer -> preferences.putJSON(KEY_OFFER + offer.id, offer) }

    override fun categories(): LiveData<Categories> {
        categories.value = Categories(preferences.getAllObjects(KEY_CATEGORY, Categories.Category::class.java))
        return categories
    }

    override fun offers(): LiveData<Offers> {
        offers.value = Offers(preferences.getAllObjects(KEY_OFFER, Offers.Offer::class.java))
        return offers
    }

    override fun clear() {
        preferences.clear(KEY_CATEGORY)
        preferences.clear(KEY_OFFER)
    }
}