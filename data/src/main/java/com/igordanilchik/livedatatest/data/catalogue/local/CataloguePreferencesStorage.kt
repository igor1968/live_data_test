package com.igordanilchik.livedatatest.data.catalogue.local

import com.igordanilchik.livedatatest.data.common.Mapper
import com.igordanilchik.livedatatest.data.common.prefs.AppPreferences
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.CategoryEntity
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import com.igordanilchik.livedatatest.data.catalogue.dto.json.CategoryJson
import com.igordanilchik.livedatatest.data.catalogue.dto.json.OfferJson
import io.reactivex.Observable

/**
 * @author Igor Danilchik
 */
private const val KEY_CATEGORY = "key_category_"
private const val KEY_OFFER = "key_offer_"

class CataloguePreferencesStorage(
    private val preferences: AppPreferences,
    private val categoryEntityMapper: Mapper<CategoryEntity, CategoryJson>,
    private val offerEntityMapper: Mapper<OfferEntity, OfferJson>,
    private val categoryJsonMapper: Mapper<CategoryJson, CategoryEntity>,
    private val offerJsonMapper: Mapper<OfferJson, OfferEntity>
) : CatalogueLocalStorage {

    override fun saveCategories(categories: List<CategoryEntity>) =
        categories.forEach { category ->
            preferences.putJSON(
                KEY_CATEGORY + category.id,
                categoryEntityMapper.map(category)
            )
        }

    override fun saveOffers(offers: List<OfferEntity>) =
        offers.forEach { offer -> preferences.putJSON(KEY_OFFER + offer.id, offerEntityMapper.map(offer)) }

    override fun getCategories(): Observable<List<CategoryEntity>> =
        Observable.fromCallable {
            preferences.getAllObjects(
                KEY_CATEGORY,
                CategoryJson::class.java
            ).map { categoryJsonMapper.map(it) }
        }

    override fun getOffers(): Observable<List<OfferEntity>> =
        Observable.fromCallable {
            preferences.getAllObjects(
                KEY_OFFER,
                OfferJson::class.java
            ).map { offerJsonMapper.map(it) }
        }

    override fun clear() {
        preferences.remove(KEY_CATEGORY)
        preferences.remove(KEY_OFFER)
    }
}