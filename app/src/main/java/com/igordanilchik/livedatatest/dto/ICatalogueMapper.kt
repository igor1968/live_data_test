package com.igordanilchik.livedatatest.dto

import com.igordanilchik.livedatatest.data.Categories
import com.igordanilchik.livedatatest.data.Offers
import com.igordanilchik.livedatatest.dto.inner.Catalogue

/**
 * @author Igor Danilchik
 */
interface ICatalogueMapper {
    fun mapToCategories(catalogue: Catalogue): Categories
    fun mapToOffers(catalogue: Catalogue): Offers
}