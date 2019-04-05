package com.igordanilchik.livedatatest.flows.offers.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.igordanilchik.livedatatest.R
import com.igordanilchik.livedatatest.common.di.common.app.ViewModelFactory
import com.igordanilchik.livedatatest.common.mvvm.view.BaseFragment
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import com.igordanilchik.livedatatest.data.common.Status
import com.igordanilchik.livedatatest.extensions.injectViewModel
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
        injectViewModel<OffersViewModel>(viewModelFactory)
    }

    override fun inject() = appComponent().inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.let { OffersFragmentArgs.fromBundle(it).categoryId } ?: 0
        val name = arguments?.let { OffersFragmentArgs.fromBundle(it).categoryName }

        name?.let { setTitle(it) }

        swipe_container.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
        swipe_container.setOnRefreshListener { viewModel.onRefresh(id) }

        offers_recycler_view.setHasFixedSize(true)
        offers_recycler_view.layoutManager = LinearLayoutManager(activity)
        offers_recycler_view.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )

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

    override fun onOfferClicked(offer: OfferEntity) = viewModel.onOfferClicked(offer)

    override fun showOffers(offers: List<OfferEntity>) {
        (offers_recycler_view.adapter as? OffersAdapter)?.apply {
            appendOrUpdate(offers)
        } ?: run {
            offers_recycler_view.adapter = OffersAdapter(offers, this)
        }
    }

    override fun showError(message: String?) =
        Snackbar.make(offers_recycler_view, "Error: $message", Snackbar.LENGTH_LONG)
            .show()

    override fun showProgress() {
        swipe_container.post { swipe_container.isRefreshing = true }
    }

    override fun hideProgress() {
        swipe_container.post { swipe_container.isRefreshing = false }
    }

    override fun goToOffer(id: Int) {
        val directions = OffersFragmentDirections.toOfferFragment().setOfferId(id)
        view?.findNavController()?.navigate(directions)
    }
}