package com.igordanilchik.live_data_test.flows.catalogue.presenter

import com.igordanilchik.live_data_test.data.Categories

/**
 * @author Igor Danilchik
 */
interface ICataloguePresenter {
    fun onRefresh()
    fun onCategoryClicked(category: Categories.Category)
}