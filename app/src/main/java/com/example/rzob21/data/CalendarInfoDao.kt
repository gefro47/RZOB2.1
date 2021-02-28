package com.example.rzob21.data

import androidx.room.*
import com.example.rzob21.models.Vacation
import com.example.rzob21.models.VacationDate
import com.example.rzob21.models.relations.VacationWithList

@Dao
interface CalendarInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacation(vacation: Vacation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacationDate(vacationDate: VacationDate)

    @Transaction
    @Query("SELECT * FROM vacation WHERE month = :month")
    suspend fun getVacationWithList(month: Int): List<VacationWithList>

    @Transaction
    @Query("SELECT * FROM vacation WHERE month = :month")
    suspend fun getVacationList(month: Int): List<Vacation>
}