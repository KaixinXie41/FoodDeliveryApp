package com.example.fooddeliveryapp.view.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fooddeliveryapp.databinding.ActivityAboutMeBinding
import com.example.fooddeliveryapp.view.activity.supportchat.ui.SupportChatActivity

class AboutMeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAboutMeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutMeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.apply {
            btnContactEmail.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "message/rfc822"
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("xkx372752892@gmail.com"))
                intent.putExtra(Intent.EXTRA_TEXT, "Text you want to share")
                startActivity(Intent.createChooser(intent, "Send mail..."))
            }
            btnContactCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:"+"+12148839090")
                startActivity(intent)
            }
            btnSupportChat.setOnClickListener {
                val intent = Intent(this@AboutMeActivity, SupportChatActivity::class.java)
                startActivity(intent)
            }
        }
    }
}