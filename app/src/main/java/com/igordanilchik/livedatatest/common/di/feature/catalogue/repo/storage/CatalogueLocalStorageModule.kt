package com.igordanilchik.livedatatest.common.di.feature.catalogue.repo.storage

import com.igordanilchik.livedatatest.data.catalogue.dto.entity.CategoryEntity
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import com.igordanilchik.livedatatest.data.catalogue.dto.json.CategoryJson
import com.igordanilchik.livedatatest.data.catalogue.dto.json.OfferJson
import com.igordanilchik.livedatatest.data.catalogue.local.CatalogueLocalStorage
import com.igordanilchik.livedatatest.data.catalogue.local.CataloguePreferencesStorage
import com.igordanilchik.livedatatest.data.common.Mapper
import com.igordanilchik.livedatatest.data.common.prefs.AppPreferences
import com.igordanilchik.livedatatest.common.di.feature.catalogue.repo.mapper.CatalogueMapperModule
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module(includes = [CatalogueMapperModule::class])
object CatalogueLocalStorageModule {

    @JvmStatic
    @Provides
    internal fun provideStorage(
        appPreferences: AppPreferences,
        categoryEntityMapper: Mapper<CategoryEntity, CategoryJson>,
        offerEntityMapper: Mapper<OfferEntity, OfferJson>,
        categoryJsonMapper: Mapper<CategoryJson, CategoryEntity>,
        offerJsonMapper: Mapper<OfferJson, OfferEntity>
    ): CatalogueLocalStorage =
        CataloguePreferencesStorage(
            appPreferences,
            categoryEntityMapper,
            offerEntityMapper,
            categoryJsonMapper,
            offerJsonMapper
        )
}