package com.igordanilchik.livedatatest.flows.catalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.igordanilchik.livedatatest.common.mvvm.SchedulersSet
import com.igordanilchik.livedatatest.common.mvvm.view_model.BaseViewModel
import com.igordanilchik.livedatatest.common.mvvm.view_model.Event
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.CategoryEntity
import com.igordanilchik.livedatatest.data.common.Constants.CatalogueLoadingBehaviorType
import com.igordanilchik.livedatatest.data.common.Constants.CatalogueLoadingBehaviorType.Companion.FORCE_REFRESH
import com.igordanilchik.livedatatest.data.common.Constants.CatalogueLoadingBehaviorType.Companion.THROTTLING
import com.igordanilchik.livedatatest.data.common.Resource
import com.igordanilchik.livedatatest.flows.catalogue.model.ICatalogueModel
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Consumer
import javax.inject.Inject

/**
 * @author Igor Danilchik
 */
class CatalogueViewModel @Inject constructor(
    schedulersSet: SchedulersSet,
    private val model: ICatalogueModel
) : BaseViewModel(schedulersSet) {

    private val selectedIdLiveData = MutableLiveData<Event<SelectedCategory>>()

    private val mediatorLiveData = MediatorLiveData<Resource<List<CategoryEntity>>>()

    init {
        loadData(THROTTLING)
    }

    val categories: LiveData<Resource<List<CategoryEntity>>>
        get() = mediatorLiveData

    val navigateToCategory: LiveData<Event<SelectedCategory>>
        get() = selectedIdLiveData

    fun onCategoryClicked(category: CategoryEntity) {
        selectedIdLiveData.value = Event(SelectedCategory(id = category.id, name = category.name))
    }

    fun onRefresh() = loadData(FORCE_REFRESH)

    private fun loadData(@CatalogueLoadingBehaviorType behavior: Int) {
        executeOn(
            ExecuteOn.IO,
            model.loadCategories(behavior),
            Consumer { list -> mediatorLiveData.value = Resource.success(list) },
            Consumer { throwable -> mediatorLiveData.value = Resource.error("Error: ${throwable.message}") },
            ObservableTransformer {
                it.doOnSubscribe {
                    mediatorLiveData.value = Resource.loading()
                }
            }
        )
    }
}