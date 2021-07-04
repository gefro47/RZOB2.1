package com.gefro.springbootkotlinRZOBbackend.models

data class Income(
    var id: Long? = null,
    var year: Int,
    var month: Int,
    var date_of_avans: String,
    var avans: Double? = null,
    var date_of_income_without_avans: String,
    var income_without_avans: Double? = null,
    var income_of_money: Double,
    var math_calc: Boolean
)