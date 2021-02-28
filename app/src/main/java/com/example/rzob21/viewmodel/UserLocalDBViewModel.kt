package com.example.rzob21.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.rzob21.data.UserLocalDatabase
import com.example.rzob21.models.UserLocalDB
import com.example.rzob21.repository.UserLocalDBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserLocalDBViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserLocalDBRepository

    fun addUser(userLocalDB: UserLocalDB){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(userLocalDB)
        }
    }

    fun updateUser(userLocalDB: UserLocalDB){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(userLocalDB)
        }
    }

    fun findByUID(UID: String): Flow<List<UserLocalDB>> {
        return repository.findByUID(UID)
    }

    init {
        val userDao = UserLocalDatabase.getDatabase(application).userDao()
        repository = UserLocalDBRepository(userDao)
    }
}