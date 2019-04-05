package com.igordanilchik.livedatatest.common.di.feature.catalogue.repo

import com.igordanilchik.livedatatest.common.di.common.Private
import com.igordanilchik.livedatatest.data.catalogue.CatalogueRepository
import com.igordanilchik.livedatatest.data.catalogue.DefaultCatalogueRepository
import com.igordanilchik.livedatatest.data.catalogue.behavior.CatalogueClearingBehavior
import com.igordanilchik.livedatatest.data.catalogue.behavior.CatalogueLoadingBehavior
import com.igordanilchik.livedatatest.data.common.KeyValueFactory
import com.igordanilchik.livedatatest.common.di.feature.catalogue.repo.behavior.CatalogueClearingBehaviorModule
import com.igordanilchik.livedatatest.common.di.feature.catalogue.repo.behavior.CatalogueLoadingBehaviorModule
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module(
    includes = [
        CatalogueLoadingBehaviorModule::class,
        CatalogueClearingBehaviorModule::class
    ]
)
object DefaultCatalogueRepositoryModule {

    @Private
    @JvmStatic
    @Provides
    internal fun provideRepo(
        loadingStrategyFactory: @JvmSuppressWildcards KeyValueFactory<Int, CatalogueLoadingBehavior>,
        clearingStrategy: CatalogueClearingBehavior
    ): CatalogueRepository =
        DefaultCatalogueRepository(
            loadingStrategyFactory,
            clearingStrategy
        )
}