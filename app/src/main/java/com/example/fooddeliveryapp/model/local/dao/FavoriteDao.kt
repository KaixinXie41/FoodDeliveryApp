package com.example.fooddeliveryapp.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.fooddeliveryapp.model.local.entities.Favorite

@Dao
interface FavoriteDao {
    @Insert(onConflict = REPLACE)
    fun addFavorite(favorite: Favorite):Long

    @Update
    fun updateFavorite(favorite: Favorite)

    @Delete
    fun deleteFavorite(favorite: Favorite)

    @Query("SELECT * FROM Favorite")
    fun getAllFavorite(): LiveData<List<Favorite>>

    @Query("SELECT * FROM Favorite WHERE userId=:userId")
    fun getFavoriteByUserId(userId: Int): LiveData<List<Favorite>>

    @Query("DELETE FROM Favorite")
    fun delete()

    @Query("SELECT * FROM Favorite WHERE mealId=:mealId")
    fun getFavoriteByMealId(mealId: Long): Favorite
}