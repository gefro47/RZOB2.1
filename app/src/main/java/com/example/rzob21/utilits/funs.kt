package com.example.rzob21.utilits

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rzob21.R
import com.example.rzob21.data.CalendarInfoDatabase
import com.example.rzob21.models.VacationDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    APP_DATE = formate.format(calendar1.time)
    APP_DATE_PICK_MONTH_L = formate1.format(Date())
    APP_CALENDAR_DATE_YEAR = Date[0].toInt()
    APP_CALENDAR_DATE_YEAR_CHECK = Date[1].toInt()
    APP_CALENDAR_DATE_MONTH = Date[1].toInt() - 1
    APP_CALENDAR_DATE_MONTH_CHECK = Date[1].toInt()
    APP_CALENDAR_DATE_DAY = Date[2].toInt()
    APP_CALENDAR_DATE = APP_DATE_TODAY
    APP_DATE_DAY_OF_WEEK = calendar1.get(Calendar.DAY_OF_WEEK)
    return APP_DATE_TODAY
    Log.d("Date", APP_CALENDAR_DATE)
}

//fun clickOnItem(fragment: Fragment, list: MutableList<Int>, nextFragment: Fragment, ImageView: ImageView, Text: TextView, View: View){
//    val mDialogView = LayoutInflater.from(APP_ACTIVITY).inflate(
//        R.layout.change_event_list,
//        null
//    )
//    val mBuilder = AlertDialog.Builder(APP_ACTIVITY)
//        .setView(mDialogView)
//        .show()
//    if (list.contains(APP_CALENDAR_DATE_DAY)){
//        disabledItemOfChangeEventList(ImageView, Text, View)
//    }else{
//        mDialogView.container_for_recast.setOnClickListener {
//            fragment.replaceFragment(nextFragment)
//            mBuilder.cancel()
//        }
//    }
//}

fun vacationPlusDate(): Boolean{
    val status: Boolean
    val formate = SimpleDateFormat("yyyy-MM-dd")

    val calendarStart = Calendar.getInstance()
    calendarStart.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH, APP_CALENDAR_DATE_DAY)

    val calendarEnd = Calendar.getInstance()
    calendarEnd.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH, APP_CALENDAR_DATE_DAY)
    calendarEnd.add(Calendar.DAY_OF_MONTH, APP_VACATION_DAY - 1)
    APP_CALENDAR_DATE_PLUS_VACATION = formate.format(calendarEnd.time)

    var calendarCount = Calendar.getInstance()
    calendarCount.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH, APP_CALENDAR_DATE_DAY)

    var calendarCountFDOM = Calendar.getInstance()
    calendarCountFDOM.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH +1, 1)

    var calendarLastDOM = Calendar.getInstance()
    calendarLastDOM.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH +1, 1)
    calendarLastDOM.add(Calendar.DAY_OF_MONTH, -1)
    APP_CALENDAR_DATE_LAST_DAY_OF_MONTH = formate.format(calendarLastDOM.time)

    var calendarFirstDOM = Calendar.getInstance()
    calendarFirstDOM.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH +1, 1)
    APP_CALENDAR_DATE_FIRST_DAY_OF_MONTH = formate.format(calendarFirstDOM.time)

    APP_VACATION_DAY_CHECK = daysBetween(calendarStart, calendarLastDOM).toInt()
    if (APP_VACATION_DAY > APP_VACATION_DAY_CHECK){
        APP_VACATION_DAY_ON_WITH_MONTH = daysBetween(calendarStart, calendarLastDOM).toInt()
        APP_VACATION_DAY_ON_NEXT_MONTH = if (calendarFirstDOM.get(Calendar.DAY_OF_MONTH) == calendarEnd.get(Calendar.DAY_OF_MONTH)){
            1
        }else daysBetween(calendarFirstDOM, calendarEnd).toInt() + 1
        status = funOnNextMonth(APP_VACATION_DAY_ON_NEXT_MONTH, calendarCountFDOM, calendarFirstDOM, APP_VACATION_DAY_ON_WITH_MONTH, calendarCount, calendarStart)
    }else{
        status = funOnThisMonth(APP_VACATION_DAY, calendarCount, calendarStart)
    }
    return status
}

fun funOnThisMonth(schet: Int, calendarCount: Calendar, calendarStart: Calendar): Boolean{
    val status: Boolean
    if (checkVacation(schet, calendarCount, LIST_VACATION_OF_MONTH)) {
        insertVacationDate(schet, calendarCount, calendarStart)
        status = true
    }else{
//        showToast("Добавляемы отпуск совпадает с уже добавленным!")
        status = false
    }
    return status
}

fun funOnNextMonth(schet1: Int, calendarCount1: Calendar, calendarStart1: Calendar,schet2: Int, calendarCount2: Calendar, calendarStart2: Calendar): Boolean{
    val status: Boolean
//    val dao = CalendarInfoDatabase.getInstance(APP_ACTIVITY).vacationDao()
    if (checkVacation(schet1, calendarCount1, LIST_VACATION_OF_NEXT_MONTH)) {
        if (checkVacation(schet2, calendarCount2, LIST_VACATION_OF_MONTH)) {
            insertVacationDate(schet2, calendarCount2, calendarStart2)
            insertVacationDate(schet1, calendarCount1, calendarStart1)
            status = true
        }else{
//            showToast("Добавляемы отпуск совпадает с уже добавленным!")
            status = false
        }
    }else{
//        showToast("Добавляемы отпуск совпадает с уже добавленным!")
        status = false
    }
    return status
}

fun checkVacation(schet: Int, calendar: Calendar, List: List<String>): Boolean{
    var status = true
    val formate = SimpleDateFormat("yyyy-MM-dd")
    for (i in 0 until schet) {
        calendar.add(Calendar.DAY_OF_MONTH, i)
        val date = formate.format(calendar.time)
        if (List.contains(date)){
            status = false
            break
        }
        calendar.add(Calendar.DAY_OF_MONTH, -i)
    }
    return status
}

fun daysBetween(startDate: Calendar, endDate: Calendar): Long {
    val end = endDate.timeInMillis
    val start = startDate.timeInMillis
    return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start)) + 1
}

fun insertVacationDate(schet: Int, calendarCount: Calendar, calendarStart: Calendar){
    val formate = SimpleDateFormat("yyyy-MM-dd")
    for (i in 0 until schet) {
        calendarCount.add(Calendar.DAY_OF_MONTH, i)
        val countDate = formate.format(calendarCount.time)
        val startDate = formate.format(calendarStart.time)
        val dao = CalendarInfoDatabase.getInstance(APP_ACTIVITY).vacationDao()
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertVacationDate(VacationDate(countDate, startDate))
        }
        calendarCount.add(Calendar.DAY_OF_MONTH, -i)
    }
}