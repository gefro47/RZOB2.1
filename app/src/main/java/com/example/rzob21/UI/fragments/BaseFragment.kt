package com.example.rzob21.UI.fragments

import androidx.fragment.app.Fragment
import com.example.rzob21.utilits.APP_ACTIVITY
import com.example.rzob21.utilits.hideKeyboard

open class BaseFragment(layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.mAppDrawer.disableDrawer()
        hideKeyboard()
    }

    override fun onStop() {
        super.onStop()
        APP_ACTIVITY.mAppDrawer.enableDrawer()
    }
}