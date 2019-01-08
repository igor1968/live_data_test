package com.igordanilchik.livedatatest.flows.catalogue.view

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import com.google.android.material.snackbar.Snackbar
import com.igordanilchik.livedatatest.R
import com.igordanilchik.livedatatest.common.di.ViewModelFactory
import com.igordanilchik.livedatatest.common.mvvm.view.BaseFragment
import com.igordanilchik.livedatatest.data.Categories
import com.igordanilchik.livedatatest.data.Status
import com.igordanilchik.livedatatest.flows.catalogue.viewmodel.CatalogueViewModel
import com.igordanilchik.livedatatest.ui.adapter.CategoriesAdapter
import javax.inject.Inject

/**
 * @author Igor Danilchik
 */
class CatalogueFragment : BaseFragment(), CatalogueView, CategoriesAdapter.CategoriesCallback {

    @BindView(R.id.catalogue_recycler_view)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.swipe_container)
    lateinit var swipeContainer: SwipeRefreshLayout
    @BindView(R.id.empty_state_container)
    lateinit var emptyStateContainer: LinearLayout

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, viewModelFactory).get(CatalogueViewModel::class.java)
    }

    override val layoutResID = R.layout.fragment_catalogue

    override fun inject() {
        appComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeContainer.setOnRefreshListener(viewModel::onRefresh)
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )

        viewModel.categories.observe(this, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    hideProgress()
                    result.data?.let { categories ->
                        hideEmptyState()
                        showCategories(categories)
                    } ?: showEmptyState()
                }
                Status.LOADING -> showProgress()
                Status.ERROR -> {
                    hideProgress()
                    showError(result.message)
                }
            }
        })

        viewModel.navigateToCategory.observe(this, Observer {
            it.getContentIfNotHandled()?.apply {
                goToCategory(this)
            }
        })

        showEmptyState()
    }

    override fun onDestroyView() {
        recyclerView.adapter = null

        super.onDestroyView()
    }

    override fun onCategoryClicked(category: Categories.Category) {
        viewModel.onCategoryClicked(category)
    }

    override fun showCategories(categories: Categories) {
        (recyclerView.adapter as? CategoriesAdapter)?.apply {
            appendOrUpdate(categories.categories)
        } ?: run {
            recyclerView.adapter = CategoriesAdapter(categories, this)
        }
    }

    override fun showError(message: String?) =
        Snackbar.make(recyclerView, "Error: $message", Snackbar.LENGTH_LONG)
            .show()

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
}