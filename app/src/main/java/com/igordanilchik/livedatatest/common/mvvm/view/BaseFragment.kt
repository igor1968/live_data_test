package com.igordanilchik.livedatatest.common.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.igordanilchik.livedatatest.app.DaggerApplication
import com.igordanilchik.livedatatest.common.di.ApplicationComponent

/**
 * @author Igor Danilchik
 */
abstract class BaseFragment: Fragment() {

    private var unbinder: Unbinder? = null

    abstract val layoutResID: Int

    abstract fun inject()

    fun appComponent(): ApplicationComponent = DaggerApplication[context].appComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutResID, container, false)
        unbinder = ButterKnife.bind(this, view)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        unbinder?.unbind()
    }


}

