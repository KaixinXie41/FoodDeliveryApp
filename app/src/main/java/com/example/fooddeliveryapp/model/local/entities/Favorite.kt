package com.example.fooddeliveryapp.model.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey val mealId:Long,
    @ColumnInfo(name = "mealName") val mealName :String,
    @ColumnInfo(name = "userId") val userId:Int,
    @ColumnInfo(name = "mealPicture") var mealPicture:String

)
