package com.example.fooddeliveryapp.view.fragment.meal.mealdetails

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentMealDetailsBinding
import com.example.fooddeliveryapp.di.AppModule
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.dao.CartDao
import com.example.fooddeliveryapp.model.local.data.category.CategoryRepository
import com.example.fooddeliveryapp.model.local.entities.Cart
import com.example.fooddeliveryapp.model.remote.ApiService
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.Account_Information
import com.example.fooddeliveryapp.view.fragment.checkout.cart.CartFragment
import com.example.fooddeliveryapp.view.fragment.checkout.cart.CartFragment.Companion.CART
import com.example.fooddeliveryapp.view.fragment.checkout.cart.CartFragmentAdapter.Companion.TOTAL_PRICE
import com.example.fooddeliveryapp.view.fragment.checkout.checkout.CheckoutSummaryFragment
import com.example.fooddeliveryapp.view.fragment.meal.area.AreaListAdapter.Companion.MEAL_ID
import com.example.fooddeliveryapp.viewmodel.CategoryViewModel
import com.example.fooddeliveryapp.viewmodel.CategoryViewModelProvider
import javax.inject.Inject
import kotlin.random.Random

class MealDetailsFragment : Fragment() {

    @Inject
    lateinit var appModule:AppModule
    private lateinit var binding: FragmentMealDetailsBinding
    private lateinit var mealViewModel : CategoryViewModel
    private lateinit var appDatabase: AppDatabase
    private lateinit var cartDao: CartDao
    private var count = 0
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealDetailsBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(this.requireContext())
        cartDao = appDatabase.getCartDao()
        sharedPreferences = this.requireActivity().getSharedPreferences(
            Account_Information, AppCompatActivity.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appModule = AppModule
        setUpView()
        setUpViewModel()
        setUpObserver()
    }

    private fun setUpView() {
        binding.apply {
            rvMealDetails.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setUpViewModel() {
        val repository = CategoryRepository(appModule.providesRetrofit().create(ApiService::class.java))
        val factory = CategoryViewModelProvider(repository)
        mealViewModel = ViewModelProvider(this,factory)[CategoryViewModel::class.java]
        mealViewModel.searchByMealId(arguments?.getString(MEAL_ID) ?: "" )
    }

    private fun setUpObserver(){
        mealViewModel.mealLiveData.observe(viewLifecycleOwner){
            binding.rvMealDetails.adapter = MealDetailsAdapter(mealViewModel, it.meals ,this.requireContext())
            val price = price()
            val total = (count * price).toDouble()
            val cartItem = Cart(0, it.meals[0].strMeal, it.meals[0].idMeal,price.toDouble(),count,total,it.meals[0].strMealThumb, it.meals[0].strCategory )
            binding.btnPlus.setOnClickListener {
                count++
                binding.txtMealCount.text = count.toString()
                binding.txtTotalPrice.text = (count * price).toString()
            }
            binding.btnMin.setOnClickListener {
                if(count > 0){
                    count--
                }
                binding.txtMealCount.text = count.toString()
                binding.txtTotalPrice.text = (count * price).toString()


            }
            binding.btnAddToCart.setOnClickListener{p0 ->
                cartItem.count = count
                cartItem.totalPrice = total
                cartDao.addCart(cartItem)
                editor.putString(TOTAL_PRICE, total.toString())
                editor.apply()
                val activity = p0!!.context as AppCompatActivity
                val cartFragment = CartFragment()
                val checkoutSummaryFragment = CheckoutSummaryFragment()
                val bundle = Bundle()
                bundle.putParcelable(CART,cartItem)
                checkoutSummaryFragment.arguments = bundle
                activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout_full, cartFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
    private fun price(): Int {
        return Random.nextInt(15, 40)
    }
}