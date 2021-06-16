package com.example.rzob21.utilits

import com.example.rzob21.models.PeriodModel
import com.gefro.springbootkotlinRZOBbackend.models.Income
import com.gefro.springbootkotlinRZOBbackend.models.Recast
import java.sql.Date

var APP_CALENDAR_DATE = ""
var APP_CALENDAR_DATE_PLUS_VACATION = ""
var APP_VACATION_DAY = 0
var APP_VACATION_NUMBER_OF_DAYS = 0
var APP_CALENDAR_DATE_DAY = 0
var APP_CALENDAR_DATE_MONTH = 0
var APP_CALENDAR_DATE_MONTH_V = 0
var APP_CALENDAR_DATE_YEAR = 0
var APP_DATE_TODAY = ""
var APP_DATE: Date ?= null
var SICK_LEAVE_STOP: Date ?= null
var VACATION_STOP: Date ?= null
var APP_INCOME_DATE: Date ?= null
var APP_INCOME_OF_HISTORY_DATE: Date ?= null
var APP_HISTORY_OF_VACATION_DATE: Date ?= null
var APP_HISTORY_OF_SICK_LEAVE_DATE: Date ?= null
var INCOME: Income ?= null
var APP_DATE_PICK_MONTH_L = ""
var INCOME_HISTORY_DATE_PICK_MONTH_AND_YEAR = ""
var APP_CALENDAR_DATE_MONTH_CHECK = 0
var APP_CALENDAR_DATE_YEAR_CHECK = 0
var LIST_RECAST_OF_MONTH = mutableListOf<Recast>()
var LIST_RECAST_OF_YEAR = mutableListOf<Date>()
var LIST_OF_RECAST_DATE = mutableListOf<Date>()
var LIST_OF_SICK_LEAVE_DATE = mutableListOf<Date>()
var LIST_OF_VACATION_DATE_START = mutableListOf<String>()
var LIST_OF_VACATION_DATE = mutableListOf<Date>()
var LIST_VACATION_OF_NEXT_MONTH = mutableListOf<String>()
var LIST_INCOME_OF_USER = mutableListOf<Income>()
var LIST_OF_HOLIDAYS = mutableListOf<String>()
var LIST_OF_PREHOLIDAYS = mutableListOf<String>()
var LIST_OF_NOTWORK2020 = mutableListOf<String>()
var READ_RECAST = false

lateinit var APP_INCOME_DATE_NEXT: Date


var LIST_SICK_LEAVE_OF_TWO_YEAR = mutableListOf<String>()
var APP_SICK_LEAVE_NUMBER_OF_DAYS = 0
var APP_CALENDAR_DATE_PLUS_SICK_LEAVE = ""
var LIST_SICK_LEAVE_OF_MONTH = mutableListOf<PeriodModel>()
var LIST_SICK_LEAVE_OF_YEAR = mutableListOf<PeriodModel>()
var LIST_VACATION_OF_MONTH = mutableListOf<PeriodModel>()
var LIST_VACATION_OF_YEAR = mutableListOf<PeriodModel>()
var LIST_SICK_LEAVE_OF_NEXT_MONTH = mutableListOf<String>()
var LIST_OF_SICK_LEAVE_DATE_START = mutableListOf<String>()
var APP_SICK_LEAVE_DAY = 0
var APP_CALENDAR_DATE_MONTH_S = 0