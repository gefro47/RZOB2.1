package com.example.rzob21.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.rzob21.models.SickLeave
import com.example.rzob21.models.SickLeaveDate

data class SickLeaveWithList(
    @Embedded val sick_leave: SickLeave,
    @Relation(
        parentColumn = "date_start",
        entityColumn = "date_start"
    )
    val sickLeaveDate: List<SickLeaveDate>
)
