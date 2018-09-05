package com.igordanilchik.live_data_test.flows.catalogue.presenter

import com.arellomobile.mvp.InjectViewState
import com.igordanilchik.live_data_test.common.mvp.presenter.AppBasePresenter
import com.igordanilchik.live_data_test.data.Categories
import com.igordanilchik.live_data_test.flows.catalogue.model.ICatalogueModel
import com.igordanilchik.live_data_test.flows.catalogue.view.CatalogueView
import com.igordanilchik.live_data_test.repo.SchedulersSet
import timber.log.Timber

/**
 * @author Igor Danilchik
 */

@InjectViewState
class CataloguePresenter(
    schedulersSet: SchedulersSet,
    val model: ICatalogueModel
) : AppBasePresenter<CatalogueView>(schedulersSet), ICataloguePresenter {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.showEmptyState()
        loadData()
    }

    private fun loadData() {
        executeOn(
            ExecuteOn.IO_DESTROY,
            model.loadCategories(),
            { categories ->
                Timber.d("update categories UI")
                viewState.hideEmptyState()
                viewState.showCategories(categories)
            },
            viewState::showError,
            {}
        ) {
            it.doOnSubscribe {
                viewState.showProgress()
            }.doFinally { viewState.hideProgress() }
        }
    }

    override fun onRefresh() = loadData()

    override fun onCategoryClicked(category: Categories.Category) =
        viewState.goToCategory(category.id)
}