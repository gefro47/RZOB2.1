package com.example.rzob21.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class UserLocalDB(
    @PrimaryKey
    val id: String = "",
    val phone: String = "",
    var position: String = "",
    var salary: Double = 0.0,
    var average_salary: Double = 0.0
):Parcelable
