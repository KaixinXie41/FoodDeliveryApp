package com.example.fooddeliveryapp.view.auth.profile

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fooddeliveryapp.databinding.ActivityProfileBinding
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.dao.UserDao
import com.example.fooddeliveryapp.model.local.entities.User
import com.example.fooddeliveryapp.view.activity.MainActivity
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.Account_Information
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.USER_EMAIL
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.USER_ID
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.USER_MOBILE
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.USER_NAME
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.USER_PASSWORD

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var userDao: UserDao
    lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        appDatabase = AppDatabase.getInstance(this)
        userDao = appDatabase.getUserDao()
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        sharedPreferences = getSharedPreferences(Account_Information, MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val userName = binding.editName
        val userEmail = binding.editEmail
        val userMobile = binding.editMobile

        userName.hint = sharedPreferences.getString(USER_NAME, USER_NAME)
        userEmail.hint = sharedPreferences.getString(USER_EMAIL, USER_EMAIL)
        userMobile.hint = sharedPreferences.getString(USER_MOBILE, USER_MOBILE)

        binding.buttonUpdate.setOnClickListener {
            val userId = sharedPreferences.getLong(USER_ID, -1)
            val name = binding.editName.text.toString()
            val email = binding.editEmail.text.toString()
            val mobile = binding.editMobile.text.toString()
            val password = sharedPreferences.getString(USER_PASSWORD,"")

            editor.putLong(USER_ID,userId)
            editor.putString(USER_NAME,name)
            editor.putString(USER_EMAIL,email)
            editor.putString(USER_MOBILE,mobile)
            editor.apply()
            val user = User(userId, name,mobile,email, password.toString())
            userDao.updateUser(user)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        binding.imageClose.setOnClickListener {
            val intent = Intent(this@ProfileActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}