package com.igordanilchik.livedatatest.common.di.feature.catalogue.repo.mapper

import com.igordanilchik.livedatatest.data.catalogue.dto.entity.CategoryEntity
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.OfferEntity
import com.igordanilchik.livedatatest.data.catalogue.dto.json.CategoryJson
import com.igordanilchik.livedatatest.data.catalogue.dto.json.OfferJson
import com.igordanilchik.livedatatest.data.catalogue.dto.xml.Catalogue
import com.igordanilchik.livedatatest.data.catalogue.mapper.CatalogueToCategoryEntityMapper
import com.igordanilchik.livedatatest.data.catalogue.mapper.CatalogueToOfferEntityMapper
import com.igordanilchik.livedatatest.data.catalogue.mapper.CategoryEntityJsonMapper
import com.igordanilchik.livedatatest.data.catalogue.mapper.CategoryJsonEntityMapper
import com.igordanilchik.livedatatest.data.catalogue.mapper.OfferEntityJsonMapper
import com.igordanilchik.livedatatest.data.catalogue.mapper.OfferJsonEntityMapper
import com.igordanilchik.livedatatest.data.common.Mapper
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module
object CatalogueMapperModule {

    @JvmStatic
    @Provides
    fun catalogueCategoryEntityMapper(): Mapper<Catalogue, List<CategoryEntity>> =
        CatalogueToCategoryEntityMapper()

    @JvmStatic
    @Provides
    fun catalogueOfferEntityMapper(): Mapper<Catalogue, List<OfferEntity>> =
        CatalogueToOfferEntityMapper()

    @JvmStatic
    @Provides
    fun categoryEntityJsonMapper(): Mapper<CategoryEntity, CategoryJson> =
        CategoryEntityJsonMapper()

    @JvmStatic
    @Provides
    fun categoryJsonEntityMapper(): Mapper<CategoryJson, CategoryEntity> =
        CategoryJsonEntityMapper()

    @JvmStatic
    @Provides
    fun offerEntityJsonMapper(): Mapper<OfferEntity, OfferJson> =
        OfferEntityJsonMapper()

    @JvmStatic
    @Provides
    fun offerJsonEntityMapper(): Mapper<OfferJson, OfferEntity> =
        OfferJsonEntityMapper()
}