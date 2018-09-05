package com.igordanilchik.live_data_test.flows.offers.builder

import com.igordanilchik.live_data_test.data.source.IRepository
import com.igordanilchik.live_data_test.flows.offers.model.IOffersModel
import com.igordanilchik.live_data_test.flows.offers.model.OffersModel
import com.igordanilchik.live_data_test.flows.offers.model.OffersSupplier
import com.igordanilchik.live_data_test.flows.offers.presenter.OffersPresenter
import com.igordanilchik.live_data_test.repo.SchedulersSet
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module
class OffersModule(private val supplier: OffersSupplier) {

    @Provides
    fun model(repository: IRepository): IOffersModel =
            OffersModel(
                    repository,
                    supplier
            )

    @Provides
    fun presenter(
            schedulersSet: SchedulersSet,
            model: IOffersModel
    ): OffersPresenter = OffersPresenter(
            schedulersSet,
            model
    )
}