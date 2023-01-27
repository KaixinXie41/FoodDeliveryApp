package com.example.fooddeliveryapp.model.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Address(
    @PrimaryKey(autoGenerate = true)val addressId: Long = 0,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "title") var title: String
)
