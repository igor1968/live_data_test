package com.igordanilchik.live_data_test.common.di

import com.igordanilchik.live_data_test.dto.CatalogueMapper
import com.igordanilchik.live_data_test.dto.ICatalogueMapper
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