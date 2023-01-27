package com.example.fooddeliveryapp.view.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.databinding.ActivityFavoriteBinding
import com.example.fooddeliveryapp.view.activity.FavoriteAdapter
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.Account_Information
import com.example.fooddeliveryapp.viewmodel.CheckoutViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: CheckoutViewModel
    private lateinit var adapter: FavoriteAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        sharedPreferences = getSharedPreferences(Account_Information, MODE_PRIVATE)
        editor = sharedPreferences.edit()
        setContentView(binding.root)
        setUpView()
        setUpViewModel()
        setUpObserver()

        binding.btnHomePage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setUpObserver() {
        viewModel.allFavorite.observe(this){
            binding.rvFavoriteItem.adapter = FavoriteAdapter(
                this,it
            )
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this)[CheckoutViewModel::class.java]
    }

    private fun setUpView() {
        binding.rvFavoriteItem.layoutManager = LinearLayoutManager(this)
    }
}