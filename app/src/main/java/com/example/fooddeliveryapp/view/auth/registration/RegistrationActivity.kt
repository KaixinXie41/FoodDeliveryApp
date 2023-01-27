package com.example.fooddeliveryapp.view.auth.registration

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.fooddeliveryapp.databinding.ActivityRegistrationBinding
import com.example.fooddeliveryapp.model.local.entities.User
import com.example.fooddeliveryapp.view.activity.MainActivity
import com.example.fooddeliveryapp.view.auth.login.LoginActivity
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

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRegistrationBinding
    private lateinit var currentUser: FirebaseUser
    private lateinit var authViewModel: AuthViewModel
    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    val REQ_CODE = 123



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        initView()
        prepareSignInOption()


    }

    //Email Type Registration
    private fun initView() {
        binding.circularProgress.visibility = View.VISIBLE
        binding.btnRegister.setOnClickListener{
            val signUpEmail = binding.edtEmail.text.toString()
            val signUpPassword = binding.edtPassword.text.toString()
            val signUpMobile = binding.edtMobile.text.toString()
            val signUpUserName = binding.edtName.text.toString()
            val user = User(0,signUpUserName,signUpMobile,signUpEmail,signUpPassword)
            authViewModel.signUp(signUpEmail, signUpPassword)
            makeToast(this@RegistrationActivity, "Register Successful!")
            val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnPhone.setOnClickListener {
            val intent = Intent(this@RegistrationActivity, PhoneActivity::class.java)
            startActivity(intent)
        }
        binding.txtHaveAccount.setOnClickListener{
            val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
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


    //Google Type Registration
    private fun prepareSignInOption(){
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(CLIENT_ID)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        binding.btnGoogle.setOnClickListener{
            googleSignIntent()
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
            val account:GoogleSignInAccount?= task.getResult(ApiException::class.java)
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
                    val intent = Intent(this@RegistrationActivity, MainActivity::class.java)
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

    companion object{
        const val CLIENT_ID = "64521325605-6m9h4ghrjj2rl9bnia04iv4mfo4s1gf7.apps.googleusercontent.com"
        const val Account_Information = "Account information"
        const val USER_EMAIL ="email"
        const val USER_PASSWORD = "password"
        const val USER_NAME = "name"
        const val USER_ID = "userId"
        const val USER_MOBILE = "Mobile"
    }
}