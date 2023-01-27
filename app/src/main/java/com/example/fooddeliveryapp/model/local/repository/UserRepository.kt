package com.example.fooddeliveryapp.model.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.dao.UserDao
import com.example.fooddeliveryapp.model.local.entities.User

class UserRepository(application: Application) {

    private val userDao: UserDao?
    val allUser: LiveData<List<User>>

    init {
        val database = application.let {
            AppDatabase.getInstance(it)
        }
        userDao = database.getUserDao()
        allUser = userDao.getAllUser()
    }

    fun insert(user: User) {
        userDao?.addUser(user)
    }

    fun delete(user: User) {
        userDao?.deleteUser(user)
    }

    fun update(user: User){
        userDao?.updateUser(user)
    }

    fun getUserByUserId(userId:Long){
        userDao?.getUserByUserId(userId)
    }
}