package com.example.rzob21.UI.fragments

import android.app.DatePickerDialog
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import com.example.rzob21.ApiInterface.IncomeApi
import com.example.rzob21.ApiInterface.RecastApi
import com.example.rzob21.R
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.fragment_month_info.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS


class MonthInfoFragment : BaseFragment(R.layout.fragment_month_info) {

    override fun onStart() {
        super.onStart()
        if (APP_INCOME_DATE == null){
            val calendar1 = Calendar.getInstance()
            val formate = SimpleDateFormat("yyyy-MM-dd")//date for FB
            APP_INCOME_DATE = Date.valueOf("${formate.format(calendar1.time)}")
        }
    }

    override fun onResume() {
        super.onResume()
        calendar_spinner()
//        showToast(APP_INCOME_DATE.toString())
        calculate.setOnClickListener {
            if(APP_INCOME_DATE != null){
                val initIncome = GlobalScope.launch(Dispatchers.Main) {
                    val getOperation = async(Dispatchers.IO) {
                        APP_INCOME_DATE?.let {IncomeApi().getIncome(it) }
                    }
                    getOperation.await()
                    incomeView.setText("${INCOME?.income_of_money}")
                }
            }
        }
    }

    private fun calendar_spinner() {
        val today = Calendar.getInstance()
        date_picker_month.init(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
//            val month = month + 1
            val date = Date.valueOf("$year-${month+1}-1")
            APP_INCOME_DATE = date
//            showToast(date.toString())
//            val msg = "You Selected: $month/$year"
//            showToast(msg)
        }
        (date_picker_month as ViewGroup).findViewById<View>(
            Resources.getSystem().getIdentifier("day", "id", "android")
        ).visibility =
            View.GONE
    }
}