package com.example.rzob21.utilits

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rzob21.R
import com.example.rzob21.UI.fragments.RecastFragment
import kotlinx.android.synthetic.main.change_event_list.view.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun showToast (message:String){
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
            .replace(R.id.dataContainer,
                fragment
            ).commit()
    }else{
        supportFragmentManager.beginTransaction()
            .replace(R.id.dataContainer,
                fragment
            ).commit()
    }

}

fun Fragment.replaceFragment(fragment: Fragment){
    this.fragmentManager?.beginTransaction()
        ?.addToBackStack(null)
        ?.replace(R.id.dataContainer,
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
//    data.text = APP_DATE_TODAY
    APP_DATE_PICK_MONTH_L = formate1.format(Date())
    APP_CALENDAR_DATE_YEAR = Date[0].toInt()
    APP_CALENDAR_DATE_YEAR_CHECK = Date[1].toInt()
    APP_CALENDAR_DATE_MONTH = Date[1].toInt()
    APP_CALENDAR_DATE_MONTH_CHECK = Date[1].toInt()
    APP_CALENDAR_DATE_DAY = Date[2].toInt()
    APP_CALENDAR_DATE = APP_DATE_TODAY
    APP_DATE_DAY_OF_WEEK = calendar1.get(Calendar.DAY_OF_WEEK)
    return APP_DATE_TODAY
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

fun holidayPlusDate(){
    val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG)
    val calendar1 = Calendar.getInstance()
    calendar1.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH, APP_CALENDAR_DATE_DAY)
    calendar1.add(Calendar.DAY_OF_MONTH, APP_HOLIDAYS_DAY-1)
    val kekCal = dateFormatter.format(calendar1.time)
    APP_CALENDAR_DATE_PLUS_HOLIDAY = kekCal
//    showToast("$kekCal")
}