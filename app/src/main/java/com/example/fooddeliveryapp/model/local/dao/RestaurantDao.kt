package com.example.fooddeliveryapp.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.fooddeliveryapp.model.local.entities.Restaurant

@Dao
interface RestaurantDao {

    @Insert(onConflict = REPLACE)
    fun insert(restaurant: Restaurant):Long

    @Delete
    fun delete(restaurant: Restaurant)

    @Update
    fun update(restaurant: Restaurant)

    @Query("SELECT * FROM Restaurant")
    fun getAllRestaurant(): LiveData<List<Restaurant>>

    @Query("SELECT * From Restaurant WHERE resId=:resId")
    fun getRestaurantById(resId:Long): Restaurant
}