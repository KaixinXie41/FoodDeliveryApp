package com.example.fooddeliveryapp.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fooddeliveryapp.model.local.entities.Order

@Dao
interface OrderDao {

    @Insert
    suspend fun insert(order: Order):Long

    @Delete
    suspend fun delete(order: Order):Int

    @Update
    suspend fun update(order: Order):Int

    @Query("SELECT * FROM `Order`")
    fun getAllOrder(): LiveData<List<Order>>

    @Query("SELECT * From `Order` WHERE orderId=:orderId")
    fun getOrderByOrderId(orderId:Long): Order
}