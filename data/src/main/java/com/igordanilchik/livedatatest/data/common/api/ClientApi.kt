package com.igordanilchik.livedatatest.data.common.api

import com.igordanilchik.livedatatest.data.catalogue.dto.xml.Catalogue
import io.reactivex.Observable

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ClientApi {

    @GET
    fun getCatalogue(@Url url: String, @Query("key") key: String): Observable<Catalogue>

    companion object {
        const val API_BASE_URL = "http://ufa.farfor.ru"
        const val API_BASE_PATH = "/getyml"
        const val API_KEY = "ukAXxeJYZN"
    }
}
