package com.example.fooddeliveryapp.di

import com.example.fooddeliveryapp.model.local.data.category.CategoryRepository
import com.example.fooddeliveryapp.model.remote.ApiService
import com.example.fooddeliveryapp.model.remote.Constants.BASE_URL
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRetrofit():Retrofit{
        val retrofit = Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }.build()
        return retrofit
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesCategoryRepository(apiService: ApiService):CategoryRepository{
        return CategoryRepository(apiService)
    }

}