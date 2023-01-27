package com.example.fooddeliveryapp.model.local.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Parcelize
@Entity
data class Order(
    @PrimaryKey(autoGenerate = true) val orderId: Long = 0,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "title") var addressTitle: String,
    @ColumnInfo(name = "bill_amount") val bill_amount: Double,
    @ColumnInfo(name = "order_date") val order_date: String,
    @ColumnInfo(name = "order_status") val order_status: String,
    @ColumnInfo(name = "payment_method") val payment_method: String,
    @ColumnInfo(name = "delivery_type") val delivery_type:String
):Parcelable
