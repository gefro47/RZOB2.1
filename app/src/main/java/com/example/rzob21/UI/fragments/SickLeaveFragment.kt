package com.example.rzob21.UI.fragments

import android.view.KeyEvent
import android.view.View
import com.example.rzob21.R
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.fragment_sick_leave.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async


class SickLeaveFragment(var boolean: Boolean = false) : BaseChangeCalendarFragment(R.layout.fragment_sick_leave) {
    override fun onResume() {
        super.onResume()
        sick_leave_data.text = APP_CALENDAR_DATE
        if (boolean){
            sick_leave_delete_image.visibility = View.VISIBLE

        }else sick_leave_delete_image.visibility = View.INVISIBLE
        sick_leave_input_days.requestFocus()
        showKeyboard()
        sick_leave_input_days.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                change()
                return@OnKeyListener true
            }
            false
        })
        sick_leave_delete_image.setOnClickListener {

            showToast(getString(R.string.toast_data_update))
            fragmentManager?.popBackStack()
        }
    }

    override fun change() {

    }

}