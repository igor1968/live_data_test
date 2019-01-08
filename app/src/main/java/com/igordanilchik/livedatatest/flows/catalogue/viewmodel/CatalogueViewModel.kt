package com.igordanilchik.livedatatest.flows.catalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.igordanilchik.livedatatest.common.mvvm.view_model.Event
import com.igordanilchik.livedatatest.data.Categories
import com.igordanilchik.livedatatest.data.Resource
import com.igordanilchik.livedatatest.data.source.IRepository
import javax.inject.Inject

/**
 * @author Igor Danilchik
 */
class CatalogueViewModel @Inject constructor(
    private val repository: IRepository
) : ViewModel() {

    private val selectedIdLiveData = MutableLiveData<Event<Int>>()

    private val mediatorLiveData = MediatorLiveData<Resource<Categories>>()

    init {
        mediatorLiveData.addSource(repository.categories) { mediatorLiveData.value = it }
    }

    val categories: LiveData<Resource<Categories>>
        get() = mediatorLiveData

    val navigateToCategory: LiveData<Event<Int>>
        get() = selectedIdLiveData

    fun onCategoryClicked(category: Categories.Category) {
        selectedIdLiveData.value = Event(category.id)
    }

    fun onRefresh() {
        mediatorLiveData.removeSource(repository.categories)
        repository.refresh = true
        mediatorLiveData.addSource(repository.categories) { mediatorLiveData.value = it }
    }
}