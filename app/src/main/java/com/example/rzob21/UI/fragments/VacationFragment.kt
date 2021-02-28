package com.example.rzob21.UI.fragments

import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.rzob21.R
import com.example.rzob21.data.CalendarInfoDatabase
import com.example.rzob21.models.Vacation
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.fragment_recast.*
import kotlinx.android.synthetic.main.fragment_vacation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class VacationFragment : BaseChangeCalendarFragment(R.layout.fragment_vacation) {

    val dao = CalendarInfoDatabase.getInstance(APP_ACTIVITY).vacationDao()

    override fun onResume() {
        super.onResume()
        vacation_data.text = APP_CALENDAR_DATE
        vacation_input_days.requestFocus()
        showKeyboard()
        vacation_input_days.setOnKeyListener(View.OnKeyListener{ v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                change()
                return@OnKeyListener true
            }
            false
        })



    }
    override fun change() {
        val hours = vacation_input_days.text.toString()
        if (hours.isEmpty() || hours.toInt() == 0){
            showToast("Поле не может быть пустым\n или равным нулю!")
        }else {
            APP_VACATION_DAY = vacation_input_days.text.toString().toInt()
            if(vacationPlusDate()) {
                next()
                fragmentManager?.popBackStack()
            }else{
//                Toast.makeText(context, "kek", Toast.LENGTH_SHORT).show()
                showToast("Добавляемый отпуск совпадает с уже добавленным!")
//                fragmentManager?.popBackStack()
            }
        }
    }
    fun next(){
        if (APP_VACATION_DAY > APP_VACATION_DAY_CHECK){
            val vacation1 = Vacation(APP_DATE, APP_CALENDAR_DATE_LAST_DAY_OF_MONTH, APP_VACATION_DAY_ON_WITH_MONTH, APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH + 1)
            val vacation2 = Vacation(APP_CALENDAR_DATE_FIRST_DAY_OF_MONTH, APP_CALENDAR_DATE_PLUS_VACATION, APP_VACATION_DAY_ON_NEXT_MONTH, APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH + 2)
            CoroutineScope(Dispatchers.IO).async {
                dao.insertVacation(vacation1)
                dao.insertVacation(vacation2)

                Log.d("kek", vacation1.toString())
                Log.d("kek", vacation2.toString())
                showToast(getString(R.string.toast_data_update))
                fragmentManager?.popBackStack()
            }
        }else{
            val vacation = Vacation(APP_DATE, APP_CALENDAR_DATE_PLUS_VACATION, APP_VACATION_DAY, APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH + 1)
            lifecycleScope.launch {
                dao.insertVacation(vacation)
                Log.d("kek", vacation.toString())
                showToast(getString(R.string.toast_data_update))
                fragmentManager?.popBackStack()
            }
        }
    }
}