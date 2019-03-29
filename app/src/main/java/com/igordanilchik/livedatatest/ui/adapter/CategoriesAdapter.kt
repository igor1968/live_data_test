package com.igordanilchik.livedatatest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.igordanilchik.livedatatest.R
import com.igordanilchik.livedatatest.data.Categories
import com.igordanilchik.livedatatest.ui.adapter.holder.CategoriesViewHolder
import com.igordanilchik.livedatatest.ui.base.adapter.BaseAdapter
import com.igordanilchik.livedatatest.ui.base.adapter.holder.BaseViewHolder

/**
 * @author Igor Danilchik
 */
class CategoriesAdapter(
    categories: Categories,
    private var callback: CategoriesCallback?
) : BaseAdapter<BaseViewHolder<Categories.Category, CategoriesAdapter.CategoriesCallback>, Categories.Category>(
    categories.categories,
    null
) {

    override val adapterID: String = CategoriesAdapter::class.java.simpleName

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Categories.Category, CategoriesCallback> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)

        return CategoriesViewHolder(v, null, callback)
    }

    interface CategoriesCallback {
        fun onCategoryClicked(category: Categories.Category)
    }
}