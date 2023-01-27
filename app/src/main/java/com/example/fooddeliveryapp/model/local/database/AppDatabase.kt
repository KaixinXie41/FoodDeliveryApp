package com.example.fooddeliveryapp.model.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fooddeliveryapp.model.local.entities.Item
import com.example.fooddeliveryapp.model.local.dao.ItemDao
import com.example.fooddeliveryapp.model.local.entities.Order
import com.example.fooddeliveryapp.model.local.dao.OrderDao
import com.example.fooddeliveryapp.model.local.entities.Address
import com.example.fooddeliveryapp.model.local.dao.AddressDao
import com.example.fooddeliveryapp.model.local.entities.Cart
import com.example.fooddeliveryapp.model.local.dao.CartDao
import com.example.fooddeliveryapp.model.local.entities.Favorite
import com.example.fooddeliveryapp.model.local.dao.FavoriteDao
import com.example.fooddeliveryapp.model.local.entities.Restaurant
import com.example.fooddeliveryapp.model.local.dao.RestaurantDao
import com.example.fooddeliveryapp.model.local.entities.User
import com.example.fooddeliveryapp.model.local.dao.UserDao

@Database(entities = [Address::class, Restaurant::class, Order::class, Cart::class, User::class, Favorite::class, Item::class], version = 14, exportSchema = false)
abstract class AppDatabase: RoomDatabase(){

    abstract fun getAddressDao(): AddressDao
    abstract fun getRestaurantDao(): RestaurantDao
    abstract fun getOrderDao(): OrderDao
    abstract fun getCartDao(): CartDao
    abstract fun getUserDao(): UserDao
    abstract fun getFavoriteDao(): FavoriteDao
    abstract fun getItemDao(): ItemDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "AppDB")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE as AppDatabase
        }
    }
}