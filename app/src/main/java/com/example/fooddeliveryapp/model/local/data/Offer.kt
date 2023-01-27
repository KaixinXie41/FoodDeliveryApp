package com.example.fooddeliveryapp.model.local.data

import androidx.annotation.DrawableRes

data class Offer(
    val name:String,
    val details: String,
    val timeRange:String,
    @DrawableRes val image:Int
)