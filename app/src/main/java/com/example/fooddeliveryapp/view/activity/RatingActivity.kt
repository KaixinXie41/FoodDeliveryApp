package com.example.fooddeliveryapp.view.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.ActivityRatingBinding
import com.google.android.material.snackbar.Snackbar

class RatingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRatingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.apply {
            ratingBar.setOnRatingBarChangeListener { _, _, _ ->
                when (binding.ratingBar.rating.toInt()) {
                    0 -> {
                        imgEmoji.setImageResource(R.drawable.empty)
                    }
                    1 -> {
                        imgEmoji.setImageResource(R.drawable.angry)
                    }
                    2 -> {
                        imgEmoji.setImageResource(R.drawable.sadface)
                    }
                    3 -> {
                        imgEmoji.setImageResource(R.drawable.natrualface)
                    }
                    4 -> {
                        imgEmoji.setImageResource(R.drawable.smile)
                    }
                    5 -> {
                        imgEmoji.setImageResource(R.drawable.loveit)
                    }
                }
            }
            btnSubmit.setOnClickListener {
                val ratingAmount = binding.ratingBar.rating.toString()
                val snackbar = Snackbar.make(it, "Thank you for rating this app! Your rating is $ratingAmount", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.GRAY)
                snackbar.show()

                val intent = Intent(this@RatingActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}

