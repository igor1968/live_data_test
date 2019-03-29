package com.igordanilchik.livedatatest.flows.offer.view

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.igordanilchik.livedatatest.R
import com.igordanilchik.livedatatest.common.di.ViewModelFactory
import com.igordanilchik.livedatatest.common.mvvm.view.BaseFragment
import com.igordanilchik.livedatatest.data.Offers
import com.igordanilchik.livedatatest.data.Status
import com.igordanilchik.livedatatest.data.getParamByKey
import com.igordanilchik.livedatatest.flows.offer.viewmodel.OfferViewModel
import kotlinx.android.synthetic.main.fragment_offer.*
import javax.inject.Inject

/**
 * @author Igor Danilchik
 */
class OfferFragment : BaseFragment(), OfferView {

    override val layoutResID = R.layout.fragment_offer

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, viewModelFactory).get(OfferViewModel::class.java)
    }

    override fun inject() = appComponent().inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.let { OfferFragmentArgs.fromBundle(it).offerId } ?: 0

        viewModel.offer(id).observe(this, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    hideProgress()
                    result.data?.let { offer ->
                        showOffer(offer)
                    }
                }
                Status.LOADING -> showProgress()
                Status.ERROR -> {
                    hideProgress()
                    showError(result.message)
                }
            }
        })
    }

    override fun showOffer(offer: Offers.Offer) {
        card_title.text = offer.name
        card_price.text = getString(R.string.offer_price, offer.price)

        offer.getParamByKey(getString(R.string.param_name_weight)).let {
            card_weight.text = getString(R.string.offer_weight, it)
        }

        offer.picture?.let { url ->
            url.takeIf { it.isNotEmpty() }?.let {
                val options = RequestOptions()
                    .fitCenter()
                    .placeholder(context?.let { ctx -> ContextCompat.getDrawable(ctx, R.drawable.ic_image_black_24dp) })
                    .diskCacheStrategy(DiskCacheStrategy.ALL)

                Glide.with(this)
                    .load(it)
                    .apply(options)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(card_image)
            }
        } ?: run { card_image.visibility = View.GONE }

        card_description.text = offer.description
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun showError(message: String?) =
        Snackbar.make(linear_layout, "Error: $message", Snackbar.LENGTH_LONG)
            .show()
}