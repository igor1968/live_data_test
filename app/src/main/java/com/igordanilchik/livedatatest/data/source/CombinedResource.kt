package com.igordanilchik.livedatatest.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.igordanilchik.livedatatest.api.ApiEmptyResponse
import com.igordanilchik.livedatatest.api.ApiErrorResponse
import com.igordanilchik.livedatatest.api.ApiResponse
import com.igordanilchik.livedatatest.api.ApiSuccessResponse
import com.igordanilchik.livedatatest.data.Resource

/**
 * A generic class that can provide a resource backed by both the local and remote.
 *
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class CombinedResource<ResultType, RequestType> {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData))
                }
            }
        }
    }

    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponse -> {
                    saveCallResult(processResponse(response))
                    // we specially request a new live data,
                    // otherwise we will get immediately last cached value,
                    // which may not be updated with latest results received from network.
                    result.addSource(loadFromDb()) { newData ->
                        setValue(Resource.success(newData))
                    }
                }
                is ApiEmptyResponse -> {
                    // reload from disk whatever we had
                    result.addSource(loadFromDb()) { newData ->
                        setValue(Resource.success(newData))
                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.error(response.errorMessage, newData))
                    }
                }
            }
        }
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    protected open fun onFetchFailed() {}

    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    protected abstract fun saveCallResult(item: RequestType)

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun loadFromDb(): LiveData<ResultType>

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}
