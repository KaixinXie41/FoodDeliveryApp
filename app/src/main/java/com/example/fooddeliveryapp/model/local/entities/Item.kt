package com.example.fooddeliveryapp.model.local.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "OrderItem")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val itemId:Long = 0,

    @ColumnInfo(name = "mealId")
    val meal_id: Int,

    @ColumnInfo(name = "quantity")
    val quantity: Int,

    @ColumnInfo(name = "unitPrice")
    val unit_price: Int,

    @ColumnInfo(name = "mealName")
    var meal_name:String,

    @ColumnInfo(name = "orderId",)
    val orderId:Long,

    @ColumnInfo(name = "mealCategory")
    val meal_category:String
):Parcelable