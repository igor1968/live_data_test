package com.igordanilchik.livedatatest.flows.catalogue.view

import com.igordanilchik.livedatatest.data.catalogue.dto.entity.CategoryEntity
import com.igordanilchik.livedatatest.flows.catalogue.router.CatalogueRouter

/**
 * @author Igor Danilchik
 */
interface CatalogueView : CatalogueRouter {
    fun showCategories(categories: List<CategoryEntity>)
    fun showProgress()
    fun hideProgress()
    fun showEmptyState()
    fun hideEmptyState()
    fun showError(message: String?)
}