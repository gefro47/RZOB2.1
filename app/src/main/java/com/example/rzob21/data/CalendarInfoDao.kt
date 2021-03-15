package com.example.rzob21.data

import androidx.room.*
import com.example.rzob21.models.*
import com.example.rzob21.models.relations.SickLeaveWithList
import com.example.rzob21.models.relations.VacationWithList

@Dao
interface CalendarInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacation(vacation: Vacation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacationDate(vacationDate: VacationDate)

    @Transaction
    @Query("SELECT * FROM vacation WHERE month_start = :month")
    suspend fun getVacationWithListOfStartM(month: Int): List<VacationWithList>

    @Transaction
    @Query("SELECT * FROM vacation WHERE month_stop = :month")
    suspend fun getVacationWithListOfStopM(month: Int): List<VacationWithList>

    @Transaction
    @Query("SELECT * FROM vacation WHERE month_start = :month")
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
    suspend fun insertSickLeave(sickLeave: SickLeave)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSickLeaveDate(sickLeaveDate: SickLeaveDate)

    @Transaction
    @Query("SELECT * FROM sickleave WHERE month_start = :month")
    suspend fun getSickLeaveWithListOfStartM(month: Int): List<SickLeaveWithList>

    @Transaction
    @Query("SELECT * FROM sickleave WHERE month_stop = :month")
    suspend fun getSickLeaveWithListOfStopM(month: Int): List<SickLeaveWithList>

    @Transaction
    @Query("SELECT * FROM sickleave WHERE month_start = :month")
    suspend fun getSickLeaveList(month: Int): List<SickLeave>

    @Transaction
    @Query("SELECT * FROM sickleavedate WHERE Date = :Date")
    suspend fun getSickLeaveDate(Date: String): SickLeaveDate

    @Transaction
    @Query("SELECT * FROM sickleavedate WHERE year = :year")
    suspend fun getSickLeaveDateOfTwoYear(year: Int): List<SickLeaveDate>

    @Transaction
    @Query("SELECT * FROM sickleave WHERE date_start = :date_start")
    suspend fun getSickLeave(date_start: String): SickLeave

    @Query("DELETE FROM sickleave WHERE date_start = :date_start")
    suspend fun deleteSickLeave(date_start: String)

    @Query("DELETE FROM sickleavedate WHERE date_start = :date_start")
    suspend fun deleteSickLeaveDate(date_start: String)




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