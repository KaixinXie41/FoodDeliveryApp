package com.example.fooddeliveryapp.dao

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.fooddeliveryapp.model.local.dao.ItemDao
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.entities.Item
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ItemDaoTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var itemDao: ItemDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        itemDao = appDatabase.getItemDao()
    }

    @Test
    fun testSaveItem() {
        runBlocking {
            val item = Item(
                0,
                12345,
                1,
                20,
                "chicken",
                1,
                "chicken"
            )

            val itemId: Long = itemDao.insert(item)
            val saveItem = itemDao.getItemByItemId(itemId)

            assertNotNull(saveItem)
            assertEquals(itemId, saveItem.itemId)
        }
    }

    @Test
    fun testDeleteItem() {
        runBlocking {
            val item = Item(
                0,
                12345,
                1,
                20,
                "chicken",
                1,
                "chicken"
            )

            val itemId: Long = itemDao.insert(item)
            val saveItem = itemDao.getItemByItemId(itemId)

            assertNotNull(saveItem)
            assertEquals(itemId, saveItem.itemId)

            itemDao.delete(saveItem)

            assertNull(itemDao.getItemByItemId(itemId))
        }
    }

    @Test
    fun testUpdateItem() {
        runBlocking {
            val item = Item(
                0,
                12345,
                1,
                20,
                "chicken",
                1,
                "chicken"
            )

            val itemId: Long = itemDao.insert(item)
            val saveItem = itemDao.getItemByItemId(itemId)
            val newMealName = "Chicken Wings"

            saveItem.meal_name = newMealName
            itemDao.update(saveItem)

            val updateNote = itemDao.getItemByItemId(itemId)

            assertEquals(saveItem.meal_name, updateNote.meal_name)
        }
    }
}