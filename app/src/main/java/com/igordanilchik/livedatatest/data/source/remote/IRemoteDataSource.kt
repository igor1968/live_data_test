package com.igordanilchik.livedatatest.data.source.remote

import androidx.lifecycle.LiveData
import com.igordanilchik.livedatatest.api.ApiResponse
import com.igordanilchik.livedatatest.dto.inner.Catalogue

/**
 * @author Igor Danilchik
 */
interface IRemoteDataSource {
    val catalogue: LiveData<ApiResponse<Catalogue>>
}