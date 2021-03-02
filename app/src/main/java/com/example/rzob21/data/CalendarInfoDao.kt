package com.example.rzob21.data

import androidx.room.*
import com.example.rzob21.models.Recast
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

    @Transaction
    @Query("SELECT * FROM VacationDate WHERE Date = :Date")
    suspend fun getVacationDate(Date: String): VacationDate

    @Transaction
    @Query("SELECT * FROM vacation WHERE date_start = :date_start")
    suspend fun getVacation(date_start: String): Vacation

    @Query("DELETE FROM vacation WHERE date_start = :date_start")
    suspend fun deleteVacation(date_start: String)

    @Query("DELETE FROM VacationDate WHERE date_start = :date_start")
    suspend fun deleteVacationDate(date_start: String)




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecast(recast: Recast)

    @Transaction
    @Query("SELECT * FROM recast WHERE month = :month")
    suspend fun getRecastList(month: Int): List<Recast>

    @Transaction
    @Query("SELECT * FROM recast WHERE date = :date")
    suspend fun getRecastHours(date: String): Recast

    @Query("DELETE FROM recast WHERE date = :date")
    suspend fun deleteByRecastDay(date: String)
}