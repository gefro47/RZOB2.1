package com.example.rzob21.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rzob21.models.Recast
import com.example.rzob21.models.Vacation
import com.example.rzob21.models.VacationDate


@Database(
    entities = [
        Vacation::class,
        VacationDate::class,
        Recast::class
         ],
    version = 1
)
abstract class CalendarInfoDatabase: RoomDatabase() {

    abstract fun vacationDao(): CalendarInfoDao

    companion object{
        @Volatile
        private var INSTANCE: CalendarInfoDatabase? = null

        fun getInstance(context: Context): CalendarInfoDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CalendarInfoDatabase::class.java,
                    "calendar_info_database"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}