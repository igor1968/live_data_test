package com.igordanilchik.livedatatest.common.di.feature.catalogue.repo

import com.igordanilchik.livedatatest.data.catalogue.CatalogueRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Igor Danilchik
 */
@Module(subcomponents = [CatalogueRepositoryComponent::class])
object CatalogueRepositoryModule {

    @JvmStatic
    @Singleton
    @Provides
    internal fun provideRepo(
        componentBuilder: CatalogueRepositoryComponent.Builder
    ): CatalogueRepository = componentBuilder
        .build()
        .repo()
}