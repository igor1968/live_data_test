package com.igordanilchik.livedatatest.common.di.feature.catalogue.repo.storage

import com.igordanilchik.livedatatest.common.di.common.data.ApiModule
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.CategoryEntity
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import com.igordanilchik.livedatatest.data.catalogue.dto.xml.Catalogue
import com.igordanilchik.livedatatest.data.catalogue.remote.CatalogueNetworkStorage
import com.igordanilchik.livedatatest.data.catalogue.remote.CatalogueRemoteStorage
import com.igordanilchik.livedatatest.data.common.Mapper
import com.igordanilchik.livedatatest.data.common.api.ClientApi
import com.igordanilchik.livedatatest.data.service.EndpointProvider
import com.igordanilchik.livedatatest.common.di.feature.catalogue.repo.mapper.CatalogueMapperModule
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module(
    includes = [
        CatalogueMapperModule::class,
        ApiModule::class
    ]
)
object CatalogueRemoteStorageModule {

    @JvmStatic
    @Provides
    internal fun provideStorage(
        api: ClientApi,
        endpointProvider: EndpointProvider,
        categoryMapper: Mapper<Catalogue, List<CategoryEntity>>,
        offerMapper: Mapper<Catalogue, List<OfferEntity>>
    ): CatalogueRemoteStorage =
        CatalogueNetworkStorage(
            api,
            endpointProvider,
            categoryMapper,
            offerMapper
        )
}