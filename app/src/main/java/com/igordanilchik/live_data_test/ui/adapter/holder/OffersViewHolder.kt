package com.igordanilchik.live_data_test.ui.adapter.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import butterknife.BindView
import com.arellomobile.mvp.MvpDelegate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.igordanilchik.live_data_test.R
import com.igordanilchik.live_data_test.data.Offers
import com.igordanilchik.live_data_test.ui.adapter.OffersAdapter
import com.igordanilchik.live_data_test.ui.base.adapter.holder.BaseViewHolder

/**
 * @author Igor Danilchik
 */
class OffersViewHolder(
        itemView: View,
        parentDelegate: MvpDelegate<*>?,
        callback: OffersAdapter.OffersCallback?
) : BaseViewHolder<Offers.Offer, OffersAdapter.OffersCallback>(
        itemView,
        parentDelegate,
        callback

) {

    @BindView(R.id.offer_name)
    lateinit var name: TextView
    @BindView(R.id.offer_image)
    lateinit var image: ImageView
    @BindView(R.id.offer_weight)
    lateinit var weight: TextView
    @BindView(R.id.offer_price)
    lateinit var price: TextView


    override fun render(item: Offers.Offer) {
        itemView.setOnClickListener { callback?.onOfferClicked(item) }

        name.text = item.name

        Glide.with(itemView.context)
                .load(item.picture)
                .fitCenter()
                .centerCrop()
                .placeholder(ContextCompat.getDrawable(itemView.context, R.drawable.ic_image_black_24dp))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image)

    }

}