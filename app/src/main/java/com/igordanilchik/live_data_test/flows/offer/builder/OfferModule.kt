package com.igordanilchik.live_data_test.flows.offer.builder

import com.igordanilchik.live_data_test.data.source.IRepository
import com.igordanilchik.live_data_test.flows.offer.model.IOfferModel
import com.igordanilchik.live_data_test.flows.offer.model.OfferModel
import com.igordanilchik.live_data_test.flows.offer.model.OfferSupplier
import com.igordanilchik.live_data_test.flows.offer.presenter.OfferPresenter
import com.igordanilchik.live_data_test.repo.SchedulersSet
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module
class OfferModule(private val supplier: OfferSupplier) {

    @Provides
    fun presenter(
            schedulersSet: SchedulersSet,
            model: IOfferModel
    ): OfferPresenter = OfferPresenter(
            schedulersSet,
            model
    )

    @Provides
    fun model(repository: IRepository): IOfferModel =
            OfferModel(
                    repository,
                    supplier
            )

}