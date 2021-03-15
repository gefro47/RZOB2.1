package com.example.rzob21.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SickLeaveDate(
    @PrimaryKey(autoGenerate = false)
    val Date: String,
    val date_start: String,
    val year: Int
)
