package com.example.fooddeliveryapp.view.fragment.checkout.deliveryoption

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.ItemViewAddressBinding
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.dao.AddressDao
import com.example.fooddeliveryapp.model.local.entities.Address
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.Account_Information
import com.example.fooddeliveryapp.view.fragment.checkout.checkout.CheckoutDeliveryFragment.Companion.ADDRESS
import com.example.fooddeliveryapp.view.fragment.checkout.checkout.CheckoutDeliveryFragment.Companion.ADDRESS_TITLE
import com.example.fooddeliveryapp.view.fragment.checkout.checkout.CheckoutSummaryFragment
import com.example.fooddeliveryapp.viewmodel.CheckoutViewModel

class DeliveryAdapter(
    private val viewModel: CheckoutViewModel,
    private val addressList:List<Address>,
    private val context: Context)
    :RecyclerView.Adapter<DeliveryAdapter.AddressViewHolder>() {

    private lateinit var binding: ItemViewAddressBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var addressDao: AddressDao
    private lateinit var addressItem: Address
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        binding = ItemViewAddressBinding.inflate(layoutInflater, parent, false)
        appDatabase = AppDatabase.getInstance(context)
        addressDao = appDatabase.getAddressDao()
        sharedPreferences = context.getSharedPreferences(Account_Information, AppCompatActivity.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        return AddressViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.apply {
            val item = addressList[position]
            txtAddressTitle.text = item.title
            txtAddress.text = item.address


            btnEdit.setOnClickListener { p0 ->
                val activity = p0!!.context as AppCompatActivity
                val editAddressFragment = EditAddressFragment()
                val bundle = Bundle()
                val addressId = item.addressId
                bundle.putLong(ADDRESS_ID, addressId)
                editAddressFragment.arguments = bundle
                activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout_delivery_option, editAddressFragment)
                    .addToBackStack(null)
                    .commit()
            }
            itemView.setOnClickListener {
                val checkoutSummaryFragment = CheckoutSummaryFragment()
                val deliveryFragment = DeliveryFragment()
                val bundle = Bundle()
                val addressId = item.addressId
                bundle.putLong(ADDRESS_ID, addressId)
                checkoutSummaryFragment.arguments = bundle
                deliveryFragment.arguments = bundle
            }
            addressRadioButton.setOnClickListener {
                val address = item.address
                val title = item.title
                editor.putString(ADDRESS, address)
                editor.putString(ADDRESS_TITLE, title)
                editor.apply()
            }
            binding.btnDeleteAddress.setOnClickListener {
                addressDao.delete(item)
            }
        }
    }



    override fun getItemCount() = addressList.size

    inner class AddressViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val txtAddressTitle = binding.txtAddressTitle
        val txtAddress = binding.txtAddress
        val btnEdit = binding.btnEditAddress
        val addressRadioButton = binding.radioButton
    }

    companion object{
        const val  ADDRESS_ID = "addressId"
    }
}