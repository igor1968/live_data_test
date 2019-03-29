package com.igordanilchik.livedatatest.flows.catalogue.view

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
import com.igordanilchik.livedatatest.data.Categories
import com.igordanilchik.livedatatest.data.Status
import com.igordanilchik.livedatatest.flows.catalogue.viewmodel.CatalogueViewModel
import com.igordanilchik.livedatatest.flows.catalogue.viewmodel.SelectedCategory
import com.igordanilchik.livedatatest.ui.adapter.CategoriesAdapter
import kotlinx.android.synthetic.main.empty_state.*
import kotlinx.android.synthetic.main.fragment_catalogue.*
import javax.inject.Inject

/**
 * @author Igor Danilchik
 */
class CatalogueFragment : BaseFragment(), CatalogueView, CategoriesAdapter.CategoriesCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, viewModelFactory).get(CatalogueViewModel::class.java)
    }

    override val layoutResID = R.layout.fragment_catalogue

    override val baseTitle = R.string.farfor_title

    override fun inject() = appComponent().inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_container.setOnRefreshListener(viewModel::onRefresh)
        swipe_container.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        catalogue_recycler_view.setHasFixedSize(true)
        catalogue_recycler_view.layoutManager = LinearLayoutManager(activity)
        catalogue_recycler_view.addItemDecoration(
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
        catalogue_recycler_view.adapter = null

        super.onDestroyView()
    }

    override fun onCategoryClicked(category: Categories.Category) = viewModel.onCategoryClicked(category)

    override fun showCategories(categories: Categories) {
        (catalogue_recycler_view.adapter as? CategoriesAdapter)?.apply {
            appendOrUpdate(categories.categories)
        } ?: run {
            catalogue_recycler_view.adapter = CategoriesAdapter(categories, this)
        }
    }

    override fun showError(message: String?) =
        Snackbar.make(catalogue_recycler_view, "Error: $message", Snackbar.LENGTH_LONG)
            .show()

    override fun showProgress() {
        swipe_container.post { swipe_container.isRefreshing = true }
    }

    override fun hideProgress() {
        swipe_container.post { swipe_container.isRefreshing = false }
    }

    override fun showEmptyState() {
        empty_state_container.visibility = View.VISIBLE
    }

    override fun hideEmptyState() {
        empty_state_container.visibility = View.GONE
    }

    override fun goToCategory(selectedCategory: SelectedCategory) {
        val directions = CatalogueFragmentDirections.toOffersFragment(selectedCategory.name)
            .setCategoryId(selectedCategory.id)
        view?.findNavController()?.navigate(directions)
    }
}