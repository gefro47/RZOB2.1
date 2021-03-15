package com.example.rzob21.utilits

import android.util.Log
import com.example.rzob21.models.SickLeave
import com.example.rzob21.models.SickLeaveDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

fun sickLeaveCheckAndInsert(): Boolean{
    val status: Boolean
    val formate = SimpleDateFormat("yyyy-MM-dd")

    val calendarStart = Calendar.getInstance()
    calendarStart.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH, APP_CALENDAR_DATE_DAY)
    APP_DATE = formate.format(calendarStart.time)

    val calendarEnd = Calendar.getInstance()
    calendarEnd.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH, APP_CALENDAR_DATE_DAY)
    calendarEnd.add(Calendar.DAY_OF_MONTH, APP_SICK_LEAVE_DAY - 1)
    APP_CALENDAR_DATE_PLUS_SICK_LEAVE = formate.format(calendarEnd.time)

    var calendarCount = Calendar.getInstance()
    calendarCount.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH, APP_CALENDAR_DATE_DAY)

    var calendarCountFDOM = Calendar.getInstance()
    calendarCountFDOM.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH +1, 1)

    status = funOnThisMonthSickLeave(APP_SICK_LEAVE_DAY, calendarCount, calendarStart)
    return status
}

fun funOnThisMonthSickLeave(schet: Int, calendarCount: Calendar, calendarStart: Calendar): Boolean{
    return if (checkSickLeave(schet, calendarCount, LIST_SICK_LEAVE_OF_MONTH)&&
        checkSickLeave(schet, calendarCount, LIST_SICK_LEAVE_OF_NEXT_MONTH)&&
        checkSickLeave(schet, calendarCount, LIST_VACATION_OF_MONTH)&&
        checkSickLeave(schet, calendarCount, LIST_VACATION_OF_NEXT_MONTH)&&
        checkSickLeave(schet, calendarCount, LIST_RECAST_OF_MONTH)&&
        checkSickLeave(schet, calendarCount, LIST_RECAST_OF_NEXT_MONTH)) {
        insertSickLeaveDate(schet, calendarCount, calendarStart)
        insertSickLeave()
        true
    }else{
        false
    }
}

fun checkSickLeave(schet: Int, calendar: Calendar, List: List<String>): Boolean{
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

fun insertSickLeaveDate(schet: Int, calendarCount: Calendar, calendarStart: Calendar){
    val formate = SimpleDateFormat("yyyy-MM-dd")
    for (i in 0 until schet) {
        calendarCount.add(Calendar.DAY_OF_MONTH, i)
        val countDate = formate.format(calendarCount.time)
        val startDate = formate.format(calendarStart.time)
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertSickLeaveDate(SickLeaveDate(countDate, startDate, APP_CALENDAR_DATE_YEAR))
        }
        calendarCount.add(Calendar.DAY_OF_MONTH, -i)
    }
}

fun insertSickLeave(){
    val sickLeave = SickLeave(
        APP_DATE,
        APP_CALENDAR_DATE_PLUS_SICK_LEAVE,
        APP_SICK_LEAVE_DAY,
        APP_CALENDAR_DATE_YEAR,
        APP_CALENDAR_DATE_MONTH + 1,
        APP_CALENDAR_DATE_PLUS_SICK_LEAVE.split("-")[1].toInt()
    )
    CoroutineScope(Dispatchers.IO).async {
        dao.insertSickLeave(sickLeave)
    }
}

fun initSickLeaveList() {
    LIST_SICK_LEAVE_OF_MONTH = mutableListOf()
    CoroutineScope(Dispatchers.IO).async {
        for (i in dao.getSickLeaveWithListOfStartM(APP_CALENDAR_DATE_MONTH + 1).indices){
            for(j in dao.getSickLeaveWithListOfStartM(APP_CALENDAR_DATE_MONTH + 1)[i].sickLeaveDate.indices){
                LIST_SICK_LEAVE_OF_MONTH.add(dao.getSickLeaveWithListOfStartM(APP_CALENDAR_DATE_MONTH + 1)[i].sickLeaveDate[j].Date)
            }
        }
        for (i in dao.getSickLeaveWithListOfStopM(APP_CALENDAR_DATE_MONTH + 1).indices){
            for(j in dao.getSickLeaveWithListOfStopM(APP_CALENDAR_DATE_MONTH + 1)[i].sickLeaveDate.indices){
                LIST_SICK_LEAVE_OF_MONTH.add(dao.getSickLeaveWithListOfStopM(APP_CALENDAR_DATE_MONTH + 1)[i].sickLeaveDate[j].Date)
            }
        }

        LIST_SICK_LEAVE_OF_NEXT_MONTH = mutableListOf()
        for (i in dao.getSickLeaveWithListOfStartM(APP_CALENDAR_DATE_MONTH + 2).indices){
            for(j in dao.getSickLeaveWithListOfStartM(APP_CALENDAR_DATE_MONTH + 2)[i].sickLeaveDate.indices){
                LIST_SICK_LEAVE_OF_NEXT_MONTH.add(dao.getSickLeaveWithListOfStartM(APP_CALENDAR_DATE_MONTH + 2)[i].sickLeaveDate[j].Date)
            }
        }

        LIST_SICK_LEAVE_OF_TWO_YEAR = mutableListOf()
        for (j in 0..1){
            for (i in dao.getSickLeaveDateOfTwoYear(APP_CALENDAR_DATE_YEAR + j).indices){
                LIST_SICK_LEAVE_OF_TWO_YEAR.add(dao.getSickLeaveDateOfTwoYear(APP_CALENDAR_DATE_YEAR + j)[i].Date)
            }
        }
        Log.d("LIST_SICK_LEAVE_OF_TWO", LIST_SICK_LEAVE_OF_TWO_YEAR.toString())

        LIST_OF_SICK_LEAVE_DATE_START = mutableListOf()
        for (i in dao.getVacationList(APP_CALENDAR_DATE_MONTH + 1).indices){
            LIST_OF_SICK_LEAVE_DATE_START.add(dao.getSickLeaveList(APP_CALENDAR_DATE_MONTH + 1)[i].date_start)
        }
    }
}

fun checkSickLeaveForUpdate(start: Int, schet: Int, List: List<String>): Boolean{
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