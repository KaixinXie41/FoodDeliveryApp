package com.example.fooddeliveryapp.view.auth.registration.phone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.ActivityPhoneBinding
import com.example.fooddeliveryapp.model.local.entities.Phone
import com.example.fooddeliveryapp.viewmodel.AuthViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneActivity : AppCompatActivity() {

    private var number:String = ""
    private lateinit var binding: ActivityPhoneBinding
    private lateinit var storeVerificationId:String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var  callBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var  auth: FirebaseAuth
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }



    private fun initView() {
        auth = FirebaseAuth.getInstance()
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        binding.btnSendCode.setOnClickListener {
            register()
        }
        setUpCallBack()

        val spinner = binding.spinnerCountryCode

        val arrayList = ArrayList<Phone>()
        arrayList.apply {
            add(Phone("+1", R.drawable.unitedstate))
            add(Phone("+86", R.drawable.china))
            add(Phone("+44", R.drawable.unitedkingdom))
            add(Phone("+49", R.drawable.german))
            add(Phone("+33", R.drawable.france))
            add(Phone("+81", R.drawable.japan))
            add(Phone("+82", R.drawable.korean))
        }
        val userAdapter = PhoneAdapter(arrayList)
        spinner.adapter = userAdapter
    }

    private fun setUpCallBack() {
        callBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                Log.i("tag", "onVerificationCompleted")
                finish()

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.i("tag", "onVerificationFailed")
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                storeVerificationId = p0
                resendToken = p1
                val intent = Intent(this@PhoneActivity, ConfirmOTPActivity::class.java)
                intent.putExtra("storedVerificationId", storeVerificationId)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun register(){
        number = binding.edtPhone.text.toString()
        if(number.isNotEmpty()){
            number = "+1$number"
            sendVerification(number)
        }
    }

    private fun sendVerification(number: String){

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callBack)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}