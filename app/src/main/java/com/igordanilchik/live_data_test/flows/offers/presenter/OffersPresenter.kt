package com.igordanilchik.live_data_test.flows.offers.presenter

import com.arellomobile.mvp.InjectViewState
import com.igordanilchik.live_data_test.common.mvp.presenter.AppBasePresenter
import com.igordanilchik.live_data_test.data.Offers
import com.igordanilchik.live_data_test.flows.offers.model.IOffersModel
import com.igordanilchik.live_data_test.flows.offers.view.OffersView
import com.igordanilchik.live_data_test.repo.SchedulersSet
import timber.log.Timber

/**
 * @author Igor Danilchik
 */
@InjectViewState
class OffersPresenter(
        schedulersSet: SchedulersSet,
        private val model: IOffersModel
) : AppBasePresenter<OffersView>(schedulersSet), IOffersPresenter {

    override fun attachView(view: OffersView?) {
        super.attachView(view)

        loadData()
    }

    private fun loadData() {
        executeOn(
                ExecuteOn.IO_DESTROY,
                model.loadOffers(),
                {
                    Timber.d("update offers UI")
                    viewState.showOffers(it)
                },
                viewState::showError,
                {}
        ) {
            it.doOnSubscribe {
                viewState.showProgress()
            }.doFinally { viewState.hideProgress() }
        }
    }

    override fun onOfferClicked(offer: Offers.Offer) = viewState.goToOffer(offer.id)

}
