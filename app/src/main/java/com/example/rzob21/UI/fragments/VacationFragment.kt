package com.example.rzob21.UI.fragments

import android.view.KeyEvent
import android.view.View
import com.example.rzob21.R
import com.example.rzob21.data.CalendarInfoDatabase
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.event_vacation_item.*
import kotlinx.android.synthetic.main.fragment_vacation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async


class VacationFragment(val Day: String,var boolean: Boolean = false) : BaseChangeCalendarFragment(R.layout.fragment_vacation) {

    val dao = CalendarInfoDatabase.getInstance(APP_ACTIVITY).calendarInfoDao()

    override fun onResume() {
        super.onResume()
        vacation_data.text = APP_CALENDAR_DATE
        if (boolean){
            vacation_delete_image.visibility = View.VISIBLE
            initVacation()
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
            deleteVacation()
            showToast(getString(R.string.toast_data_update))
            fragmentManager?.popBackStack()
        }
    }
    override fun change() {
        val days = vacation_input_days.text.toString()
        if (days.isEmpty() || days.toInt() == 0){
            showToast("Поле не может быть пустым\n или равным нулю!")
        }else if (days.toInt() > 28) {
            showToast("Невозможно ввести отпуск больше 28 дней, разбейте отпуск на несколько частей!")
        }else {
            if (boolean) {
                if (APP_VACATION_NUMBER_OF_DAYS != 0) {
                    val number_of_days = APP_VACATION_NUMBER_OF_DAYS
                    if (vacation_input_days.text.toString().toInt() > number_of_days) {
                        if (checkVacationForUpdate(
                                number_of_days,
                                vacation_input_days.text.toString().toInt(),
                                LIST_VACATION_OF_MONTH
                            )
                        ) {
                            deleteVacation()
                            initVacationList()
                            insertVacationDays()
                        } else {
                            showToast("Новый отпуск пересекается с другим отпуском!")
                        }
                    } else {
                        deleteVacation()
                        initVacationList()
                        insertVacationDays()
                    }
                }
            } else {
                insertVacationDays()
            }
        }
    }



    fun deleteVacation(){
        CoroutineScope(Dispatchers.IO).async {
            dao.deleteVacationDate(Day)
            dao.deleteVacation(Day)
        }
    }

    fun initVacation(){
        CoroutineScope(Dispatchers.IO).async {
            APP_VACATION_NUMBER_OF_DAYS = dao.getVacation(Day).number_of_days
            if (APP_VACATION_NUMBER_OF_DAYS != 0){
                vacation_input_days.setText(APP_VACATION_NUMBER_OF_DAYS.toString())
                vacation_input_days.requestFocus()
                vacation_input_days.setSelection(APP_VACATION_NUMBER_OF_DAYS.toString().length)
            }
        }
    }

    private fun insertVacationDays(){
            APP_VACATION_DAY = vacation_input_days.text.toString().toInt()
            if(vacationCheckAndInsert()) {
                showToast(getString(R.string.toast_data_update))
                fragmentManager?.popBackStack()
            }else{
//                Toast.makeText(context, "kek", Toast.LENGTH_SHORT).show()
                showToast("Добавляемый отпуск совпадает с уже добавленным!")
//                fragmentManager?.popBackStack()
            }
    }
}