package com.example.fooddeliveryapp.view.auth.registration.phone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fooddeliveryapp.databinding.ActivityConfirmOtpactivityBinding
import com.example.fooddeliveryapp.view.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class ConfirmOTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmOtpactivityBinding
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        val storedVerification = intent.getStringExtra("storedVerificationId")

        binding.btnConfirm.setOnClickListener(){
            val otp = binding.edtOTP.text.toString()
            if(otp.isNotEmpty()){
                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(storedVerification.toString(), otp)
                signInWithCredential(credential)

            }
        }
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener{
                    task ->
                if (task.isSuccessful){
                    val intent = Intent(this@ConfirmOTPActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "Login Failure", Toast.LENGTH_SHORT).show()
                }
            }

    }
}