package com.example.rzob21.UI.fragments

import android.app.DatePickerDialog
import android.view.View
import android.widget.DatePicker
import com.example.rzob21.R
import com.example.rzob21.utilits.APP_CALENDAR_DATE
import com.example.rzob21.utilits.APP_DATE
import com.example.rzob21.utilits.showToast
import kotlinx.android.synthetic.main.fragment_sick_leave.*
import java.sql.Date
import java.text.DateFormat
import java.util.*
import kotlin.time.milliseconds


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
            val mcurrentTime = Calendar.getInstance()
            val year = mcurrentTime.get(Calendar.YEAR)
            val month = mcurrentTime.get(Calendar.MONTH)
            val day = mcurrentTime.get(Calendar.DAY_OF_MONTH)

            val datePicker = context?.let { it1 ->
                DatePickerDialog(it1, object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        val stopDay = Date.valueOf("$year-${month + 1}-$dayOfMonth")
                        stop_sick_leave_text.setText("Конец больничного: ${dateFormatter.format(stopDay)}")
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

    }

}