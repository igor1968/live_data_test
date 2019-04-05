package com.igordanilchik.livedatatest.common.di.feature.catalogue.repo

import com.igordanilchik.livedatatest.common.di.common.Private
import com.igordanilchik.livedatatest.data.catalogue.CatalogueRepository
import dagger.Subcomponent

/**
 * @author Igor Danilchik
 */
@Subcomponent(modules = [DefaultCatalogueRepositoryModule::class])
interface CatalogueRepositoryComponent {

    @Private
    fun repo(): CatalogueRepository

    @Subcomponent.Builder
    interface Builder {
        fun build(): CatalogueRepositoryComponent
    }
}