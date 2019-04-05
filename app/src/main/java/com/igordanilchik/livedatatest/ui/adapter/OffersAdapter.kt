package com.igordanilchik.livedatatest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.igordanilchik.livedatatest.R
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import com.igordanilchik.livedatatest.ui.adapter.holder.OffersViewHolder
import com.igordanilchik.livedatatest.ui.base.adapter.BaseAdapter
import com.igordanilchik.livedatatest.ui.base.adapter.holder.BaseViewHolder

/**
 * @author Igor Danilchik
 */
class OffersAdapter(
    offers: List<OfferEntity>,
    private val callback: OffersCallback?
) : BaseAdapter<BaseViewHolder<OfferEntity, OffersAdapter.OffersCallback>, OfferEntity>(
    offers,
    null
) {

    override val adapterID: String = OffersAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<OfferEntity, OffersCallback> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.offers_item, parent, false)

        return OffersViewHolder(v, null, callback)
    }

    interface OffersCallback {
        fun onOfferClicked(offer: OfferEntity)
    }
}