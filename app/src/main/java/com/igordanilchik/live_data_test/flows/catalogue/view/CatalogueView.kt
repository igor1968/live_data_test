package com.igordanilchik.live_data_test.flows.catalogue.view

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.igordanilchik.live_data_test.common.mvp.view.AppBaseView
import com.igordanilchik.live_data_test.data.Categories
import com.igordanilchik.live_data_test.flows.catalogue.router.CatalogueRouter

/**
 * @author Igor Danilchik
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface CatalogueView: AppBaseView, CatalogueRouter {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showCategories(categories: Categories)
    fun showProgress()
    fun hideProgress()
    fun showEmptyState()
    fun hideEmptyState()
    fun showError(throwable: Throwable)
}