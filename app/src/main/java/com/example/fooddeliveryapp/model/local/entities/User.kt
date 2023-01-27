package com.example.fooddeliveryapp.model.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) var userId: Long = 0,
    @ColumnInfo(name = "name") var fullName: String?,
    @ColumnInfo(name = "mobile") var mobileNo: String?,
    @ColumnInfo(name = "email")val email: String,
    @ColumnInfo(name = "password")val password: String
)
