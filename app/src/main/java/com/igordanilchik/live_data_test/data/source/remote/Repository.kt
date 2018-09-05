package com.igordanilchik.live_data_test.data.source.remote

import com.igordanilchik.live_data_test.api.ClientApi
import com.igordanilchik.live_data_test.dto.inner.Catalogue
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Inject


class Repository @Inject
constructor(httpClient: OkHttpClient) {

    private val restApi: ClientApi =
            Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .baseUrl(ClientApi.API_BASE_URL)
                    .client(httpClient)
                    .build()
                    .create(ClientApi::class.java)

    val catalogue: Observable<Catalogue>
        get() = restApi.loadCatalogue(ClientApi.API_KEY)

}