package com.example.rzob21.API

import com.example.rzob21.model.CalendarHolidays
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequests {

    @GET ("d10xa/holidays-calendar/master/json/calendar.json")
    fun getholidays(): Call<CalendarHolidays>
}