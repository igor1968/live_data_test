package com.igordanilchik.livedatatest.common.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.igordanilchik.livedatatest.app.DaggerApplication
import com.igordanilchik.livedatatest.common.di.common.app.ApplicationComponent

/**
 * @author Igor Danilchik
 */
abstract class BaseFragment : Fragment() {

    abstract val layoutResID: Int

    abstract fun inject()

    @StringRes
    open val baseTitle: Int? = null

    fun appComponent(): ApplicationComponent = DaggerApplication[context].appComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(layoutResID, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseTitle?.let { setTitle(getString(it)) }
    }

    protected fun setTitle(title: CharSequence) {
        activity?.takeIf { !it.isFinishing }?.title = title
    }
}

