package com.example.fooddeliveryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fooddeliveryapp.model.local.data.category.CategoryRepository
import javax.inject.Inject

class CategoryViewModelProvider@Inject constructor(private val repository: CategoryRepository)
    :ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(repository) as T
    }
}