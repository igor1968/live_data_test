package com.igordanilchik.livedatatest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.igordanilchik.livedatatest.R
import com.igordanilchik.livedatatest.data.catalogue.dto.entity.CategoryEntity
import com.igordanilchik.livedatatest.ui.adapter.holder.CategoriesViewHolder
import com.igordanilchik.livedatatest.ui.base.adapter.BaseAdapter
import com.igordanilchik.livedatatest.ui.base.adapter.holder.BaseViewHolder

/**
 * @author Igor Danilchik
 */
class CategoriesAdapter(
    categories: List<CategoryEntity>,
    private var callback: CategoriesCallback?
) : BaseAdapter<BaseViewHolder<CategoryEntity, CategoriesAdapter.CategoriesCallback>, CategoryEntity>(
    categories,
    null
) {

    override val adapterID: String = CategoriesAdapter::class.java.simpleName

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<CategoryEntity, CategoriesCallback> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)

        return CategoriesViewHolder(v, null, callback)
    }

    interface CategoriesCallback {
        fun onCategoryClicked(category: CategoryEntity)
    }
}