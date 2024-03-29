package com.example.rzob21.UI.fragments

import android.app.DatePickerDialog
import android.graphics.Color
import android.view.View
import android.widget.DatePicker
import com.example.rzob21.ApiInterface.RecastApi
import com.example.rzob21.ApiInterface.SickLeaveApi
import com.example.rzob21.R
import com.example.rzob21.models.PeriodModel
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


class SickLeaveFragment(val sickLeave: PeriodModel = PeriodModel(date_start = APP_DATE.toString(),date_stop = ""), var boolean: Boolean = false) : BaseChangeCalendarFragment(R.layout.fragment_sick_leave) {


    val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG)

    override fun onResume() {
        super.onResume()
        sick_leave_data.text = APP_CALENDAR_DATE
        start_sick_leave_text.setText("Начало больничного: $APP_CALENDAR_DATE")
        APP_DATE?.let { RecastApi().getAllByYear(it) }
        if (boolean){
            sick_leave_delete_image.visibility = View.VISIBLE
            stop_sick_leave_text.setText("Конец больничного: ${dateFormatter.format(Date.valueOf(sickLeave.date_stop))}")
            stop_sick_leave_text.setTextColor(Color.parseColor("#837E7E"))
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
                        stop_sick_leave_text.setTextColor(Color.parseColor("#000000"))
                    }
                }, year, month, day)
            }
            val calendar2 = Calendar.getInstance()
            if (boolean){
                calendar2.set(
                    sickLeave.date_start.split("-")[0].toInt(),
                    sickLeave.date_start.split("-")[1].toInt()-1,
                    sickLeave.date_start.split("-")[2].toInt()
                )
            }else{
                calendar2.set(
                    APP_DATE.toString().split("-")[0].toInt(),
                    APP_DATE.toString().split("-")[1].toInt()-1,
                    APP_DATE.toString().split("-")[2].toInt()
                )
            }

            datePicker?.datePicker?.minDate = calendar2.timeInMillis
            datePicker?.show()
        }

        sick_leave_delete_image.setOnClickListener {
            val initDelete = GlobalScope.launch(Dispatchers.Main) {
                val postOperation = async(Dispatchers.IO) {
                    sickLeave.id?.let { it1 -> SickLeaveApi().delete(it1) }
                }
                postOperation.await()
                fragmentManager?.popBackStack()
            }
        }
    }

    override fun change() {
        if (!boolean){
            if (check_date(boolean, sickLeave)){
                if (stop_sick_leave_text.text != "") {
                    val initPost = GlobalScope.launch(Dispatchers.Main) {
                        val postOperation = async(Dispatchers.IO) {
                            SickLeaveApi().post(SICK_LEAVE_STOP.toString())
                        }
                        postOperation.await()
                        fragmentManager?.popBackStack()
                    }
                }else {
                    showToast("Укажите окончание больничного!")
                }
            }else{
                showToast("Этот больничный пересекается с другими событиями!")
            }
        }else if (boolean){
            if (stop_sick_leave_text.text != "Конец больничного: ${dateFormatter.format(Date.valueOf(sickLeave.date_stop))}"){
                if (check_date(boolean, sickLeave)){
                    val initPut = GlobalScope.launch(Dispatchers.Main) {
                        val postOperation = async(Dispatchers.IO) {
                            SickLeaveApi().put(PeriodModel(sickLeave.id, sickLeave.date_start, SICK_LEAVE_STOP.toString()))
                        }
                        postOperation.await()
                        fragmentManager?.popBackStack()
                    }
                }else{
                    showToast("Этот больничный пересекается с другими событиями!")
                }
            }else{
                showToast("Конец больничного не изменен!")
            }
        }
    }
}