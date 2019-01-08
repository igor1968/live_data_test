package com.igordanilchik.livedatatest.common.di

import com.igordanilchik.livedatatest.dto.CatalogueMapper
import com.igordanilchik.livedatatest.dto.ICatalogueMapper
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module
class MapperModule {

    @Provides
    fun mapper(): ICatalogueMapper = CatalogueMapper()
}