package com.example.fooddeliveryapp.model.local.data.category

import com.example.fooddeliveryapp.model.remote.ApiService
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllCategory() = apiService.getCategoryInfo()


    fun searchByMealName (message:String) = apiService.searchByMealName(message)

    fun searchByArea(message: String) = apiService.searchByArea(message)

    fun searchByIngredient(message: String) = apiService.searchByIngredient(message)

    fun searchByMealId(message: String) = apiService.searchByMealId(message)

    fun searchByMealCategory(message: String) = apiService.searchByMealCategory(message)
}