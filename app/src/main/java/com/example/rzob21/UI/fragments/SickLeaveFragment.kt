package com.example.rzob21.UI.fragments

import android.app.DatePickerDialog
import android.view.View
import android.widget.DatePicker
import com.example.rzob21.ApiInterface.SickLeaveApi
import com.example.rzob21.R
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.fragment_sick_leave.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class SickLeaveFragment(var boolean: Boolean = false) : BaseChangeCalendarFragment(R.layout.fragment_sick_leave) {

    val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG)

    override fun onResume() {
        super.onResume()
        sick_leave_data.text = APP_CALENDAR_DATE
        start_sick_leave_text.setText("Начало больничного: $APP_CALENDAR_DATE")
        if (boolean){
            sick_leave_delete_image.visibility = View.VISIBLE
        }else sick_leave_delete_image.visibility = View.INVISIBLE

        date_picker_sick_leave.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val year = currentTime.get(Calendar.YEAR)
            val month = currentTime.get(Calendar.MONTH)
            val day = currentTime.get(Calendar.DAY_OF_MONTH)

            val datePicker = context?.let { it1 ->
                DatePickerDialog(it1, object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        SICK_LEAVE_STOP = Date.valueOf("$year-${month + 1}-$dayOfMonth")
                        stop_sick_leave_text.setText("Конец больничного: ${dateFormatter.format(SICK_LEAVE_STOP)}")
                    }
                }, year, month, day)
            }
            val calendar2 = Calendar.getInstance()
            calendar2.set(
                APP_DATE.toString().split("-")[0].toInt(),
                APP_DATE.toString().split("-")[1].toInt()-1,
                APP_DATE.toString().split("-")[2].toInt()
            )
            datePicker?.datePicker?.minDate = calendar2.timeInMillis
            datePicker?.show()
        }

        sick_leave_delete_image.setOnClickListener {

            showToast(getString(R.string.toast_data_update))
            fragmentManager?.popBackStack()
        }
    }

    override fun change() {
        if (!boolean){
            if (check_date()){
                val initPost = GlobalScope.launch(Dispatchers.Main) {
                    val postOperation = async(Dispatchers.IO) {
                        SickLeaveApi().post(SICK_LEAVE_STOP.toString())
                    }
                    postOperation.await()
                    fragmentManager?.popBackStack()
                }
            }else{
                showToast("Этот больничный пересекается с другим, уже записанным, больничным!")
            }
        }
    }

    fun check_date(): Boolean{
        val sick_leave_start = APP_DATE
        val sick_leave_stop =  SICK_LEAVE_STOP
        val dif = sick_leave_stop?.time?.minus(sick_leave_start?.time!!)?.let {
            TimeUnit.DAYS.convert(
                it, TimeUnit.MILLISECONDS)
        }
        if (dif != null) {
            for (j in 0 .. dif.toInt()){
                if(LIST_OF_SICK_LEAVE_DATE.contains(Date(sick_leave_start?.time!! + (1000 * 60 * 60 * 24 * j)))){
                    return false
                }
            }
        }
        return true
    }

}