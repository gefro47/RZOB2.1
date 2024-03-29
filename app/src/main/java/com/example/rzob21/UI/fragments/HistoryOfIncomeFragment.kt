package com.example.rzob21.UI.fragments

import android.content.res.Resources
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.example.rzob21.ApiInterface.IncomeApi
import com.example.rzob21.R
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.fragment_history_of_income.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


class HistoryOfIncomeFragment : BaseFragment(R.layout.fragment_history_of_income) {

    override fun onStart() {
        super.onStart()
        if (APP_INCOME_OF_HISTORY_DATE == null){
            val calendar1 = Calendar.getInstance()
            val formate = SimpleDateFormat("yyyy-MM-dd")//date for FB
            APP_INCOME_OF_HISTORY_DATE = Date.valueOf("${formate.format(calendar1.time)}")
        }
    }

//    @SuppressLint("ClickableViewAccessibility")
    override fun onResume() {
        super.onResume()
        calendar_spinner()
        initAllIncome()
    }

    fun initAllIncome() {
        GlobalScope.launch(Dispatchers.Main) {
            val postOperation = async(Dispatchers.IO) {
                val formateYear = SimpleDateFormat("yyyy")
                IncomeApi().getAllByYear(formateYear.format(APP_INCOME_OF_HISTORY_DATE).toInt())
            }
            postOperation.await()
            initRecyclerViewForHistoryOfIncomeFragment(history_recycler_view, requireContext())
        }
    }

    private fun calendar_spinner() {
        val today = Calendar.getInstance()
        date_picker_month_of_history.init(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val date = Date.valueOf("$year-${month + 1}-1")
            APP_INCOME_OF_HISTORY_DATE = date
            initAllIncome()
        }
        (date_picker_month_of_history as ViewGroup).findViewById<View>(
            Resources.getSystem().getIdentifier("day", "id", "android")
        ).visibility =
            View.GONE
        (date_picker_month_of_history as ViewGroup).findViewById<View>(
            Resources.getSystem().getIdentifier("month", "id", "android")
        ).visibility =
            View.GONE
    }
}