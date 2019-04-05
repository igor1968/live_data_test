package com.igordanilchik.livedatatest.data.catalogue.behavior

import com.igordanilchik.livedatatest.data.catalogue.dto.entity.CategoryEntity
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import com.igordanilchik.livedatatest.data.catalogue.local.CatalogueLocalStorage
import com.igordanilchik.livedatatest.data.catalogue.remote.CatalogueRemoteStorage
import com.igordanilchik.livedatatest.data.common.Constants.TimeStampType
import com.igordanilchik.livedatatest.data.common.Constants.ValidationTimeType
import com.igordanilchik.livedatatest.data.common.Throttling
import com.igordanilchik.livedatatest.data.common.timestamp.TimeStamp
import com.igordanilchik.livedatatest.data.common.timestamp.TimeStampUtils
import com.igordanilchik.livedatatest.data.timestamp.local.TimeStampLocalStorage
import io.reactivex.Observable

/**
 * @author Igor Danilchik
 */
class CatalogueThrottlingBehavior(
    private val remoteStorage: CatalogueRemoteStorage,
    private val localStorage: CatalogueLocalStorage,
    private val timeStampStorage: TimeStampLocalStorage,
    //private val hotObservableExecutorsFactory: KeyValueFactory<Int, HotObservableExecutor<Categories>>,
    private val throttling: Throttling
) : CatalogueLoadingBehavior {

    override fun getCategories(): Observable<List<CategoryEntity>> = Observable.defer {
        when {
            isCacheExpired() -> refreshCategories()
            else -> getCachedCategories()
        }
    }

    override fun getOffers(): Observable<List<OfferEntity>> = Observable.defer {
        when {
            isCacheExpired() -> refreshOffers()
            else -> getCachedOffers()
        }
    }

    private fun refreshCategories(): Observable<List<CategoryEntity>> = getFreshCategories()
        .doOnNext {
            cacheCategories(it)
            refreshTimeStamp()
        }
        .onErrorResumeNext { responseError: Throwable ->
            getCachedCategories()
                .map { cachedCategories ->
                    if (cachedCategories.isEmpty()) {
                        throw responseError
                    } else {
                        cachedCategories
                    }
                }

        }
    //.compose { hotObservableExecutorsFactory.getInstance().execute(it) }

    private fun refreshOffers(): Observable<List<OfferEntity>> = getFreshOffers()
        .doOnNext {
            cacheOffers(it)
            refreshTimeStamp()
        }
        .onErrorResumeNext { responseError: Throwable ->
            getCachedOffers()
                .map { cachedOffers ->
                    if (cachedOffers.isEmpty()) {
                        throw responseError
                    } else {
                        cachedOffers
                    }
                }

        }
    //.compose { hotObservableExecutorsFactory.getInstance().execute(it) }

    private fun getCachedCategories(): Observable<List<CategoryEntity>> = localStorage.getCategories()

    private fun getCachedOffers(): Observable<List<OfferEntity>> = localStorage.getOffers()

    private fun getFreshCategories(): Observable<List<CategoryEntity>> = remoteStorage.getCategories()

    private fun getFreshOffers(): Observable<List<OfferEntity>> = remoteStorage.getOffers()

    private fun cacheCategories(categories: List<CategoryEntity>) = localStorage.saveCategories(categories)

    private fun cacheOffers(offers: List<OfferEntity>) = localStorage.saveOffers(offers)

    private fun refreshTimeStamp() = timeStampStorage.save(TimeStamp(TimeStampType.CATLOGUE))

    private fun isCacheExpired(): Boolean {
        val timeStamp = timeStampStorage.get(TimeStampType.CATLOGUE)

        return timeStamp == null || !TimeStampUtils.isValid(
            timeStamp.timeInMillis,
            throttling.value,
            ValidationTimeType.VALIDATION_TIME_SECONDS
        )
    }
}