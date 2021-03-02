package com.example.rzob21.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Recast(
    @PrimaryKey(autoGenerate = false)
    var date: String,
    var recast_hours: Double,
    var weekend: Boolean,
    var year: Int,
    var month: Int
)
