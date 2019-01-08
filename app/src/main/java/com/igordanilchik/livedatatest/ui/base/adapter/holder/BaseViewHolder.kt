package com.igordanilchik.livedatatest.ui.base.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpDelegate

/**
 * @author Igor Danilchik
 */
abstract class BaseViewHolder<ITEM_TYPE, CALLBACK_TYPE>(
    itemView: View,
    private val parentDelegate: MvpDelegate<*>?,
    protected var callback: CALLBACK_TYPE?
) : RecyclerView.ViewHolder(itemView), IBaseViewHolder<ITEM_TYPE> {

    private var mvpDelegate: MvpDelegate<out BaseViewHolder<*, *>>? = null

    protected var item: ITEM_TYPE? = null

    init {
        ButterKnife.bind(this, itemView)
    }

    /**
     * We can use presenter after call this method
     */
    fun bindView(item: ITEM_TYPE?) {
        this.item = item

        if (item != null) {
            if (parentDelegate != null) {
                mvpDelegate?.apply {
                    onSaveInstanceState()
                    onDetach()
                    onDestroyView()

                }

                mvpDelegate = getMvpDelegate()
                mvpDelegate?.apply {
                    onCreate()
                    onAttach()
                }
            }

            render(item)
        }
    }

    private fun getMvpDelegate(): MvpDelegate<out BaseViewHolder<*, *>>? =
        mvpDelegate ?: run {
            mvpDelegate = MvpDelegate(this)
            mvpDelegate?.apply { setParentDelegate(parentDelegate, item.hashCode().toString()) }
            return mvpDelegate
        }
}
