package com.igordanilchik.live_data_test.api


import com.igordanilchik.live_data_test.dto.inner.Catalogue
import io.reactivex.Observable

import retrofit2.http.GET
import retrofit2.http.Query

interface ClientApi {

    @GET("/getyml")
    fun loadCatalogue(@Query("key") key: String): Observable<Catalogue>

    companion object {

        val API_BASE_URL = "http://ufa.farfor.ru"
        val API_KEY = "ukAXxeJYZN"
    }
}
