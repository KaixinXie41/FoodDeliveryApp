package com.example.fooddeliveryapp.view.fragment.checkout.deliveryoption

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.view.fragment.checkout.deliveryoption.PickUpAdapter.Companion.RESTAURANT_ID
import com.example.fooddeliveryapp.databinding.FragmentPickupBinding
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.dao.RestaurantDao
import com.example.fooddeliveryapp.model.local.entities.Restaurant
import com.example.fooddeliveryapp.viewmodel.CheckoutViewModel

class PickupFragment : Fragment() {

    private lateinit var binding : FragmentPickupBinding
    private lateinit var restaurantDao: RestaurantDao
    private lateinit var restaurantViewModel: CheckoutViewModel
    private lateinit var appDatabase: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPickupBinding.inflate(inflater, container,false)
        appDatabase = AppDatabase.getInstance(this.requireContext())
        restaurantDao=appDatabase.getRestaurantDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpViewModel()
        setUpObserver()
        initView()
    }

    private fun initView() {
        restaurantDao.insert(Restaurant(1,"Canes","1902 N US 75-Central Expy","4",33.227983889437134, -96.63681366926913))
        restaurantDao.insert(Restaurant(2,"Burger King","1700 W University Dr","3.6",33.217337014311106, -96.63202445630284))
        restaurantDao.insert(Restaurant(3,"Chili's Grill","1940 N US 75-Central Expy","4.0",33.218195029733586, -96.63441756577299))
        restaurantDao.insert(Restaurant(4,"Applebee's","1820 W University Dr","4.1",33.21744252586674, -96.6345898183086))
        restaurantDao.insert(Restaurant(5,"Wendy's","1714 W University Dr, McKinney, TX 75069","3.8",33.21715720559892, -96.6332021883642))


    }

    private fun setUpObserver() {
        restaurantViewModel.allRestaurant.observe(viewLifecycleOwner){
            binding.rvRestaurantList.adapter = PickUpAdapter(
                restaurantViewModel, it, this.requireContext())
        }
    }

    private fun setUpViewModel() {
        restaurantViewModel = ViewModelProvider(this)[CheckoutViewModel::class.java]
        restaurantViewModel.getRestaurantByRestaurantId(arguments?.getLong(RESTAURANT_ID)?: 0)
    }

    private fun setUpView() {
        binding.apply {
            rvRestaurantList.layoutManager = LinearLayoutManager(context)

        }
    }

}