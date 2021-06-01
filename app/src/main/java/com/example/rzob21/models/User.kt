package com.example.rzob21.models


data class User(
    val id: String = "",
    val phone: String? = null,
    var position: String? = null,
    var salary: Double? = null,
    var average_salary: Double? = null
)