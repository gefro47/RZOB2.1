package com.example.rzob21.UI.fragments

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rzob21.ApiInterface.SickLeaveApi
import com.example.rzob21.ApiInterface.VacationApi
import com.example.rzob21.R
import com.example.rzob21.utilits.APP_HISTORY_OF_SICK_LEAVE_DATE
import com.example.rzob21.utilits.APP_HISTORY_OF_VACATION_DATE
import com.example.rzob21.utilits.initRecyclerViewForHistoryOfSickLeaveFragment
import com.example.rzob21.utilits.initRecyclerViewForHistoryOfVacationFragment
import kotlinx.android.synthetic.main.fragment_history_of_sick_leave.*
import kotlinx.android.synthetic.main.fragment_history_of_vacation.*
import kotlinx.android.synthetic.main.fragment_history_of_vacation.date_picker_history_of_vacation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


class HistoryOfSickLeaveFragment : BaseFragment(R.layout.fragment_history_of_sick_leave) {
    override fun onStart() {
        super.onStart()
        if (APP_HISTORY_OF_SICK_LEAVE_DATE == null){
            val calendar1 = Calendar.getInstance()
            val formate = SimpleDateFormat("yyyy-MM-dd")//date for FB
            APP_HISTORY_OF_SICK_LEAVE_DATE = Date.valueOf("${formate.format(calendar1.time)}")
        }
    }

    //    @SuppressLint("ClickableViewAccessibility")
    override fun onResume() {
        super.onResume()
        calendar_spinner()
        initAllSickLeave()
    }

    fun initAllSickLeave() {
        GlobalScope.launch(Dispatchers.Main) {
            val postOperation = async(Dispatchers.IO) {
                APP_HISTORY_OF_SICK_LEAVE_DATE?.let { SickLeaveApi().getAllByYear(it) }
            }
            postOperation.await()
            initRecyclerViewForHistoryOfSickLeaveFragment(history_of_sick_leave_recycler_view, requireContext())
        }
    }

    private fun calendar_spinner() {
        val today = Calendar.getInstance()
        date_picker_history_of_sick_leave.init(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val date = Date.valueOf("$year-${month + 1}-1")
            APP_HISTORY_OF_SICK_LEAVE_DATE = date
            initAllSickLeave()
        }
        (date_picker_history_of_sick_leave as ViewGroup).findViewById<View>(
            Resources.getSystem().getIdentifier("day", "id", "android")
        ).visibility =
            View.GONE
        (date_picker_history_of_sick_leave as ViewGroup).findViewById<View>(
            Resources.getSystem().getIdentifier("month", "id", "android")
        ).visibility =
            View.GONE
    }
}