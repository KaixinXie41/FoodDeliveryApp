package com.example.fooddeliveryapp.model.local.data.category

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("idCategory")
    val idCategory: String,

    @SerializedName("strCategory")
    val strCategory: String,

    @SerializedName("strCategoryDescription")
    val strCategoryDescription: String,

    @SerializedName("strCategoryThumb")
    val strCategoryThumb: String
)