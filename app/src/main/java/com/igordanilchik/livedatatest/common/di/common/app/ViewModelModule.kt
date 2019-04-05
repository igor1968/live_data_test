package com.igordanilchik.livedatatest.common.di.common.app

import androidx.lifecycle.ViewModel
import com.igordanilchik.livedatatest.flows.catalogue.viewmodel.CatalogueViewModel
import com.igordanilchik.livedatatest.flows.location.viewmodel.LocationViewModel
import com.igordanilchik.livedatatest.flows.offer.viewmodel.OfferViewModel
import com.igordanilchik.livedatatest.flows.offers.viewmodel.OffersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author Igor Danilchik
 */
@Module(includes = [ModelModule::class])
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CatalogueViewModel::class)
    abstract fun bindCatalogueViewModel(viewModel: CatalogueViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OffersViewModel::class)
    abstract fun bindOffersViewModel(viewModel: OffersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OfferViewModel::class)
    abstract fun bindOfferViewModel(viewModel: OfferViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    abstract fun bindLocationViewModel(viewModel: LocationViewModel): ViewModel


}