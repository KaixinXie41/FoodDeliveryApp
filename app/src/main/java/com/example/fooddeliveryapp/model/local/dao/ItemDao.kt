package com.example.fooddeliveryapp.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fooddeliveryapp.model.local.entities.Item

@Dao
interface ItemDao {
    @Insert
    suspend fun insert(item: Item):Long

    @Delete
    suspend fun delete(item: Item):Int

    @Update
    suspend fun update(item: Item):Int

    @Query("SELECT * FROM OrderItem")
    fun getAllItem():LiveData<List<Item>>

    @Query("SELECT * From OrderItem WHERE orderId=:orderId")
    fun getItemByOrderId(orderId:Long): LiveData<List<Item>>

    @Query("SELECT * From OrderItem WHERE itemId=:itemId")
    fun getItemByItemId(itemId:Long): Item
}