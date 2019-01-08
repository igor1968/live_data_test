package com.igordanilchik.livedatatest.flows.offers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.igordanilchik.livedatatest.common.mvvm.view_model.Event
import com.igordanilchik.livedatatest.data.Offers
import com.igordanilchik.livedatatest.data.Resource
import com.igordanilchik.livedatatest.data.source.IRepository
import javax.inject.Inject

/**
 * @author Igor Danilchik
 */
class OffersViewModel @Inject constructor(
    private val repository: IRepository
) : ViewModel() {

    private val selectedIdLiveData = MutableLiveData<Event<Int>>()

    val navigateToOffer: LiveData<Event<Int>>
        get() = selectedIdLiveData

    fun onOfferClicked(offer: Offers.Offer) {
        selectedIdLiveData.value = Event(offer.id)
    }

    fun offers(categoryId: Int): LiveData<Resource<Offers>> =
        Transformations.map(repository.offers) { playerList ->
            val filteredList =
                playerList.data?.offers?.filter { offer -> offer.categoryId == categoryId } ?: emptyList()
            return@map playerList.copy(data = Offers(filteredList))
        }
}