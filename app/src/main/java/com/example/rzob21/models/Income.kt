package com.gefro.springbootkotlinRZOBbackend.models

data class Income(
    var id: Long? = null,
    var year: Int,
    var month: Int,
    var income_of_money: Double,
    var math_calc: Boolean,
)