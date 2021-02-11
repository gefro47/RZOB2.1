package com.example.rzob21.UI.fragments

import android.app.ActionBar
import android.opengl.Visibility
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import com.example.rzob21.R
import com.example.rzob21.models.Recast
import com.example.rzob21.models.User
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.fragment_recast.*


class RecastFragment(val Day: Int,var boolean: Boolean = false) : BaseChangeCalendarFragment(R.layout.fragment_recast) {
    override fun onResume() {
        super.onResume()
        initRecast()
        if (boolean){
            recast_delete_image.visibility = View.VISIBLE
        }else recast_delete_image.visibility = View.INVISIBLE
        recast_data.text = APP_CALENDAR_DATE
        recast_input_hours.requestFocus()
        recast_input_hours.setSelection(recast_input_hours.getText().length)
        showKeyboard()
        recast_input_hours.setOnKeyListener(View.OnKeyListener{ v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                    change()
                    return@OnKeyListener true
                }
            false
            })
        recast_delete_image.setOnClickListener{
            deleteData(NODE_RECAST, Day)
            showToast(getString(R.string.toast_data_update))
            fragmentManager?.popBackStack()
        }
        LIST_OF_RECAST_DATE = mutableListOf<Int>()
    }

    override fun change() {
        val hours = recast_input_hours.text.toString()
        if (hours.isEmpty() || hours.toDouble() == 0.0){
            showToast(getString(R.string.recast_toast_hours_is_empty))
        }else {
            val recast = Recast(hours.toDouble(), APP_CALENDAR_DATE_DAY, APP_DATE_DAY_OF_WEEK)
            saveData(NODE_RECAST, recast){
                showToast(getString(R.string.toast_data_update))
                fragmentManager?.popBackStack()
            }
        }
    }
    fun initRecast(){
        readData(NODE_RECAST, Day){
            RECAST = it.getValue(Recast::class.java) ?: Recast()
            if (RECAST.recast_hours != 0.0){
                recast_input_hours.setText(RECAST.recast_hours.toString())
            }
            recast_input_hours.requestFocus()
            recast_input_hours.setSelection(recast_input_hours.getText().length)
        }
    }
}