package com.igordanilchik.livedatatest.common.di.feature.catalogue.repo.behavior

import com.igordanilchik.livedatatest.common.di.feature.catalogue.repo.storage.CatalogueLocalStorageModule
import com.igordanilchik.livedatatest.data.catalogue.behavior.CatalogueClearingBehavior
import com.igordanilchik.livedatatest.data.catalogue.behavior.CatalogueClearingCacheBehavior
import com.igordanilchik.livedatatest.data.catalogue.local.CatalogueLocalStorage
import com.igordanilchik.livedatatest.data.timestamp.local.TimeStampLocalStorage
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module(includes = [CatalogueLocalStorageModule::class])
object CatalogueClearingBehaviorModule {

    @JvmStatic
    @Provides
    fun provideClearLocalStrategy(
        catalogueLocalStorage: CatalogueLocalStorage,
        timeStampStorage: TimeStampLocalStorage
    ): CatalogueClearingBehavior = CatalogueClearingCacheBehavior(
        catalogueLocalStorage,
        timeStampStorage
    )
}