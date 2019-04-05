package com.igordanilchik.livedatatest.common.di.common.app

import com.igordanilchik.livedatatest.data.catalogue.CatalogueRepository
import com.igordanilchik.livedatatest.flows.catalogue.model.CatalogueModel
import com.igordanilchik.livedatatest.flows.catalogue.model.ICatalogueModel
import com.igordanilchik.livedatatest.flows.offer.model.IOfferModel
import com.igordanilchik.livedatatest.flows.offer.model.OfferModel
import com.igordanilchik.livedatatest.flows.offers.model.IOffersModel
import com.igordanilchik.livedatatest.flows.offers.model.OffersModel
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module
object ModelModule {

    @JvmStatic
    @Provides
    fun catalogueModel(
        repository: CatalogueRepository
    ): ICatalogueModel =
        CatalogueModel(repository)

    @JvmStatic
    @Provides
    fun offersModel(
        repository: CatalogueRepository
    ): IOffersModel =
        OffersModel(repository)

    @JvmStatic
    @Provides
    fun offerModel(
        repository: CatalogueRepository
    ): IOfferModel =
        OfferModel(repository)

}