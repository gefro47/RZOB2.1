package com.example.rzob21.utilits

import android.util.Log
import com.example.rzob21.UI.fragments.CalendarFragment
import com.example.rzob21.models.Holiday
import com.example.rzob21.models.Recast
import com.example.rzob21.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.event_recast_item.view.*

lateinit var AUTH: FirebaseAuth
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var USER: User
lateinit var UID: String
lateinit var RECAST: Recast
lateinit var HOLIDAY: Holiday



const val NODE_USERS = "users"
const val NODE_RECAST = "recast"//переработка
const val NODE_HOLIDAY= "holiday"
const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_POSITION_AT_WORK = "position"//должность
const val CHILD_PAYMENT = "payment"//расчет
const val CHILD_AVERAGE_SALARY = "average_salary" //средняя зп
const val CHILD_SALARY = "salary"//зп
const val CHILD_WORK_DAY = "work_days"// кол-во раб дней
const val CHILD_WORKED_DAY = "worked_days"//отработанные дни
const val CHILD_SICK_DAYS = "sick_days"//больничные дни
const val CHILD_HOLIDAY = "holiday"//отпуск
const val CHILD_DAY_OF_WEEK = "day_of_week"//день недели
const val CHILD_RECAST_HOURS = "recast_hours"//
const val CHILD_RECAST_DAY = "day_of_month"
const val CHILD_START_SICK_DAYS ="start_sick_days"//начало больничного
const val CHILD_STOP_SICK_DAYS = "stop_sick_days"//конец больничного
const val CHILD_START_HOLIDAY ="start_holiday"//начало отпуска
const val CHILD_STOP_HOLIDAY = "stop_holiday"//конец отпуска
const val CHILD_NUMBER_OF_SICK_DAYS = "number_of_sick_days"//кол-во больничных дней
const val CHILD_NUMBER_OF_HOLIDAY = "number_of_holiday"//кол-во дней отпуска



fun initFirebase(){
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    USER = User()
    RECAST = Recast()
    HOLIDAY = Holiday()
    UID = AUTH.currentUser?.uid.toString()
}

inline fun saveData(Node: String ,value: Any, crossinline  function: () -> Unit) {
    REF_DATABASE_ROOT.child(Node)
        .child(UID).child(APP_CALENDAR_DATE_YEAR.toString())
        .child(APP_CALENDAR_DATE_MONTH.toString())
        .child(APP_CALENDAR_DATE_DAY.toString())
        .setValue(value)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun readData(Node: String ,Day: Int, crossinline  function: (it: DataSnapshot) -> Unit){
    REF_DATABASE_ROOT.child(Node).child(UID).child(APP_CALENDAR_DATE_YEAR.toString()).child(APP_CALENDAR_DATE_MONTH.toString()).child(Day.toString())
        .addListenerForSingleValueEvent(AppValueEventListener{
            function(it)
        })
}

fun readDataRecastForRecycleView(holder: CalendarFragment.RecastHolder,
                                 model: Recast){
    val mRefRecast: DatabaseReference = REF_DATABASE_ROOT.child(NODE_RECAST).child(UID).child(APP_CALENDAR_DATE_YEAR.toString())
        .child(APP_CALENDAR_DATE_MONTH.toString()).child(model.day_of_month.toString())
    mRefRecast.addValueEventListener(AppValueEventListener {
        val recast = it.getRecast()
//        if (recast.day_of_month!=0) LIST_OF_RECAST.add(Recast(recast.recast_hours,recast.day_of_month, recast.day_of_week))
        if (recast.day_of_month!=0) LIST_OF_RECAST_DATE.add(recast.day_of_month)
        Log.d("kek", LIST_OF_RECAST_DATE.toString())
        holder.date.text = "Переработка ${recast.day_of_month} $APP_DATE_PICK_MONTH_L"
        holder.info.text = "${recast.recast_hours} час(а)"
    })

}

fun readDataHolidayForRecycleView(holder: CalendarFragment.HolidayHolder,
                                 model: Holiday){
    val mRefHoliday: DatabaseReference = REF_DATABASE_ROOT.child(NODE_HOLIDAY).child(UID).child(APP_CALENDAR_DATE_YEAR.toString())
        .child(APP_CALENDAR_DATE_MONTH.toString()).child(model.day_of_month.toString())
    mRefHoliday.addValueEventListener(AppValueEventListener {
        val holiday = it.getHoliday()
        LIST_OF_HOLIDAY_DATE.add(holiday.day_of_month)
        holder.date.text = "Отпуск ${holiday.day_of_month} $APP_DATE_PICK_MONTH_L"
        holder.info.text = "C ${holiday.holiday_start} по ${holiday.holiday_end}"
    })

}

fun deleteData(valueNODE: String,valueDay: Int){
    REF_DATABASE_ROOT.child(valueNODE).child(UID).child(APP_CALENDAR_DATE_YEAR.toString()).child(APP_CALENDAR_DATE_MONTH.toString()).child(valueDay.toString())
        .removeValue()
}




fun DataSnapshot.getRecast(): Recast = this.getValue(Recast::class.java)?: Recast()
fun DataSnapshot.getHoliday(): Holiday = this.getValue(Holiday::class.java)?: Holiday()