package com.example.rzob21.utilits

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rzob21.ApiInterface.IncomeApi
import com.example.rzob21.R
import com.example.rzob21.models.PeriodModel
import kotlinx.android.synthetic.main.add_income_dialog.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


fun showToast(message: String){
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.replaceActivity(activity: AppCompatActivity){
    val intent = Intent(this, activity::class.java)
    startActivity(intent)
    this.finish()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, addStack: Boolean = true){
    if (addStack){
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.dataContainer,
                fragment
            ).commit()
    }else{
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.dataContainer,
                fragment
            ).commit()
    }

}

fun Fragment.replaceFragment(fragment: Fragment){
    this.fragmentManager?.beginTransaction()
        ?.addToBackStack(null)
        ?.replace(
            R.id.dataContainer,
            fragment
        )?.commit()
}

fun hideKeyboard() {
    val imm: InputMethodManager = APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(APP_ACTIVITY.window.decorView.windowToken, 0)
}

fun showKeyboard() {
    val imm: InputMethodManager = APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun disabledItemOfChangeEventList(ImageView: ImageView, Text: TextView, View: View){
    View.isClickable = false
    ImageView.setColorFilter(R.color.colorGrey)
    Text.setTextColor(Color.parseColor("#837E7E"))
}

fun calendarikToday():String {
    val calendar1 = Calendar.getInstance()
    val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG)
    val formate = SimpleDateFormat("yyyy-MM-dd")//date for FB
    val formate1 = SimpleDateFormat("MMMM")
    val Date =formate.format(Date()).split("-")
    APP_DATE_TODAY = dateFormatter.format(Date())
    APP_DATE = java.sql.Date.valueOf("${formate.format(calendar1.time)}")
    APP_DATE_PICK_MONTH_L = formate1.format(Date())
    APP_CALENDAR_DATE_YEAR = Date[0].toInt()
    APP_CALENDAR_DATE_YEAR_CHECK = Date[1].toInt()
    APP_CALENDAR_DATE_MONTH = Date[1].toInt() - 1
    APP_CALENDAR_DATE_MONTH_CHECK = Date[1].toInt()
    APP_CALENDAR_DATE_DAY = Date[2].toInt()
    APP_CALENDAR_DATE = APP_DATE_TODAY
    return APP_DATE_TODAY
    Log.d("Date", APP_CALENDAR_DATE)
}



fun customAlertDialog(){
    val mDialogView = LayoutInflater.from(APP_ACTIVITY).inflate(
        R.layout.add_income_dialog,
        null
    )
    mDialogView.text_for_date.setText(INCOME_HISTORY_DATE_PICK_MONTH_AND_YEAR)
    val mBuilder = AlertDialog.Builder(APP_ACTIVITY)
        .setView(mDialogView)
        .show()
    mBuilder.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    mDialogView.input_income_dialog.requestFocus()
    mDialogView.input_income_dialog.setSelection(mDialogView.input_income_dialog.getText().length)
    showKeyboard()
    mDialogView.input_income_dialog.onFocusChangeListener = View.OnFocusChangeListener { p0, p1 ->
        if (!p1){
            // hide soft keyboard when edit text lost focus
            hideKeyboard()
        }
    }
    mDialogView.input_income_dialog.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
            IncomeApi().post(
                mDialogView.input_income_dialog.text.toString().toDouble(),
                APP_INCOME_OF_HISTORY_DATE?.toString()!!.split("-")[1].toInt(),
                APP_INCOME_OF_HISTORY_DATE?.toString()!!.split("-")[0].toInt(),
                false
            )
            val imm = APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(mDialogView.windowToken, 0)
            mBuilder.cancel()
            return@OnKeyListener true
        }
        false
    })

//    val dialogBuilder = AlertDialog.Builder(this)
//    dialogBuilder.setView(R.layout.temp)
//    val alertDialog = dialogBuilder.create()
//    alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//    alertDialog.show()
}

fun check_date(boolean: Boolean, sickLeave: PeriodModel): Boolean{
    val sick_leave_start = APP_DATE
    val sick_leave_stop =  SICK_LEAVE_STOP
    val dif = sick_leave_stop?.time?.minus(sick_leave_start?.time!!)?.let {
        TimeUnit.DAYS.convert(
            it, TimeUnit.MILLISECONDS)
    }

    if(boolean){
        val sick_leave_start_put = java.sql.Date.valueOf(sickLeave.date_start)
        val sick_leave_stop_put =  java.sql.Date.valueOf(sickLeave.date_stop)
        val dif_put = sick_leave_stop_put?.time?.minus(sick_leave_start_put?.time!!)?.let {
            TimeUnit.DAYS.convert(
                it, TimeUnit.MILLISECONDS)
        }
        val List_of_sick_leave_put = mutableListOf<java.sql.Date>()
        if (dif_put != null) {
            for (i in 0..dif_put.toInt()){
                List_of_sick_leave_put.add(java.sql.Date(sick_leave_start_put?.time!! + (one_day * i)))
            }
        }
        if (dif != null) {
            for (j in 0..dif.toInt()) {
                if (LIST_OF_SICK_LEAVE_DATE.contains(java.sql.Date(sick_leave_start?.time!! + (one_day * j)))
                    && !List_of_sick_leave_put.contains(java.sql.Date(sick_leave_start.time + (one_day * j)))) {
                    return false
                }else if(LIST_RECAST_OF_YEAR.contains(java.sql.Date(sick_leave_start.time + (one_day * j)))
                    && !List_of_sick_leave_put.contains(java.sql.Date(sick_leave_start.time + (one_day * j)))){
                    return false
                }else if(LIST_OF_VACATION_DATE.contains(java.sql.Date(sick_leave_start.time + (one_day * j)))
                    && !List_of_sick_leave_put.contains(java.sql.Date(sick_leave_start.time + (one_day * j)))){
                    return false
                }
            }
        }
        return true
    }else {
        if (dif != null) {
            for (j in 0..dif.toInt()) {
                if (LIST_OF_SICK_LEAVE_DATE.contains(java.sql.Date(sick_leave_start?.time!! + (one_day * j)))
                    || LIST_RECAST_OF_YEAR.contains(java.sql.Date(sick_leave_start.time + (one_day * j)))
                    ||LIST_OF_VACATION_DATE.contains(java.sql.Date(sick_leave_start.time + (one_day * j)))) {
                    return false
                }
            }
        }
        return true
    }
}