package com.igordanilchik.livedatatest.data.source

import androidx.lifecycle.LiveData
import com.igordanilchik.livedatatest.data.Categories
import com.igordanilchik.livedatatest.data.Offers
import com.igordanilchik.livedatatest.data.Resource

/**
 * @author Igor Danilchik
 */
interface IRepository {
    val categories: LiveData<Resource<Categories>>
    val offers: LiveData<Resource<Offers>>
    var refresh: Boolean
}