package com.igordanilchik.live_data_test.dto

import com.igordanilchik.live_data_test.data.Categories
import com.igordanilchik.live_data_test.data.Offers
import com.igordanilchik.live_data_test.dto.inner.Catalogue

/**
 * @author Igor Danilchik
 */
interface ICatalogueMapper {
    fun mapToCategories(catalogue: Catalogue): Categories
    fun mapToOffers(catalogue: Catalogue): Offers
}