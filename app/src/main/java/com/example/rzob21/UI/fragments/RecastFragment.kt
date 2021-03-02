package com.example.rzob21.UI.fragments

import android.util.Log
import android.view.KeyEvent
import android.view.View
import com.example.rzob21.R
import com.example.rzob21.data.CalendarInfoDatabase
import com.example.rzob21.models.Recast
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.fragment_recast.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class RecastFragment(val Day: String,var boolean: Boolean = false) : BaseChangeCalendarFragment(R.layout.fragment_recast) {

    val dao = CalendarInfoDatabase.getInstance(APP_ACTIVITY).calendarInfoDao()

    override fun onResume() {
        super.onResume()
        if (boolean){
            recast_delete_image.visibility = View.VISIBLE
            initRecast()
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
            CoroutineScope(Dispatchers.IO).async {
                dao.deleteByRecastDay(Day)
            }
            showToast(getString(R.string.toast_data_update))
            fragmentManager?.popBackStack()
        }
    }

    override fun change() {
        val hours = recast_input_hours.text.toString()
        if (hours.isEmpty() || hours.toDouble() == 0.0){
            showToast(getString(R.string.recast_toast_hours_is_empty))
        }else {
            val weekend = LIST_OF_HOLIDAYS.contains(Day)
            val recast = Recast(Day, hours.toDouble(), weekend, APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH + 1)
            CoroutineScope(Dispatchers.IO).async  {
                dao.insertRecast(recast)
            }
            showToast(getString(R.string.toast_data_update))
            fragmentManager?.popBackStack()
        }
    }
    fun initRecast(){
        CoroutineScope(Dispatchers.IO).async {
            val hours = dao.getRecastHours(Day).recast_hours
            Log.d("hours", hours.toString())
            if (hours != 0.0) {
                recast_input_hours.setText(hours.toString())
                recast_input_hours.requestFocus()
                recast_input_hours.setSelection(hours.toString().length)
            }
        }
    }
}