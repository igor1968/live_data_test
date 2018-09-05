package com.igordanilchik.live_data_test.data.source.remote

import com.igordanilchik.live_data_test.dto.inner.Catalogue
import io.reactivex.Observable

/**
 * @author Igor Danilchik
 */
interface IRemoteDataSource {
    val catalogue: Observable<Catalogue>
}