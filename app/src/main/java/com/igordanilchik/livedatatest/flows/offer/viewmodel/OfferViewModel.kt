package com.igordanilchik.livedatatest.flows.offer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.igordanilchik.livedatatest.data.Offers
import com.igordanilchik.livedatatest.data.Resource
import com.igordanilchik.livedatatest.data.source.IRepository
import javax.inject.Inject

/**
 * @author Igor Danilchik
 */
class OfferViewModel @Inject constructor(
    private val repository: IRepository
) : ViewModel() {

    fun offer(offerId: Int): LiveData<Resource<Offers.Offer>> =
        Transformations.map(repository.offers) { playerList ->
            val filteredOffer = playerList.data?.offers?.first { offer -> offer.id == offerId }
            return@map Resource(status = playerList.status, data = filteredOffer, message = playerList.message)
        }

}