package com.igordanilchik.livedatatest.flows.offers.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.igordanilchik.livedatatest.R
import com.igordanilchik.livedatatest.common.di.ViewModelFactory
import com.igordanilchik.livedatatest.common.mvvm.view.BaseFragment
import com.igordanilchik.livedatatest.data.Offers
import com.igordanilchik.livedatatest.data.Status
import com.igordanilchik.livedatatest.flows.offers.viewmodel.OffersViewModel
import com.igordanilchik.livedatatest.ui.adapter.OffersAdapter
import kotlinx.android.synthetic.main.fragment_offers.*
import javax.inject.Inject

/**
 * @author Igor Danilchik
 */
class OffersFragment : BaseFragment(), OffersView, OffersAdapter.OffersCallback {

    override val layoutResID = R.layout.fragment_offers

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, viewModelFactory).get(OffersViewModel::class.java)
    }

    override fun inject() = appComponent().inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        offers_recycler_view.setHasFixedSize(true)
        offers_recycler_view.layoutManager = LinearLayoutManager(activity)
        offers_recycler_view.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )

        val id = arguments?.let { OffersFragmentArgs.fromBundle(it).categoryId } ?: 0
        val name = arguments?.let { OffersFragmentArgs.fromBundle(it).categoryName }

        name?.let { setTitle(it) }

        viewModel.offers(id).observe(this, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    hideProgress()
                    result.data?.let { offers ->
                        showOffers(offers)
                    }
                }
                Status.LOADING -> showProgress()
                Status.ERROR -> {
                    hideProgress()
                    showError(result.message)
                }
            }
        })

        viewModel.navigateToOffer.observe(this, Observer {
            it.getContentIfNotHandled()?.apply {
                goToOffer(this)
            }
        })
    }

    override fun onDestroyView() {
        offers_recycler_view.adapter = null

        super.onDestroyView()
    }

    override fun onOfferClicked(offer: Offers.Offer) = viewModel.onOfferClicked(offer)

    override fun showOffers(offers: Offers) {
        (offers_recycler_view.adapter as? OffersAdapter)?.apply {
            appendOrUpdate(offers.offers)
        } ?: run {
            offers_recycler_view.adapter = OffersAdapter(offers, this)
        }
    }

    override fun showError(message: String?) =
        Snackbar.make(offers_recycler_view, "Error: $message", Snackbar.LENGTH_LONG)
            .show()

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun goToOffer(id: Int) {
        val directions = OffersFragmentDirections.toOfferFragment().setOfferId(id)
        view?.findNavController()?.navigate(directions)
    }
}