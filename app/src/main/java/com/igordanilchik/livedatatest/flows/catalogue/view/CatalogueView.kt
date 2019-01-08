package com.igordanilchik.livedatatest.flows.catalogue.view

import com.igordanilchik.livedatatest.data.Categories
import com.igordanilchik.livedatatest.flows.catalogue.router.CatalogueRouter

/**
 * @author Igor Danilchik
 */
interface CatalogueView: CatalogueRouter {
    fun showCategories(categories: Categories)
    fun showProgress()
    fun hideProgress()
    fun showEmptyState()
    fun hideEmptyState()
    fun showError(message: String?)
}