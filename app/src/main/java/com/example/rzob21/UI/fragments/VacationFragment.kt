package com.example.rzob21.UI.fragments

import android.app.DatePickerDialog
import android.graphics.Color
import android.view.KeyEvent
import android.view.View
import android.widget.DatePicker
import com.example.rzob21.ApiInterface.SickLeaveApi
import com.example.rzob21.ApiInterface.VacationApi
import com.example.rzob21.R
import com.example.rzob21.models.PeriodModel
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.event_vacation_item.*
import kotlinx.android.synthetic.main.fragment_sick_leave.*
import kotlinx.android.synthetic.main.fragment_vacation.*
import kotlinx.android.synthetic.main.fragment_vacation.stop_vacation_text
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class VacationFragment(val vacation: PeriodModel = PeriodModel(date_start = APP_DATE.toString(),date_stop = ""), var boolean: Boolean = false) : BaseChangeCalendarFragment(R.layout.fragment_vacation) {

    val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG)

    override fun onResume() {
        super.onResume()
        vacation_data.text = APP_CALENDAR_DATE
        start_vacation_text.setText("Начало отпуска: $APP_CALENDAR_DATE")
        if (boolean){
            vacation_delete_image.visibility = View.VISIBLE
            stop_vacation_text.setText("Конец отпуска: ${dateFormatter.format(Date.valueOf(vacation.date_stop))}")
            stop_vacation_text.setTextColor(Color.parseColor("#837E7E"))
        }else vacation_delete_image.visibility = View.INVISIBLE
        date_picker_vacation.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val year = currentTime.get(Calendar.YEAR)
            val month = currentTime.get(Calendar.MONTH)
            val day = currentTime.get(Calendar.DAY_OF_MONTH)

            val datePicker = context?.let { it1 ->
                DatePickerDialog(it1, object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        VACATION_STOP = Date.valueOf("$year-${month + 1}-$dayOfMonth")
                        stop_vacation_text.setText("Конец отпуска: ${dateFormatter.format(VACATION_STOP)}")
                        stop_vacation_text.setTextColor(Color.parseColor("#000000"))
                    }
                }, year, month, day)
            }
            val calendar2 = Calendar.getInstance()
            if (boolean){
                calendar2.set(
                    vacation.date_start.split("-")[0].toInt(),
                    vacation.date_start.split("-")[1].toInt()-1,
                    vacation.date_start.split("-")[2].toInt()
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

        vacation_delete_image.setOnClickListener {
            val initDelete = GlobalScope.launch(Dispatchers.Main) {
                val postOperation = async(Dispatchers.IO) {
                    vacation.id?.let { it1 -> VacationApi().delete(it1) }
                }
                postOperation.await()
                fragmentManager?.popBackStack()
            }
        }
    }
    override fun change() {
        if (!boolean){
            if (check_date(boolean, vacation)){
                if (stop_vacation_text.text != "") {
                    if (TimeUnit.DAYS.convert(VACATION_STOP?.time!! - APP_DATE?.time!!, TimeUnit.MILLISECONDS) <= 27) {
                        val initPost = GlobalScope.launch(Dispatchers.Main) {
                            val postOperation = async(Dispatchers.IO) {
                                VacationApi().post(VACATION_STOP.toString())
                            }
                            postOperation.await()
                            fragmentManager?.popBackStack()
                        }
                    }else{
                        showToast("Отпуск не может быть больше 28 дней!")
                    }
                }else {
                    showToast("Укажите окончание отпуска!")
                }
            }else{
                showToast("Этот отпуск пересекается с другими событиями!")
            }
        }else if (boolean){
            if (stop_vacation_text.text != "Конец отпуска: ${dateFormatter.format(Date.valueOf(vacation.date_stop))}"){
                if (check_date(boolean, vacation)){
                    val initPut = GlobalScope.launch(Dispatchers.Main) {
                        val postOperation = async(Dispatchers.IO) {
                            VacationApi().put(PeriodModel(vacation.id, vacation.date_start, SICK_LEAVE_STOP.toString()))
                        }
                        postOperation.await()
                        fragmentManager?.popBackStack()
                    }
                }else{
                    showToast("Этот отпуск пересекается с другими событиями!")
                }
            }else{
                showToast("Конец отпуска не изменен!")
            }
        }
    }

}