package com.example.rzob21.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SickLeave(
    @PrimaryKey(autoGenerate = false)
    var date_start: String,
    var date_stop: String,
    var number_of_days: Int,
    var year: Int,
    var month_start: Int,
    var month_stop: Int
)
