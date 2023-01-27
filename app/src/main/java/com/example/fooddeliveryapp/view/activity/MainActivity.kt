package com.example.fooddeliveryapp.view.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.ActivityMainBinding
import com.example.fooddeliveryapp.model.local.utils.Utils
import com.example.fooddeliveryapp.view.activity.supportchat.ui.SupportChatActivity.Companion.CART_PAGE
import com.example.fooddeliveryapp.view.activity.supportchat.ui.SupportChatActivity.Companion.ORDER_PAGE
import com.example.fooddeliveryapp.view.auth.entry.EntryActivity
import com.example.fooddeliveryapp.view.auth.profile.ProfileActivity
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.Account_Information
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.USER_EMAIL
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.USER_ID
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.USER_NAME
import com.example.fooddeliveryapp.view.fragment.checkout.cart.CartFragment
import com.example.fooddeliveryapp.view.fragment.checkout.order.OrderFragment
import com.example.fooddeliveryapp.view.fragment.home.HomeFragment
import com.example.fooddeliveryapp.view.fragment.search.SearchByAreaFragment
import com.example.fooddeliveryapp.view.fragment.search.SearchByIngredientFragment
import com.example.fooddeliveryapp.view.fragment.search.SearchFragment
import com.example.fooddeliveryapp.viewmodel.AuthViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var viewModel: AuthViewModel
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkIntent()
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        initView()
    }

    private fun checkIntent() {
        if (intent.getBooleanExtra(ORDER_PAGE, false)) {
            addFragment(OrderFragment(), R.id.frameLayout_main)
        } else if (intent.getBooleanExtra(CART_PAGE, false)) {
            addFragment(CartFragment(), R.id.frameLayout_main)
        } else {
            addFragment(HomeFragment(), R.id.frameLayout_main)
            addFragment(SearchFragment(), R.id.frameLayout_search)
        }
    }


    private fun initView() {
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        drawerLayout = findViewById(R.id.drawerLayout_main)
        sharedPreferences = getSharedPreferences(Account_Information, MODE_PRIVATE)
        editor = sharedPreferences.edit()


        val navHeader = binding.navView.inflateHeaderView(R.layout.nav_header)
        val userName: TextView = navHeader.findViewById(R.id.txt_nv_head_name)
        val userEmail: TextView = navHeader.findViewById(R.id.txt_nv_head_email)

        userName.text = sharedPreferences.getString(USER_NAME, "")
        userEmail.text = sharedPreferences.getString(USER_EMAIL, USER_EMAIL)

        val navigationView = binding.navView
        navigationView.setNavigationItemSelectedListener { menuItems ->
            when (menuItems.itemId) {
                R.id.nav_cart_page -> {
                    val cartFragment = CartFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout_full, cartFragment)
                        .addToBackStack(null)
                        .commit()
                }
                R.id.nav_home_page -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.nav_order_page -> {
                    val orderFragment = OrderFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout_full, orderFragment)
                        .addToBackStack(null)
                        .commit()
                }
                R.id.nav_profile -> {
                    val userId = sharedPreferences.getLong(USER_ID, -1).toInt()
                    if (userId != -1) {
                        val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                        startActivity(intent)
                    } else {
                        Utils.showDialog(
                            this,
                            getString(R.string.approve),
                            getString(R.string.message_approve),
                            getString(R.string.approved)
                        )
                    }
                }
                R.id.nav_logout -> {
                    editor.clear().apply()
                    viewModel.signOut()
                    val intent = Intent(this@MainActivity, EntryActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        binding.btmAppbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.supportChat -> {
                    val intent = Intent(this@MainActivity, AboutMeActivity::class.java)
                    startActivity(intent)
                }
                R.id.rating -> {
                    val intent = Intent(this@MainActivity, RatingActivity::class.java)
                    startActivity(intent)
                }
                R.id.favorite -> {
                    val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }


        binding.btnOption.shrink()
        var isCollasped = false
        binding.apply {
            btnOption.setOnClickListener {
                if (!isCollasped) {
                    btnOption.show()
                    btnOptionArea.show()
                    btnOptionIngredient.show()
                    isCollasped = true
                } else {
                    btnOptionArea.hide()
                    btnOptionIngredient.hide()
                    isCollasped = false
                }
            }
            btnOptionArea.setOnClickListener { p0 ->
                val activity = p0!!.context as AppCompatActivity
                val searchByAreaFragment = SearchByAreaFragment()
                activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout_search, searchByAreaFragment)
                    .addToBackStack(null)
                    .commit()
            }
            btnOptionIngredient.setOnClickListener { p0 ->
                val activity = p0!!.context as AppCompatActivity
                val searchByIngredientsFragment = SearchByIngredientFragment()
                activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout_search, searchByIngredientsFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (binding.drawerLayoutMain.isDrawerOpen(GravityCompat.START))
                binding.drawerLayoutMain.closeDrawer(GravityCompat.START)
            else {
                binding.drawerLayoutMain.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addFragment(fragment: HomeFragment, container: Int) {
        supportFragmentManager.beginTransaction()
            .add(container, fragment)
            .commit()
    }

    private fun addFragment(fragment: SearchFragment, container: Int) {
        supportFragmentManager.beginTransaction()
            .add(container, fragment)
            .commit()
    }

    private fun addFragment(fragment: OrderFragment, container: Int) {
        supportFragmentManager.beginTransaction()
            .add(container, fragment)
            .commit()
    }

    private fun addFragment(fragment: CartFragment, container: Int) {
        supportFragmentManager.beginTransaction()
            .add(container, fragment)
            .commit()
    }

}