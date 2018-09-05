package com.igordanilchik.live_data_test.common.di

import com.igordanilchik.live_data_test.common.mvp.view.BaseFragment
import com.igordanilchik.live_data_test.flows.catalogue.builder.CatalogueComponent
import com.igordanilchik.live_data_test.flows.catalogue.builder.CatalogueModule
import com.igordanilchik.live_data_test.flows.location.builder.LocationComponent
import com.igordanilchik.live_data_test.flows.location.builder.LocationModule
import com.igordanilchik.live_data_test.flows.offer.builder.OfferComponent
import com.igordanilchik.live_data_test.flows.offer.builder.OfferModule
import com.igordanilchik.live_data_test.flows.offers.builder.OffersComponent
import com.igordanilchik.live_data_test.flows.offers.builder.OffersModule
import com.igordanilchik.live_data_test.ui.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    HttpClientModule::class,
    RepositoryModule::class,
    DataSourceModule::class,
    AppPreferencesModule::class,
    MapperModule::class
])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(baseFragment: BaseFragment)

    fun plusCatalogueComponent(catalogueModule: CatalogueModule): CatalogueComponent
    fun plusOffersComponent(offersModule: OffersModule): OffersComponent
    fun plusOfferComponent(offerModule: OfferModule): OfferComponent
    fun plusLocationComponent(locationModule: LocationModule): LocationComponent
}
