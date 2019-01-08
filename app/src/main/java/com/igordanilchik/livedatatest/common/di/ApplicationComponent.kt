package com.igordanilchik.livedatatest.common.di

import com.igordanilchik.livedatatest.flows.catalogue.view.CatalogueFragment
import com.igordanilchik.livedatatest.flows.location.view.LocationFragment
import com.igordanilchik.livedatatest.flows.offer.view.OfferFragment
import com.igordanilchik.livedatatest.flows.offers.view.OffersFragment
import com.igordanilchik.livedatatest.ui.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    HttpClientModule::class,
    RepositoryModule::class,
    DataSourceModule::class,
    AppPreferencesModule::class,
    MapperModule::class,
    LocationModule::class
])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(catalogueFragment: CatalogueFragment)
    fun inject(offersFragment: OffersFragment)
    fun inject(offerFragment: OfferFragment)
    fun inject(locationFragment: LocationFragment)
}
