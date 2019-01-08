package com.igordanilchik.livedatatest.data.source.remote

import androidx.lifecycle.LiveData
import com.igordanilchik.livedatatest.api.ApiResponse
import com.igordanilchik.livedatatest.api.ClientApi
import com.igordanilchik.livedatatest.common.api.LiveDataCallAdapterFactory
import com.igordanilchik.livedatatest.dto.inner.Catalogue
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

/**
 * @author Igor Danilchik
 */
class RemoteDataSource(httpClient: OkHttpClient): IRemoteDataSource {

    private val restApi: ClientApi =
            Retrofit.Builder()
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .baseUrl(ClientApi.API_BASE_URL)
                    .client(httpClient)
                    .build()
                    .create(ClientApi::class.java)

    override val catalogue: LiveData<ApiResponse<Catalogue>>
        get() = restApi.loadCatalogue(ClientApi.API_KEY)

}