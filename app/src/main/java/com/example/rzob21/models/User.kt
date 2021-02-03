package com.example.rzob21.models

data class User(
    val id: String = "",
    val phone: String = "",
    var position: String = "",
    var salary: Double = 0.0,
    var average_salary: Double = 0.0
)