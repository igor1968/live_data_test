package com.igordanilchik.livedatatest.data.source

import androidx.lifecycle.LiveData
import com.igordanilchik.livedatatest.data.Categories
import com.igordanilchik.livedatatest.data.Offers
import com.igordanilchik.livedatatest.data.Resource
import com.igordanilchik.livedatatest.data.source.local.ILocalDataSource
import com.igordanilchik.livedatatest.data.source.remote.IRemoteDataSource
import com.igordanilchik.livedatatest.dto.ICatalogueMapper
import com.igordanilchik.livedatatest.dto.inner.Catalogue

/**
 * @author Igor Danilchik
 */
class Repository(
    private val localDataSource: ILocalDataSource,
    private val remoteDataSource: IRemoteDataSource,
    private val mapper: ICatalogueMapper
) : IRepository {

    override val categories: LiveData<Resource<Categories>>
        get() = object : CombinedResource<Categories, Catalogue>() {

            override fun saveCallResult(item: Catalogue) = localDataSource.saveCategories(mapper.mapToCategories(item))

            override fun shouldFetch(data: Categories?) = data?.categories?.isEmpty() == true

            override fun loadFromDb() = localDataSource.categories()

            override fun createCall() = remoteDataSource.catalogue
        }.asLiveData()

    override var refresh: Boolean = false
        set(value) {
            if (value) {
                localDataSource.clear()
                field = false
            }
        }

    override val offers: LiveData<Resource<Offers>>
        get() = object : CombinedResource<Offers, Catalogue>() {

            override fun saveCallResult(item: Catalogue) =
                localDataSource.saveOffers(mapper.mapToOffers(item))

            override fun shouldFetch(data: Offers?) = data?.offers?.isEmpty() == true

            override fun loadFromDb() = localDataSource.offers()

            override fun createCall() = remoteDataSource.catalogue
        }.asLiveData()
}