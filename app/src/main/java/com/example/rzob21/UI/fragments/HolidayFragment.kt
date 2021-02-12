package com.example.rzob21.UI.fragments

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rzob21.R
import com.example.rzob21.models.Holiday
import com.example.rzob21.models.Recast
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.fragment_holiday.*
import kotlinx.android.synthetic.main.fragment_recast.*


class HolidayFragment : BaseChangeCalendarFragment(R.layout.fragment_holiday) {
    override fun onResume() {
        super.onResume()
        holiday_data.text = APP_CALENDAR_DATE
        holiday_input_days.requestFocus()
        showKeyboard()
        holiday_input_days.setOnKeyListener(View.OnKeyListener{ v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                change()
                return@OnKeyListener true
            }
            false
        })
    }
    override fun change() {
        val hours = holiday_input_days.text.toString()
        if (hours.isEmpty() || hours.toInt() == 0){
            showToast("Поле не может быть пустым\n или равным нулю!")
        }else {
            APP_HOLIDAYS_DAY = holiday_input_days.text.toString().toInt()
            holidayPlusDate()
            val holiday = Holiday(APP_CALENDAR_DATE, APP_CALENDAR_DATE_PLUS_HOLIDAY, APP_CALENDAR_DATE_DAY, APP_DATE_DAY_OF_WEEK)

                showToast(getString(R.string.toast_data_update))
                fragmentManager?.popBackStack()
        }
    }
}