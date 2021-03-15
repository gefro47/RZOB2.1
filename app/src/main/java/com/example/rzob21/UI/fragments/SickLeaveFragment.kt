package com.example.rzob21.UI.fragments

import android.view.KeyEvent
import android.view.View
import com.example.rzob21.R
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.fragment_sick_leave.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async


class SickLeaveFragment(val Day: String,var boolean: Boolean = false) : BaseChangeCalendarFragment(R.layout.fragment_sick_leave) {
    override fun onResume() {
        super.onResume()
        sick_leave_data.text = APP_CALENDAR_DATE
        if (boolean){
            sick_leave_delete_image.visibility = View.VISIBLE
            initSickLeave()
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
            deleteSickLeave()
            showToast(getString(R.string.toast_data_update))
            fragmentManager?.popBackStack()
        }
    }

    override fun change() {
        val days = sick_leave_input_days.text.toString()
        if (days.isEmpty() || days.toInt() == 0){
            showToast("Поле не может быть пустым\n или равным нулю!")
        }else {
            if (boolean) {
                if (APP_SICK_LEAVE_NUMBER_OF_DAYS != 0) {
                    val number_of_days = APP_SICK_LEAVE_NUMBER_OF_DAYS
                    if (sick_leave_input_days.text.toString().toInt() > number_of_days){
                        if (checkSickLeaveForUpdate(
                                number_of_days,
                                sick_leave_input_days.text.toString().toInt(),
                                LIST_SICK_LEAVE_OF_TWO_YEAR
                        )){
                            deleteSickLeave()
                            initSickLeaveList()
                            insertSickLeaveDays()
                        }else {
                            showToast("Новый больничный пересекается с другим больничным!")
                        }
                    }else {
                        deleteSickLeave()
                        initSickLeaveList()
                        insertSickLeaveDays()
                    }
                }
            }else {
                insertSickLeaveDays()
            }
        }
    }

    private fun insertSickLeaveDays() {
        APP_SICK_LEAVE_DAY = sick_leave_input_days.text.toString().toInt()
        if (sickLeaveCheckAndInsert()){
            showToast(getString(R.string.toast_data_update))
            fragmentManager?.popBackStack()
        }else{
            showToast("Добавляемый больничный совпадает с уже добавленным!")
        }
    }

    private fun deleteSickLeave() {
        CoroutineScope(Dispatchers.IO).async {
            dao.deleteSickLeaveDate(Day)
            dao.deleteSickLeave(Day)
        }
    }

    private fun initSickLeave() {
        CoroutineScope(Dispatchers.IO).async {
            APP_SICK_LEAVE_NUMBER_OF_DAYS = dao.getSickLeave(Day).number_of_days
            if (APP_SICK_LEAVE_NUMBER_OF_DAYS != 0){
                sick_leave_input_days.setText(APP_SICK_LEAVE_NUMBER_OF_DAYS.toString())
                sick_leave_input_days.requestFocus()
                sick_leave_input_days.setSelection(APP_SICK_LEAVE_NUMBER_OF_DAYS.toString().length)
            }
        }
    }
}