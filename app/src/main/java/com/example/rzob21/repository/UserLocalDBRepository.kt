package com.example.rzob21.repository

import androidx.lifecycle.LiveData
import com.example.rzob21.data.UserLocalDBDao
import com.example.rzob21.models.UserLocalDB
import com.example.rzob21.utilits.UID
import kotlinx.coroutines.flow.Flow

class UserLocalDBRepository(private val userDao: UserLocalDBDao) {
    suspend fun addUser(userLocalDB: UserLocalDB){
        userDao.addUser(userLocalDB)
    }

    suspend fun updateUser(userLocalDB: UserLocalDB){
        userDao.updateUser(userLocalDB)
    }

    fun findByUID(UID: String): Flow<List<UserLocalDB>>{
        return userDao.findByUID(UID)
    }
}