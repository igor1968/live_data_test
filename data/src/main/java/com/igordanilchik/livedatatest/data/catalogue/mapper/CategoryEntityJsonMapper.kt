package com.igordanilchik.livedatatest.data.catalogue.mapper

import com.igordanilchik.livedatatest.data.common.Mapper
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.CategoryEntity
import com.igordanilchik.livedatatest.data.catalogue.dto.json.CategoryJson

/**
 * @author Igor Danilchik
 */
class CategoryEntityJsonMapper : Mapper<CategoryEntity, CategoryJson> {

    override fun map(from: CategoryEntity): CategoryJson = from.let {
        CategoryJson(
            id = it.id,
            name = it.name,
            pictureUrl = it.pictureUrl
        )
    }
}