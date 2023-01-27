package com.example.fooddeliveryapp.dao

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.fooddeliveryapp.model.local.dao.CartDao
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.entities.Cart
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class  CartDaoTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var cartDao: CartDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        cartDao = appDatabase.getCartDao()
    }

    @Test
    fun testSaveCart() {
        val  cart = Cart(
            0,
            "Barbeque Chicken",
            "1",
            20.00,
            2,
            40.00,
            "www.google.com",
            "Chicken"
        )

        val cartId = cartDao.addCart(cart)
        val saveItem = cartDao.getCartMealByCartId(cartId)

        assertNotNull(saveItem)
        assertEquals(cartId, saveItem.cartId)
    }


    @Test
    fun testDeleteCart() {
        val  cart = Cart(
            0,
            "Barbeque Chicken",
            "1",
            20.00,
            2,
            40.00,
            "www.google.com",
            "Chicken"
        )

        val cartId = cartDao.addCart(cart)
        val saveItem = cartDao.getCartMealByCartId(cartId)

        assertNotNull(saveItem)
        assertEquals(cartId, saveItem.cartId)

        cartDao.deleteCart(saveItem)

        assertNull(cartDao.getCartMealByCartId(cartId))
    }

    @Test
    fun testUpdateCart() {
            val  cart = Cart(
                0,
                "Barbeque Chicken",
                "1",
                20.00,
                2,
                40.00,
                "www.google.com",
                "Chicken"
            )

        val cartId = cartDao.addCart(cart)
        val saveItem = cartDao.getCartMealByCartId(cartId)
            val newImageUrl = "www.facebook.com"

            saveItem.mealImageUrl = newImageUrl
            cartDao.updateCart(saveItem)

            val updateNote = cartDao.getCartMealByCartId(cartId)

            assertEquals(saveItem.mealImageUrl, updateNote.mealImageUrl)
        }
}