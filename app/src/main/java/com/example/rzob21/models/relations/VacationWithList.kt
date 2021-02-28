package com.example.rzob21.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.rzob21.models.Vacation
import com.example.rzob21.models.VacationDate

data class VacationWithList(
    @Embedded val vacation: Vacation,
    @Relation(
        parentColumn = "date_start",
        entityColumn = "date_start"
    )
    val vacationDate: List<VacationDate>
)
