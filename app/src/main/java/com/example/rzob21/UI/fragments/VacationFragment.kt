package com.example.rzob21.UI.fragments

import android.view.KeyEvent
import android.view.View
import com.example.rzob21.R
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.event_vacation_item.*
import kotlinx.android.synthetic.main.fragment_vacation.*


class VacationFragment(val Day: String,var boolean: Boolean = false) : BaseChangeCalendarFragment(R.layout.fragment_vacation) {


    override fun onResume() {
        super.onResume()
        vacation_data.text = APP_CALENDAR_DATE
        if (boolean){
            vacation_delete_image.visibility = View.VISIBLE
        }else vacation_delete_image.visibility = View.INVISIBLE
            vacation_input_days.requestFocus()
            showKeyboard()
            vacation_input_days.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                   change()
                   return@OnKeyListener true
                }
                false
            })

        vacation_delete_image.setOnClickListener {
            showToast(getString(R.string.toast_data_update))
            fragmentManager?.popBackStack()
        }
    }
    override fun change() {

    }

}