package com.igordanilchik.livedatatest.flows.offers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.igordanilchik.livedatatest.common.mvvm.SchedulersSet
import com.igordanilchik.livedatatest.common.mvvm.view_model.BaseViewModel
import com.igordanilchik.livedatatest.common.mvvm.view_model.Event
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import com.igordanilchik.livedatatest.data.common.Constants.CatalogueLoadingBehaviorType
import com.igordanilchik.livedatatest.data.common.Constants.CatalogueLoadingBehaviorType.Companion.FORCE_REFRESH
import com.igordanilchik.livedatatest.data.common.Constants.CatalogueLoadingBehaviorType.Companion.THROTTLING
import com.igordanilchik.livedatatest.data.common.Resource
import com.igordanilchik.livedatatest.flows.offers.model.IOffersModel
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Consumer
import javax.inject.Inject

/**
 * @author Igor Danilchik
 */
class OffersViewModel @Inject constructor(
    schedulersSet: SchedulersSet,
    private val model: IOffersModel
) : BaseViewModel(schedulersSet) {

    private val selectedIdLiveData = MutableLiveData<Event<Int>>()

    private val mediatorLiveData = MediatorLiveData<Resource<List<OfferEntity>>>()

    val navigateToOffer: LiveData<Event<Int>>
        get() = selectedIdLiveData

    fun onOfferClicked(offer: OfferEntity) {
        selectedIdLiveData.value = Event(offer.id)
    }

    fun offers(categoryId: Int): LiveData<Resource<List<OfferEntity>>> {
        loadData(THROTTLING, categoryId)
        return mediatorLiveData
    }

    fun onRefresh(categoryId: Int) {
        loadData(FORCE_REFRESH, categoryId)
    }

    private fun loadData(@CatalogueLoadingBehaviorType behavior: Int, categoryId: Int) {
        executeOn(
            ExecuteOn.IO,
            model.loadSubcategory(behavior, categoryId),
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