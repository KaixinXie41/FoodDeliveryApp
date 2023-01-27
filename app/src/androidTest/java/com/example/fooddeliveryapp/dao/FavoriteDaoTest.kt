package com.example.fooddeliveryapp.dao

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.fooddeliveryapp.model.local.dao.FavoriteDao
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.entities.Favorite
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FavoriteDaoTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var favoriteDao: FavoriteDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        favoriteDao = appDatabase.getFavoriteDao()
    }

    @Test
    fun testSaveFavorite() {
        val favorite = Favorite(
            3,
            "Barbeque Chicken",
            1,
            "www.google.com"

        )

        val mealId = favoriteDao.addFavorite(favorite)
        val saveItem = favoriteDao.getFavoriteByMealId(mealId)

        assertNotNull(saveItem)
        assertEquals(mealId, saveItem.mealId)
    }


    @Test
    fun testDeleteFavorite() {
        val favorite = Favorite(
            3,
            "Barbeque Chicken",
            1,
            "www.google.com"

        )

        val mealId = favoriteDao.addFavorite(favorite)
        val saveItem = favoriteDao.getFavoriteByMealId(mealId)

        assertNotNull(saveItem)
        assertEquals(mealId, saveItem.mealId)

        favoriteDao.deleteFavorite(saveItem)

        assertNull(favoriteDao.getFavoriteByMealId(mealId))
    }

    @Test
    fun testUpdateFavorite() {
        runBlocking {
            val favorite = Favorite(
                3,
                "Barbeque Chicken",
                1,
                "www.google.com"

            )

            val addressId = favoriteDao.addFavorite(favorite)
            val saveItem = favoriteDao.getFavoriteByMealId(addressId)
            val newMealPicture = "www.facebook.com"

            saveItem.mealPicture = newMealPicture
            favoriteDao.updateFavorite(saveItem)

            val updateNote = favoriteDao.getFavoriteByMealId(addressId)

            assertEquals(saveItem.mealPicture, updateNote.mealPicture)
        }
    }
}