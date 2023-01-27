package com.example.fooddeliveryapp.view.fragment.checkout.deliveryoption

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.view.fragment.checkout.deliveryoption.DeliveryAdapter.Companion.ADDRESS_ID
import com.example.fooddeliveryapp.databinding.FragmentDeliveryBinding
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.dao.AddressDao
import com.example.fooddeliveryapp.model.local.entities.Address
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.Account_Information
import com.example.fooddeliveryapp.viewmodel.CheckoutViewModel

class DeliveryFragment : Fragment() {

    private lateinit var binding : FragmentDeliveryBinding
    private lateinit var addressViewModel: CheckoutViewModel
    private lateinit var addressArrayList:ArrayList<Address>
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var addressRadioGroup: RadioGroup
    private lateinit var hashMap : HashMap<Int,Int>
    private lateinit var appDatabase: AppDatabase
    private lateinit var addressDao: AddressDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeliveryBinding.inflate(inflater,container,false)
        appDatabase = AppDatabase.getInstance(this.requireContext())
        addressDao =appDatabase.getAddressDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpViewModel()
        setUpObserver()
        sharedPreferences = this.requireActivity().getSharedPreferences(Account_Information,AppCompatActivity.MODE_PRIVATE)
        editor = sharedPreferences.edit()


    }

    private fun setUpObserver() {
        addressViewModel.allAddress.observe(viewLifecycleOwner){
            binding.rvAddressList.adapter = DeliveryAdapter(
                addressViewModel, it, this.requireContext())
        }
    }

    private fun setUpView() {
        binding.apply {
            rvAddressList.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setUpViewModel() {
        addressViewModel = ViewModelProvider(this)[CheckoutViewModel::class.java]
        addressViewModel.getAddressByAddressId(arguments?.getLong(ADDRESS_ID)?: 0)
    }

}