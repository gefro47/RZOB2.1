package com.example.rzob21.UI.fragments

import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rzob21.R
import com.example.rzob21.UI.adapters.RecastAdapter
import com.example.rzob21.UI.adapters.VacationAdapter
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
        initFields()
        lifecycleScope.launch {
            initVacationList()
            initRecastList()
            initRecyclerView()
        }
    }




    private fun initFields() {
        container_for_add.setOnClickListener {
            val mDialogView = LayoutInflater.from(APP_ACTIVITY).inflate(
                R.layout.change_event_list,
                null
            )
            val mBuilder = AlertDialog.Builder(APP_ACTIVITY)
                .setView(mDialogView)
                .show()

            if (LIST_OF_RECAST_DATE.contains(APP_DATE)){
//            if (LIST_OF_HOLIDAYS.contains(APP_DATE)){
                disabledItemOfChangeEventList(mDialogView.container_for_recast.recast_image, mDialogView.container_for_recast.recast_label_view, mDialogView.container_for_recast)
            }else{
            mDialogView.container_for_recast.setOnClickListener {
                replaceFragment(RecastFragment(APP_DATE))
                mBuilder.cancel()
            }
            }
            if (LIST_VACATION_OF_MONTH.contains(APP_DATE)){
                disabledItemOfChangeEventList(mDialogView.container_for_holiday.holiday,mDialogView.container_for_holiday.holiday_label,mDialogView.container_for_holiday)
            }else{
                mDialogView.container_for_holiday.setOnClickListener {
                    replaceFragment(VacationFragment(APP_DATE))
                    mBuilder.cancel()
                }
            }
        }

    }

    private suspend fun initRecyclerView() {
        val adapter1 = VacationAdapter()
        val adapter2 = RecastAdapter()
        val adapter = ConcatAdapter(adapter1, adapter2)
        val recyclerView = calendar_recycle_view
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val vacation = dao.getVacationList(APP_CALENDAR_DATE_MONTH + 1)
        adapter1.setData(vacation)
        val recast = dao.getRecastList(APP_CALENDAR_DATE_MONTH + 1)
        Log.d("recast", recast.toString())
        adapter2.setData(recast)
    }

    fun initDay(){
        val p_date_m = APP_CALENDAR_DATE_MONTH
        val p_date_y = APP_CALENDAR_DATE_YEAR
        if (p_date_m != APP_CALENDAR_DATE_MONTH_CHECK || p_date_y != APP_CALENDAR_DATE_YEAR_CHECK){
            APP_CALENDAR_DATE_MONTH_CHECK = p_date_m
            APP_CALENDAR_DATE_YEAR_CHECK = p_date_y
            lifecycleScope.launch {
                initVacationList()
                initRecastList()
                initRecyclerView()
            }
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
}
//holiday_input_days.text.toString().toInt()