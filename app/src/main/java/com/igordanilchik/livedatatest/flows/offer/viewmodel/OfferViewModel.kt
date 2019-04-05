package com.igordanilchik.livedatatest.flows.offer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.igordanilchik.livedatatest.common.mvvm.SchedulersSet
import com.igordanilchik.livedatatest.common.mvvm.view_model.BaseViewModel
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import com.igordanilchik.livedatatest.data.common.Constants.CatalogueLoadingBehaviorType
import com.igordanilchik.livedatatest.data.common.Constants.CatalogueLoadingBehaviorType.Companion.THROTTLING
import com.igordanilchik.livedatatest.data.common.Resource
import com.igordanilchik.livedatatest.flows.offer.model.IOfferModel
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Consumer
import javax.inject.Inject

/**
 * @author Igor Danilchik
 */
class OfferViewModel @Inject constructor(
    schedulersSet: SchedulersSet,
    private val model: IOfferModel
) : BaseViewModel(schedulersSet) {

    private val mediatorLiveData = MediatorLiveData<Resource<OfferEntity>>()

    fun offer(offerId: Int): LiveData<Resource<OfferEntity>> {
        loadData(THROTTLING, offerId)
        return mediatorLiveData
    }

    private fun loadData(@CatalogueLoadingBehaviorType behavior: Int, offerId: Int) {
        executeOn(
            ExecuteOn.IO,
            model.loadOffer(behavior, offerId),
            Consumer { list -> mediatorLiveData.value = Resource.success(list) },
            Consumer { throwable -> mediatorLiveData.value = Resource.error("Error: ${throwable.message}") },
            ObservableTransformer {
                it.doOnSubscribe {
                    mediatorLiveData.value = Resource.loading()
                }
            }
        )
    }
}