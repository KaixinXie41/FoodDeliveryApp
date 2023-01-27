package com.example.fooddeliveryapp.view.fragment.checkout.deliveryoption

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.view.fragment.checkout.deliveryoption.DeliveryAdapter.Companion.ADDRESS_ID
import com.example.fooddeliveryapp.databinding.FragmentEditAddressBinding
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.dao.AddressDao
import com.example.fooddeliveryapp.model.local.entities.Address
import com.example.fooddeliveryapp.view.fragment.checkout.checkout.CheckoutDeliveryFragment
import com.example.fooddeliveryapp.viewmodel.CheckoutViewModel

class EditAddressFragment : Fragment() {

    private lateinit var binding: FragmentEditAddressBinding
    private lateinit var addressViewModel : CheckoutViewModel
    private lateinit var addressDao: AddressDao
    private lateinit var appDatabase: AppDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditAddressBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(this.requireContext())
        addressDao = appDatabase.getAddressDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        setUpObserver()
    }

    private fun setUpObserver() {
        addressViewModel.allAddress.observe(viewLifecycleOwner) {
            binding.apply {
                val addressId = it[0].addressId
                btnUpdate.setOnClickListener {
                    val title = edtAddressTitle.text.toString()
                    val address = edtAddress.text.toString()
                    addressViewModel.updateAddress(Address(addressId,address,title))
                    val activity = it!!.context as AppCompatActivity
                    val checkoutDeliveryFragment = CheckoutDeliveryFragment()
                    activity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout_delivery_option, checkoutDeliveryFragment)
                        .addToBackStack(null)
                        .commit()
                }
                btnCancel.setOnClickListener {
                    val activity = it!!.context as AppCompatActivity
                    val checkoutDeliveryFragment = CheckoutDeliveryFragment()
                    activity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout_delivery_option, checkoutDeliveryFragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }


    private fun setUpViewModel() {
        addressViewModel = ViewModelProvider(this@EditAddressFragment)[CheckoutViewModel::class.java]
        addressViewModel.getAddressByAddressId(arguments?.getLong(ADDRESS_ID)?: 0)
    }

}