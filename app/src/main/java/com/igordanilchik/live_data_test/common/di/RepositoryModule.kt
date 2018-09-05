package com.igordanilchik.live_data_test.common.di

import com.igordanilchik.live_data_test.data.source.IRepository
import com.igordanilchik.live_data_test.data.source.Repository
import com.igordanilchik.live_data_test.data.source.local.ILocalDataSource
import com.igordanilchik.live_data_test.data.source.remote.IRemoteDataSource
import com.igordanilchik.live_data_test.dto.ICatalogueMapper
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    internal fun repository(
            localDataSource: ILocalDataSource,
            remoteDataSource: IRemoteDataSource,
            mapper: ICatalogueMapper
    ): IRepository =
            Repository(
                    localDataSource,
                    remoteDataSource,
                    mapper
            )
}
