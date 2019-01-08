package com.igordanilchik.livedatatest.common.di

import com.igordanilchik.livedatatest.data.source.IRepository
import com.igordanilchik.livedatatest.data.source.Repository
import com.igordanilchik.livedatatest.data.source.local.ILocalDataSource
import com.igordanilchik.livedatatest.data.source.remote.IRemoteDataSource
import com.igordanilchik.livedatatest.dto.ICatalogueMapper
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
