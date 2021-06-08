package com.example.rzob21.models

import java.sql.Date

data class SickLeave(
    var id: Int? = null,
    var date_start: Date,
    var date_stop: Date
)
