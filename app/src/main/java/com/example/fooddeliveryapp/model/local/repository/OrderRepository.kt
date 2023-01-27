package com.example.fooddeliveryapp.model.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.fooddeliveryapp.model.local.entities.Order
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.dao.OrderDao

class OrderRepository(application: Application) {
    private val orderDao: OrderDao?
    val allOrder: LiveData<List<Order>>

    init {
        val database = application.let{
            AppDatabase.getInstance(it)}
        orderDao = database.getOrderDao()
        allOrder = orderDao.getAllOrder()
    }

    suspend fun insert(order: Order){
        orderDao?.insert(order)
    }

    suspend fun delete(order: Order){
        orderDao?.delete(order)
    }

    fun getOrderByOrderId(orderId:Long){
        orderDao?.getOrderByOrderId(orderId)
    }
}