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
const val CHILD_AVERAGE_SALARY = "average_salary" //средняя зп
const val CHILD_SALARY = "salary"//зп



fun initFirebase(){
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    USER = User()
    UID = AUTH.currentUser?.uid.toString()
}
