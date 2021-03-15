package com.example.rzob21.utilits

import android.util.Log

suspend fun initRecastList(){
    LIST_RECAST_OF_MONTH = mutableListOf()
    for (i in dao.getRecastList(APP_CALENDAR_DATE_MONTH + 1).indices){
        LIST_RECAST_OF_MONTH.add(dao.getRecastList(APP_CALENDAR_DATE_MONTH + 1)[i].date)
    }

    LIST_RECAST_OF_NEXT_MONTH = mutableListOf()
    for (i in dao.getRecastList(APP_CALENDAR_DATE_MONTH + 2).indices){
        LIST_RECAST_OF_NEXT_MONTH.add(dao.getRecastList(APP_CALENDAR_DATE_MONTH + 2)[i].date)
    }
    Log.d("LIST_OF_RECAST_DATE", LIST_RECAST_OF_MONTH.toString())
}

