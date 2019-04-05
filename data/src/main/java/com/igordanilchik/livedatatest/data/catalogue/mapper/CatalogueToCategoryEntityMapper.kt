package com.igordanilchik.livedatatest.data.catalogue.mapper

import com.igordanilchik.livedatatest.data.common.Mapper
import com.igordanilchik.livedatatest.data.common.api.ClientApi
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.CategoryEntity
import com.igordanilchik.livedatatest.data.catalogue.dto.xml.Catalogue

/**
 * @author Igor Danilchik
 */
class CatalogueToCategoryEntityMapper : Mapper<Catalogue, List<CategoryEntity>> {

    override fun map(from: Catalogue): List<CategoryEntity> = from.let { catalogue ->
        catalogue.shop.categories.map innerMap@{ category ->
            val url = catalogue.shop.offers.find { offer ->
                offer.categoryId == category.id && offer.pictureUrl != null
            }?.pictureUrl

            return@innerMap CategoryEntity(
                id = category.id,
                name = category.title,
                pictureUrl = fixUrl(url)
            )
        }
    }

    private fun fixUrl(pictureUrl: String?): String? = pictureUrl?.replace(ClientApi.API_BASE_URL, "")
}