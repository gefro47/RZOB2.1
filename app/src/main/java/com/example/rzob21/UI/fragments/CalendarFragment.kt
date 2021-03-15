package com.example.rzob21.UI.fragments

import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.rzob21.R
import com.example.rzob21.data.CalendarInfoDatabase
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.change_event_list.view.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    val dao = CalendarInfoDatabase.getInstance(APP_ACTIVITY).calendarInfoDao()

    override fun onStart() {
        super.onStart()
        if(APP_CALENDAR_DATE == "") data.text = calendarikToday() else data.text = APP_CALENDAR_DATE
        calendarik()
        hideKeyboard()
    }

    override fun onResume() {
        super.onResume()
        init()
    }




    private fun initFields() {
        container_for_add.setOnClickListener {
            if (LIST_RECAST_OF_MONTH.contains(APP_DATE)){
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
                    replaceFragment(RecastFragment(APP_DATE))
                    mBuilder.cancel()
                }
                mDialogView.container_for_holiday.setOnClickListener {
                    replaceFragment(VacationFragment(APP_DATE))
                    mBuilder.cancel()
                }
                mDialogView.container_for_sick_days.setOnClickListener {
                    replaceFragment(SickLeaveFragment(APP_DATE))
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
            init()
        }
    }

    fun calendarik(){
        val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG)
        val formate1 = SimpleDateFormat("MMMM")
        val formate = SimpleDateFormat("yyyy-MM-dd")
        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->

            APP_CALENDAR_DATE_YEAR = year
            APP_CALENDAR_DATE_MONTH = month
            APP_CALENDAR_DATE_DAY = dayOfMonth

            val calendar1 = Calendar.getInstance()
            calendar1.set(year, month, dayOfMonth)
            APP_DATE = formate.format(calendar1.time)
            APP_CALENDAR_DATE = dateFormatter.format(calendar1.time)
            APP_DATE_PICK_MONTH_L = formate1.format(calendar1.time)
            data.text = APP_CALENDAR_DATE
            Log.d("Date", APP_DATE)
            APP_DATE_DAY_OF_WEEK = calendar1.get(Calendar.DAY_OF_WEEK)
            initDay()
        }
    }

    fun init(){
        lifecycleScope.launch {
            initVacationList()
            initRecastList()
            initSickLeaveList()
            initRecyclerView(calendar_recycle_view, requireContext())
            initFields()
        }
    }
}