package com.igordanilchik.live_data_test.flows.catalogue.view

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import butterknife.BindView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import com.igordanilchik.live_data_test.R
import com.igordanilchik.live_data_test.common.mvp.view.BaseFragment
import com.igordanilchik.live_data_test.data.Categories
import com.igordanilchik.live_data_test.flows.catalogue.builder.CatalogueModule
import com.igordanilchik.live_data_test.flows.catalogue.presenter.CataloguePresenter
import com.igordanilchik.live_data_test.ui.adapter.CategoriesAdapter

/**
 * @author Igor Danilchik
 */
class CatalogueFragment: BaseFragment(), CatalogueView, CategoriesAdapter.CategoriesCallback {

    @BindView(R.id.catalogue_recycler_view)
    lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    @BindView(R.id.swipe_container)
    lateinit var swipeContainer: androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    @BindView(R.id.empty_state_container)
    lateinit var emptyStateContainer: LinearLayout


    @InjectPresenter
    lateinit var presenter: CataloguePresenter

    override val layoutResID = R.layout.fragment_catalogue

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeContainer.setOnRefreshListener(presenter::onRefresh)
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)

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

    override fun onCategoryClicked(category: Categories.Category) =
            presenter.onCategoryClicked(category)


    override fun showCategories(categories: Categories) {
        (recyclerView.adapter as? CategoriesAdapter)?.apply {
            appendOrUpdate(categories.categories)
        } ?: run {
            recyclerView.adapter = CategoriesAdapter(categories, this)
        }
    }

    override fun showError(throwable: Throwable) {
        Snackbar.make(recyclerView, "Error: " + throwable.message, Snackbar.LENGTH_LONG)
                .show()
    }

    override fun showProgress() {
        swipeContainer.post { swipeContainer.isRefreshing = true }
    }

    override fun hideProgress() {
        swipeContainer.post { swipeContainer.isRefreshing = false }
    }

    override fun showEmptyState() {
        emptyStateContainer.visibility = View.VISIBLE
    }

    override fun hideEmptyState() {
        emptyStateContainer.visibility = View.GONE
    }

    override fun goToCategory(id: Int) {
        val directions = CatalogueFragmentDirections.toOffersFragment().setCategoryId(id)
        view?.findNavController()?.navigate(directions)
    }

    @ProvidePresenter
    fun providePresenter(): CataloguePresenter =
        appComponent().plusCatalogueComponent(CatalogueModule()).presenter()

}