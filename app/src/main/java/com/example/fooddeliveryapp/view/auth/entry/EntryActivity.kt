package com.example.fooddeliveryapp.view.auth.entry

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fooddeliveryapp.databinding.ActivityEntryBinding
import com.example.fooddeliveryapp.view.activity.MainActivity
import com.example.fooddeliveryapp.view.auth.login.LoginActivity
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.Account_Information
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.USER_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEntryBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = this.getSharedPreferences(Account_Information, MODE_PRIVATE)
        editor = sharedPreferences.edit()
        initView()

    }

    private fun initView() {
        val userId = sharedPreferences.getLong(USER_ID, -1)
        if(userId.toInt() != -1){
            val intent = Intent(this@EntryActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.apply {
            btnRegister.setOnClickListener {
                val intent = Intent(this@EntryActivity, RegistrationActivity::class.java)
                startActivity(intent)
                finish()
            }

            btnLogin.setOnClickListener {
                val intent = Intent(this@EntryActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            txtGuestEntry.setOnClickListener{
                val intent = Intent(this@EntryActivity, MainActivity::class.java)
                startActivity(intent)
                makeToast(this@EntryActivity, "Login as Guest ")
                finish()
            }
        }
    }


    private fun makeToast(context: Context, s:String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }
}