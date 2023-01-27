package com.example.fooddeliveryapp.model.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.dao.RestaurantDao
import com.example.fooddeliveryapp.model.local.entities.Restaurant

class RestaurantRepository(application: Application) {
    private val restaurantDao: RestaurantDao?
    val allRestaurant: LiveData<List<Restaurant>>

    init {
        val database = application.let {
            AppDatabase.getInstance(it)
        }
        restaurantDao = database.getRestaurantDao()
        allRestaurant = restaurantDao.getAllRestaurant()
    }

    fun insert(restaurant: Restaurant) {
        restaurantDao?.insert(restaurant)
    }

    fun delete(restaurant: Restaurant) {
        restaurantDao?.delete(restaurant)
    }

    fun update(restaurant: Restaurant){
        restaurantDao?.update(restaurant)
    }

    fun getRestaurantById(resId:Long){
        restaurantDao?.getRestaurantById(resId)
    }
}