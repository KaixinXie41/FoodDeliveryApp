package com.example.fooddeliveryapp.model.local.entities

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cart_table")
data class Cart(
    @PrimaryKey(autoGenerate = true) var cartId:Long,
    @ColumnInfo(name = "mealName") val mealName:String,
    @ColumnInfo(name = "mealId") val mealId:String,
    @ColumnInfo(name = "mealPrice") val mealPrice:Double,
    @ColumnInfo(name = "count") var count:Int,
    @ColumnInfo(name = "totalPrice") var totalPrice:Double,
    @ColumnInfo(name = "mealImageUrl") var mealImageUrl:String,
    @ColumnInfo(name = "mealCategory") val mealCategory:String
):Parcelable
