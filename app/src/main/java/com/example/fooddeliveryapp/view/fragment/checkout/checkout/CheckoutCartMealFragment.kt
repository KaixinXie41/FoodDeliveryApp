package com.example.fooddeliveryapp.view.fragment.checkout.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.databinding.FragmentCheckoutCartMealBinding
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.dao.CartDao
import com.example.fooddeliveryapp.model.local.entities.Cart
import com.example.fooddeliveryapp.viewmodel.CheckoutViewModel

class CheckoutCartMealFragment : Fragment() {

    private lateinit var binding: FragmentCheckoutCartMealBinding
    private lateinit var cartMealList:LiveData<List<Cart>>
    private lateinit var cartViewModel : CheckoutViewModel
    private lateinit var cartDao: CartDao
    private lateinit var adapter : CheckoutCartMealAdapter
    private lateinit var appDatabase: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentCheckoutCartMealBinding.inflate(inflater, container,false)
        appDatabase = AppDatabase.getInstance(this.requireContext())
        cartDao = appDatabase.getCartDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpVieModel()
        setUpObserver()
        binding.btnNext.setOnClickListener {
            (this.parentFragment as CheckoutMealFragment).nextPager()
        }
    }

    private fun setUpObserver() {
        cartViewModel.allCart.observe(viewLifecycleOwner) {
            binding.recyclerViewCheckoutMeal.adapter = CheckoutCartMealAdapter(
                cartViewModel, it, this.requireContext()
            )
            var total = 0.0
            val size = it.size
            for (i in 0 until size) {
                val meal = it[i]
                total += meal.mealPrice * meal.count
            }
            binding.txtTotalAmountValue.text = total.toString()
        }
    }

    private fun setUpVieModel() {
        cartViewModel = ViewModelProvider(this)[CheckoutViewModel::class.java]
    }

    private fun setUpView() {
        binding.recyclerViewCheckoutMeal.layoutManager = LinearLayoutManager(context)
    }

}