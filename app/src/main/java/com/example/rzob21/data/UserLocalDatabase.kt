package com.example.rzob21.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rzob21.models.UserLocalDB

@Database(entities = [UserLocalDB::class], version = 1, exportSchema = false)
abstract class UserLocalDatabase: RoomDatabase() {

    abstract fun userDao(): UserLocalDBDao
    companion object{
        @Volatile
        private var INSTANCE: UserLocalDatabase? = null

        fun getDatabase (context: Context): UserLocalDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserLocalDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}