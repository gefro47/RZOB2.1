package com.example.rzob21.UI.fragments

import android.app.usage.UsageEvents
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.rzob21.ApiInterface.RecastApi
import com.example.rzob21.R
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.change_event_list.view.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.coroutines.*
import java.sql.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    val calendar1 = Calendar.getInstance()

    override fun onStart() {
        super.onStart()
        if(APP_CALENDAR_DATE == "") data.text = calendarikToday() else data.text = APP_CALENDAR_DATE
        calendarik()
        hideKeyboard()
//        showToast("start")
    }

    override fun onResume() {
        super.onResume()
        if (APP_DATE != null){
            calendar1.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH, APP_CALENDAR_DATE_DAY)
        }
        initRecast()
    }





    private fun initFields() {

        container_for_add.setOnClickListener {
            if (LIST_OF_RECAST_DATE.contains(APP_DATE)){
                showToast("В этот день уже добалена переработка!")
            }else if(LIST_VACATION_OF_MONTH.contains(APP_DATE)){
                showToast("В этот день уже добавлен отпуск!")
            }else if(LIST_SICK_LEAVE_OF_MONTH.contains(APP_DATE)){
                showToast("В этот день уже добавлен больничный!")
            }else{
                val mDialogView = LayoutInflater.from(APP_ACTIVITY).inflate(
                    R.layout.change_event_list,
                    null
                )
                val mBuilder = AlertDialog.Builder(APP_ACTIVITY)
                    .setView(mDialogView)
                    .show()

                mDialogView.container_for_recast.setOnClickListener {
                    replaceFragment(RecastFragment())
                    mBuilder.cancel()
                }
//                mDialogView.container_for_holiday.setOnClickListener {
//                    replaceFragment(VacationFragment(APP_DATE))
//                    mBuilder.cancel()
//                }
                mDialogView.container_for_sick_days.setOnClickListener {
                    replaceFragment(SickLeaveFragment())
                    mBuilder.cancel()
                }
            }
        }
    }

    fun initDay(){
        val p_date_m = APP_CALENDAR_DATE_MONTH
        val p_date_y = APP_CALENDAR_DATE_YEAR
        if (p_date_m != APP_CALENDAR_DATE_MONTH_CHECK || p_date_y != APP_CALENDAR_DATE_YEAR_CHECK){
            APP_CALENDAR_DATE_MONTH_CHECK = p_date_m
            APP_CALENDAR_DATE_YEAR_CHECK = p_date_y
            initRecast()
        }
    }

    fun calendarik(){
        val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG)
        val formate1 = SimpleDateFormat("MMMM")
        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->

            APP_CALENDAR_DATE_YEAR = year
            APP_CALENDAR_DATE_MONTH = month
            APP_CALENDAR_DATE_DAY = dayOfMonth

           // val calendar1 = Calendar.getInstance()
            calendar1.set(year, month, dayOfMonth)
            APP_DATE = Date.valueOf("$year-${month + 1}-$dayOfMonth")
            APP_CALENDAR_DATE = dateFormatter.format(calendar1.time)
            APP_DATE_PICK_MONTH_L = formate1.format(calendar1.time)
            data.text = APP_CALENDAR_DATE
            Log.d("Date", APP_DATE.toString())
            initDay()
        }
    }


    fun initRecast(){
//      Вариант 3
        val initYearAndMonth = GlobalScope.launch(Dispatchers.Main) {
            val postOperation = async(Dispatchers.IO) {
                APP_DATE?.let { RecastApi().getAllByYearAndMonth(it) }
                Log.d("Kek3", APP_DATE.toString())
            }
            postOperation.await()
            LIST_OF_RECAST_DATE = mutableListOf()
            for (i in LIST_RECAST_OF_MONTH.indices) {
                LIST_OF_RECAST_DATE.add(Date.valueOf(LIST_RECAST_OF_MONTH[i].date))
//                Log.d("listdateofrecast", LIST_OF_RECAST_DATE.toString())
//                Log.d("listdateofrecast", APP_DATE.toString())
            }
            initRecyclerViewForCalendarFragment(calendar_recycle_view, requireContext())
            initFields()
        }

    }

}