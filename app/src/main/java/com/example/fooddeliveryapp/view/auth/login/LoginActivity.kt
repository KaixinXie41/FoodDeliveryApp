package com.example.fooddeliveryapp.view.auth.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.fooddeliveryapp.databinding.ActivityLoginBinding
import com.example.fooddeliveryapp.model.local.entities.User
import com.example.fooddeliveryapp.view.activity.MainActivity
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.Account_Information
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.CLIENT_ID
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.USER_EMAIL
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.USER_ID
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.USER_MOBILE
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.USER_NAME
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.USER_PASSWORD
import com.example.fooddeliveryapp.view.auth.registration.phone.PhoneActivity
import com.example.fooddeliveryapp.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var currentUser: FirebaseUser
    private lateinit var authViewModel: AuthViewModel
    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    val REQ_CODE = 123
    private lateinit var sharePreferences:SharedPreferences
    private lateinit var editor:SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setUpViewModel()
        prepareSignInOption()
    }
    //Email Type Login
    private fun initView() {
        binding.circularProgress.visibility = View.VISIBLE
        binding.btnLogin.setOnClickListener {
            val signInEmail = binding.edtEmail.text.toString()
            val signInPassword = binding.edtPassword.text.toString()
            val user = User(0,null,null,signInEmail,signInPassword)
            sharePreferences = getSharedPreferences(Account_Information, MODE_PRIVATE)
            editor = sharePreferences.edit()
            editor.apply{
                putLong(USER_ID, user.userId)
                putString(USER_EMAIL, user.email)
                putString(USER_PASSWORD, user.password)
                putString(USER_MOBILE, user.mobileNo)
                putString(USER_NAME, user.fullName)
                apply()
            }

            authViewModel.signIn(signInEmail, signInPassword)
            makeToast(this, "Login Successful!")
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnPhone.setOnClickListener {
            val intent =
                Intent(this@LoginActivity, PhoneActivity::class.java)
            startActivity(intent)
        }
        binding.txtNotAccount.setOnClickListener {
            val intent =
                Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart(){
        super.onStart()
        auth = Firebase.auth
        if(this::currentUser.isInitialized){
            if(auth.currentUser != null && currentUser!= auth){
                currentUser = auth.currentUser as FirebaseUser
            }
        }
    }

    private fun makeToast(context: Context, s:String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

    private fun setUpViewModel() {
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

    }


    //Google Type Login
    private fun prepareSignInOption(){
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(CLIENT_ID)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        binding.btnGoogle.setOnClickListener{
            googleSignIntent()
        }
        binding.btnCrash.setOnClickListener {
            val a = 180 / 0
            Log.e("tag", "crash by click button")
        }
    }
    private fun googleSignIntent() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQ_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CODE) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleTask(task)
        }
    }
    private fun handleTask(task: Task<GoogleSignInAccount>) {
        try{
            val account: GoogleSignInAccount?= task.getResult(ApiException::class.java)
            account?.let {
                updateUI(account)
            }
        }catch(e: ApiException){
            e.printStackTrace()
        }
    }
    private fun updateUI(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credentials)
            .addOnCompleteListener{
                    task ->
                if(task.isSuccessful) {
                    makeToast(this,"Login success")
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.apply {
                        account.apply {
                            putExtra("name", displayName)
                            putExtra("email", email)
                            putExtra("profilePicture", photoUrl)

                        }
                    }
                    startActivity(intent)
                }else{
                    makeToast(this,"Login Failed")
                }
            }

    }
}