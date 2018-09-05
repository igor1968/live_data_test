package com.igordanilchik.live_data_test.data.source

import com.igordanilchik.live_data_test.data.Categories
import com.igordanilchik.live_data_test.data.Offers
import com.igordanilchik.live_data_test.data.source.local.ILocalDataSource
import com.igordanilchik.live_data_test.data.source.remote.IRemoteDataSource
import com.igordanilchik.live_data_test.dto.ICatalogueMapper
import io.reactivex.Observable

/**
 * @author Igor Danilchik
 */
class Repository(
        private val localDataSource: ILocalDataSource,
        private val remoteDataSource: IRemoteDataSource,
        private val mapper: ICatalogueMapper
): IRepository {

    override val categories: Observable<Categories>
        get() = Observable.concat(
                localDataSource.getCategories(),
                remoteDataSource.catalogue.map { mapper.mapToCategories(it) }
                        .doOnNext { localDataSource.saveCategories(it) }
        )

    override val offers: Observable<Offers>
        get() = Observable.concat(
                localDataSource.getOffers(),
                remoteDataSource.catalogue.map { mapper.mapToOffers(it) }
                        .doOnNext { localDataSource.saveOffers(it) }
        )

}