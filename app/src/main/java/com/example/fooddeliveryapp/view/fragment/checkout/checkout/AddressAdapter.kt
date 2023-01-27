package com.example.fooddeliveryapp.view.fragment.checkout.checkout

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.databinding.ItemViewAddressBinding
import com.example.fooddeliveryapp.model.local.entities.Address

class AddressAdapter(
    private val arrayList: List<Address>,
    private val context: Context
) :RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {
    private lateinit var binding: ItemViewAddressBinding

    override fun getItemCount() = arrayList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        binding = ItemViewAddressBinding.inflate(LayoutInflater.from(context), parent, false)
        return AddressViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(
            address = arrayList[position]
        )
    }


    inner class AddressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(address: Address) {
            binding.txtAddressTitle.text = address.title
            binding.txtAddress.text = address.address

            itemView.setOnClickListener {
                Log.e("address_id", address.addressId.toString())
            }
        }
    }
}
