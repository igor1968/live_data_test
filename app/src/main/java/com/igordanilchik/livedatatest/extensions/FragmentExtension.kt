package com.igordanilchik.livedatatest.extensions

fun androidx.fragment.app.FragmentActivity.replaceFragment(containerId: Int, fragment: androidx.fragment.app.Fragment, addToBackStack: Boolean) {
    val fm = this.supportFragmentManager
    val ft = fm.beginTransaction()

    ft.replace(containerId, fragment, fragment.javaClass.name)
    if (addToBackStack) {
        ft.addToBackStack(null)
    }
    ft.commit()
}
