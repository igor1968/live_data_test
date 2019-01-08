package com.igordanilchik.livedatatest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.igordanilchik.livedatatest.R
import com.igordanilchik.livedatatest.data.Offers
import com.igordanilchik.livedatatest.ui.adapter.holder.OffersViewHolder
import com.igordanilchik.livedatatest.ui.base.adapter.BaseAdapter
import com.igordanilchik.livedatatest.ui.base.adapter.holder.BaseViewHolder

/**
 * @author Igor Danilchik
 */
class OffersAdapter(
        offers: Offers,
        private val callback: OffersCallback?
): BaseAdapter<BaseViewHolder<Offers.Offer, OffersAdapter.OffersCallback>, Offers.Offer>(
        offers.offers,
        null
) {

    override val adapterID: String = OffersAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Offers.Offer, OffersCallback> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.offers_item, parent, false)

        return OffersViewHolder(v, null, callback)
    }



    interface OffersCallback {
        fun onOfferClicked(offer: Offers.Offer)
    }
}