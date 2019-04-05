package com.igordanilchik.livedatatest.data.catalogue.mapper

import com.igordanilchik.livedatatest.data.common.Mapper
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.CategoryEntity
import com.igordanilchik.livedatatest.data.catalogue.dto.json.CategoryJson

/**
 * @author Igor Danilchik
 */
class CategoryJsonEntityMapper : Mapper<CategoryJson, CategoryEntity> {

    override fun map(from: CategoryJson): CategoryEntity = from.let {
        CategoryEntity(
            id = it.id,
            name = it.name,
            pictureUrl = it.pictureUrl
        )
    }
}