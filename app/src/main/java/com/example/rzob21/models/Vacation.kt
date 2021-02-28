package com.example.rzob21.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Entity
data class Vacation(
    @PrimaryKey(autoGenerate = false)
    var date_start: String,
    var date_stop: String,
    var number_of_days: Int,
    var year: Int,
    var month: Int
)