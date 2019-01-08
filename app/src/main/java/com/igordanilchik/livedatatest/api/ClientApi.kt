package com.igordanilchik.livedatatest.api

import androidx.lifecycle.LiveData
import com.igordanilchik.livedatatest.dto.inner.Catalogue
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Igor Danilchik
 */
interface ClientApi {

    @GET("/getyml")
    fun loadCatalogue(@Query("key") key: String): LiveData<ApiResponse<Catalogue>>

    companion object {

        val API_BASE_URL = "http://ufa.farfor.ru"
        val API_KEY = "ukAXxeJYZN"
    }
}
