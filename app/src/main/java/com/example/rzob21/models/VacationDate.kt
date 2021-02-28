package com.example.rzob21.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
data class VacationDate(
    @PrimaryKey(autoGenerate = false)
    val Date: String,
    val date_start: String
)
