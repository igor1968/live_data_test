package com.igordanilchik.livedatatest.flows.catalogue.router

import com.igordanilchik.livedatatest.flows.catalogue.viewmodel.SelectedCategory

/**
 * @author Igor Danilchik
 */
interface CatalogueRouter {
    fun goToCategory(selectedCategory: SelectedCategory)
}