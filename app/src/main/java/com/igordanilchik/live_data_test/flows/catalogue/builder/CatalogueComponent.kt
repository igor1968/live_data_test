package com.igordanilchik.live_data_test.flows.catalogue.builder

import com.igordanilchik.live_data_test.flows.catalogue.presenter.CataloguePresenter
import dagger.Subcomponent

/**
 * @author Igor Danilchik
 */
@Subcomponent(modules = [CatalogueModule::class])
interface CatalogueComponent {
    fun presenter(): CataloguePresenter
}