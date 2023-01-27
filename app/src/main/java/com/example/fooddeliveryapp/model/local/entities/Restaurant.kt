package com.example.fooddeliveryapp.model.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Restaurant(
    @PrimaryKey(autoGenerate = true)val resId: Long = 0,
    @ColumnInfo(name = "name") var res_name: String,
    @ColumnInfo(name = "address") val res_address: String,
    @ColumnInfo(name = "rating") val res_rating: String,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double
)
