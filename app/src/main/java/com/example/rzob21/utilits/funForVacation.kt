package com.example.rzob21.utilits

import android.util.Log
import com.example.rzob21.models.Vacation
import com.example.rzob21.models.VacationDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

fun vacationCheckAndInsert(): Boolean{
    val status: Boolean
    val formate = SimpleDateFormat("yyyy-MM-dd")

    val calendarStart = Calendar.getInstance()
    calendarStart.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH, APP_CALENDAR_DATE_DAY)
    APP_DATE = formate.format(calendarStart.time)

    val calendarEnd = Calendar.getInstance()
    calendarEnd.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH, APP_CALENDAR_DATE_DAY)
    calendarEnd.add(Calendar.DAY_OF_MONTH, APP_VACATION_DAY - 1)
    APP_CALENDAR_DATE_PLUS_VACATION = formate.format(calendarEnd.time)

    var calendarCount = Calendar.getInstance()
    calendarCount.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH, APP_CALENDAR_DATE_DAY)

    var calendarCountFDOM = Calendar.getInstance()
    calendarCountFDOM.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH +1, 1)

    status = funOnThisMonth(APP_VACATION_DAY, calendarCount, calendarStart)
    return status
}

fun funOnThisMonth(schet: Int, calendarCount: Calendar, calendarStart: Calendar): Boolean{
    return if (checkVacation(schet, calendarCount, LIST_VACATION_OF_MONTH)&&
        checkVacation(schet, calendarCount, LIST_VACATION_OF_NEXT_MONTH)&&
        checkVacation(schet, calendarCount, LIST_SICK_LEAVE_OF_MONTH)&&
        checkVacation(schet, calendarCount, LIST_SICK_LEAVE_OF_NEXT_MONTH)&&
        checkVacation(schet, calendarCount, LIST_RECAST_OF_MONTH)&&
        checkVacation(schet, calendarCount, LIST_RECAST_OF_NEXT_MONTH)) {
        insertVacationDate(schet, calendarCount, calendarStart)
        insertVacation()
        true
    }else{
        false
    }
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

fun insertVacationDate(schet: Int, calendarCount: Calendar, calendarStart: Calendar){
    val formate = SimpleDateFormat("yyyy-MM-dd")
    for (i in 0 until schet) {
        calendarCount.add(Calendar.DAY_OF_MONTH, i)
        val countDate = formate.format(calendarCount.time)
        val startDate = formate.format(calendarStart.time)
//        val dao = CalendarInfoDatabase.getInstance(APP_ACTIVITY).calendarInfoDao()
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertVacationDate(VacationDate(countDate, startDate))
        }
        calendarCount.add(Calendar.DAY_OF_MONTH, -i)
    }
}

fun insertVacation(){
    val vacation = Vacation(
        APP_DATE,
        APP_CALENDAR_DATE_PLUS_VACATION,
        APP_VACATION_DAY,
        APP_CALENDAR_DATE_YEAR,
        APP_CALENDAR_DATE_MONTH + 1,
        APP_CALENDAR_DATE_PLUS_VACATION.split("-")[1].toInt()
    )
    CoroutineScope(Dispatchers.IO).async {
        dao.insertVacation(vacation)
        Log.d("kek", vacation.toString())
    }
}

fun initVacationList() {
    LIST_VACATION_OF_MONTH = mutableListOf()
    CoroutineScope(Dispatchers.IO).async {
        for (i in dao.getVacationWithListOfStartM(APP_CALENDAR_DATE_MONTH + 1).indices){
            for(j in dao.getVacationWithListOfStartM(APP_CALENDAR_DATE_MONTH + 1)[i].vacationDate.indices){
                LIST_VACATION_OF_MONTH.add(dao.getVacationWithListOfStartM(APP_CALENDAR_DATE_MONTH + 1)[i].vacationDate[j].Date)
            }
        }
        for (i in dao.getVacationWithListOfStopM(APP_CALENDAR_DATE_MONTH + 1).indices){
            for(j in dao.getVacationWithListOfStopM(APP_CALENDAR_DATE_MONTH + 1)[i].vacationDate.indices){
                LIST_VACATION_OF_MONTH.add(dao.getVacationWithListOfStopM(APP_CALENDAR_DATE_MONTH + 1)[i].vacationDate[j].Date)
            }
        }


        LIST_VACATION_OF_NEXT_MONTH = mutableListOf()
        for (i in dao.getVacationWithListOfStartM(APP_CALENDAR_DATE_MONTH + 2).indices){
            for(j in dao.getVacationWithListOfStartM(APP_CALENDAR_DATE_MONTH + 2)[i].vacationDate.indices){
                LIST_VACATION_OF_NEXT_MONTH.add(dao.getVacationWithListOfStartM(APP_CALENDAR_DATE_MONTH + 2)[i].vacationDate[j].Date)
            }
        }
        Log.d("List1", LIST_VACATION_OF_NEXT_MONTH.toString())
        LIST_OF_VACATION_DATE_START = mutableListOf()
        for (i in dao.getVacationList(APP_CALENDAR_DATE_MONTH + 1).indices){
            LIST_OF_VACATION_DATE_START.add(dao.getVacationList(APP_CALENDAR_DATE_MONTH + 1)[i].date_start)
        }
    }
}

fun checkVacationForUpdate(start: Int, schet: Int, List: List<String>): Boolean{
    var calendarCount = Calendar.getInstance()
    calendarCount.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH, APP_CALENDAR_DATE_DAY)
    var status = true
    val formate = SimpleDateFormat("yyyy-MM-dd")
    for (i in start until schet) {
        calendarCount.add(Calendar.DAY_OF_MONTH, i)
        val date = formate.format(calendarCount.time)
        if (List.contains(date)){
            status = false
            break
        }
        calendarCount.add(Calendar.DAY_OF_MONTH, -i)
    }
    return status
}