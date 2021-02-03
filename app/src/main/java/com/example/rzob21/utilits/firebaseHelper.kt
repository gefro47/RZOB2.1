package com.example.rzob21.utilits

import com.example.rzob21.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

lateinit var AUTH: FirebaseAuth
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var USER: User
lateinit var UID: String



const val NODE_USERS = "users"
const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_POSITION_AT_WORK = "position"//должность
const val CHILD_PAYMENT = "payment"//расчет
const val CHILD_AVERAGE_SALARY = "average salary" //средняя зп
const val CHILD_SALARY = "salary"//зп
const val CHILD_WORK_DAY = "work days"// кол-во раб дней
const val CHILD_WORKED_DAY = "worked days"//отработанные дни
const val CHILD_RECAST = "recast"//переработка
const val CHILD_SICK_DAYS = "sick days"//больничные дни
const val CHILD_HOLIDAY = "holiday"//отпуск
const val CHILD_WEEKEND = "weekend"//выходной
const val CHILD_K15 = "k15"//коэф 1,5
const val CHILD_K2 = "k2"//коэф 2
const val CHILD_START_SICK_DAYS ="start sick days"//начало больничного
const val CHILD_STOP_SICK_DAYS = "stop sick days"//конец больничного
const val CHILD_START_HOLIDAY ="start holiday"//начало отпуска
const val CHILD_STOP_HOLIDAY = "stop holiday"//конец отпуска
const val CHILD_NUMBER_OF_SICK_DAYS = "number of sick days"//кол-во больничных дней
const val CHILD_NUMBER_OF_HOLIDAY = "number of holiday"//кол-во дней отпуска



fun initFirebase(){
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    USER = User()
    UID = AUTH.currentUser?.uid.toString()
}