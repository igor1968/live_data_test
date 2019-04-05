package com.igordanilchik.livedatatest.common.di.common.app

import com.igordanilchik.livedatatest.common.di.common.data.EndpointModule
import com.igordanilchik.livedatatest.common.di.common.data.NetworkModule
import com.igordanilchik.livedatatest.common.di.common.data.PreferencesModule
import com.igordanilchik.livedatatest.common.di.feature.catalogue.repo.CatalogueRepositoryModule
import com.igordanilchik.livedatatest.common.di.feature.timestamp.TimeStampModule
import com.igordanilchik.livedatatest.flows.catalogue.view.CatalogueFragment
import com.igordanilchik.livedatatest.flows.location.view.LocationFragment
import com.igordanilchik.livedatatest.flows.offer.view.OfferFragment
import com.igordanilchik.livedatatest.flows.offers.view.OffersFragment
import com.igordanilchik.livedatatest.ui.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        EndpointModule::class,
        NetworkModule::class,
        PreferencesModule::class,
        TimeStampModule::class,
        CatalogueRepositoryModule::class,
        LocationModule::class
    ]
)
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(catalogueFragment: CatalogueFragment)
    fun inject(offersFragment: OffersFragment)
    fun inject(offerFragment: OfferFragment)
    fun inject(locationFragment: LocationFragment)
}
