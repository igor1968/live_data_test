package com.igordanilchik.live_data_test.flows.offers.view

import android.os.Bundle
import android.view.View
import butterknife.BindView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import com.igordanilchik.live_data_test.R
import com.igordanilchik.live_data_test.common.mvp.view.BaseFragment
import com.igordanilchik.live_data_test.data.Offers
import com.igordanilchik.live_data_test.flows.offers.builder.OffersModule
import com.igordanilchik.live_data_test.flows.offers.model.OffersSupplier
import com.igordanilchik.live_data_test.flows.offers.presenter.OffersPresenter
import com.igordanilchik.live_data_test.ui.adapter.OffersAdapter

/**
 * @author Igor Danilchik
 */
class OffersFragment : BaseFragment(), OffersView, OffersAdapter.OffersCallback {

    @BindView(R.id.offers_recycler_view)
    lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView

    @InjectPresenter
    lateinit var presenter: OffersPresenter

    override val layoutResID = R.layout.fragment_offers

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        recyclerView.addItemDecoration(
            androidx.recyclerview.widget.DividerItemDecoration(
                activity,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onDestroyView() {
        recyclerView.adapter = null

        super.onDestroyView()
    }

    override fun onOfferClicked(offer: Offers.Offer) = presenter.onOfferClicked(offer)

    override fun showOffers(offers: Offers) {
        (recyclerView.adapter as? OffersAdapter)?.apply {
            appendOrUpdate(offers.offers)
        } ?: run {
            recyclerView.adapter = OffersAdapter(offers, this)
        }
    }

    override fun showError(throwable: Throwable) {
        Snackbar.make(recyclerView, "Error: " + throwable.message, Snackbar.LENGTH_LONG)
            .show()
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun goToOffer(id: Int) {
        val directions = OffersFragmentDirections.toOfferFragment().setOfferId(id)
        view?.findNavController()?.navigate(directions)
    }

    @ProvidePresenter
    fun providePresenter(): OffersPresenter {
        val supplier = OffersSupplier(id = arguments?.let { OffersFragmentArgs.fromBundle(it).categoryId } ?: 0)

        return appComponent().plusOffersComponent(OffersModule(supplier)).presenter()
    }
}