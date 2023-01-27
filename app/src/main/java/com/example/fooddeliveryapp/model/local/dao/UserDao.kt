package com.example.fooddeliveryapp.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fooddeliveryapp.model.local.entities.User

@Dao
interface UserDao {
    @Insert
    fun addUser(user: User):Long

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM User")
    fun getAllUser():LiveData<List<User>>

    @Query("SELECT * FROM User WHERE userId=:userId")
    fun getUserByUserId(userId:Long): User

    @Query("DELETE FROM User")
    fun delete()
}