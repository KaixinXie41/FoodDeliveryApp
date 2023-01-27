package com.example.fooddeliveryapp.view.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.databinding.ItemViewFavoriteItemBinding
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.dao.FavoriteDao
import com.example.fooddeliveryapp.model.local.entities.Favorite

class FavoriteAdapter(
    private val context:Context,
    private val favoriteList:List<Favorite>)
    :RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>(){

    private lateinit var binding:ItemViewFavoriteItemBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var favoriteDao: FavoriteDao

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ItemViewFavoriteItemBinding.inflate(layoutInflater, parent,false)
        appDatabase = AppDatabase.getInstance(context)
        favoriteDao = appDatabase.getFavoriteDao()
        return FavoriteViewHolder(binding.root)

    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.apply {
            val favorite = favoriteList[position]
            btnFavorite.isChecked = true
            favoriteName.text = favorite.mealName
            Glide.with(context)
                .load(favorite.mealPicture)
                .into(favoriteImage)

            btnFavorite.setOnClickListener{
                if(btnFavorite.isChecked){
                    favoriteDao.addFavorite(favorite)
                    btnFavorite.isChecked =true

                }else{
                    favoriteDao.deleteFavorite(favorite)
                    btnFavorite.isChecked = false
                }
            }
        }
    }

    override fun getItemCount() = favoriteList.size

    inner class FavoriteViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var favoriteImage  = binding.imgFavoriteMeal
        var favoriteName = binding.txtFavoriteName
        var btnFavorite = binding.btnFavorite

    }
}