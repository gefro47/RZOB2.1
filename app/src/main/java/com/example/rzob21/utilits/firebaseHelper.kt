package com.example.rzob21.utilits

import com.example.rzob21.models.Recast
import com.example.rzob21.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

lateinit var AUTH: FirebaseAuth
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var USER: User
lateinit var UID: String
lateinit var RECAST: Recast



const val NODE_USERS = "users"
const val NODE_RECAST = "recast"//переработка
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
const val CHILD_WEEKEND = "weekend"//выходной
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
    UID = AUTH.currentUser?.uid.toString()
}

fun DataSnapshot.getRecast(): Recast = this.getValue(Recast::class.java)?: Recast()