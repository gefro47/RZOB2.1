package com.example.rzob21.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.rzob21.models.User
import com.example.rzob21.models.UserLocalDB
import kotlinx.coroutines.flow.Flow

@Dao
interface UserLocalDBDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(userLocalDB: UserLocalDB)

    @Update
    suspend fun updateUser(userLocalDB: UserLocalDB)


    @Query("SELECT * FROM user_table WHERE id LIKE :id")
    fun findByUID(id: String): Flow<List<UserLocalDB>>

}