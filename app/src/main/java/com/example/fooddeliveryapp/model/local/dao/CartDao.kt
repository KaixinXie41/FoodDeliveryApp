package com.example.fooddeliveryapp.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fooddeliveryapp.model.local.entities.Cart

@Dao
interface CartDao {

    @Insert
    fun addCart(cart: Cart):Long

    @Update
    fun updateCart(cart: Cart)

    @Delete
    fun deleteCart(cart: Cart)

    @Query("SELECT * FROM cart_table")
    fun getAllCart():LiveData<List<Cart>>

    @Query("SELECT * FROM cart_table WHERE cartId=:cartId")
    fun getCartMealByCartId(cartId:Long): Cart

    @Query("SELECT * FROM cart_table WHERE mealId=:mealId")
    fun getCartMealByMealId(mealId:String): LiveData<List<Cart>>

    @Query("DELETE FROM cart_table")
    fun delete()


}