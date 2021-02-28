package com.example.rzob21.model

import androidx.room.Entity


@Entity(tableName = "holidays")
data class HolidaysDate(
    val year: Int,
    val month: Int,
    val dayOfMonth: Int
)