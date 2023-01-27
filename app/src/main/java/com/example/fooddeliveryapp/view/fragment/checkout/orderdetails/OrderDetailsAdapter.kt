package com.example.fooddeliveryapp.view.fragment.checkout.orderdetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.ItemViewOrderMealBinding
import com.example.fooddeliveryapp.model.local.entities.Item
import com.example.fooddeliveryapp.viewmodel.CheckoutViewModel

class OrderDetailsAdapter(
    private val viewModel: CheckoutViewModel,
    private val itemList:List<Item>,
    private val context:Context)
    :RecyclerView.Adapter<OrderDetailsAdapter.OrderViewHolder>(){

    private lateinit var binding : ItemViewOrderMealBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ItemViewOrderMealBinding.inflate(layoutInflater, parent, false)
        return OrderViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(
            item = itemList[position]
        )
    }

    override fun getItemCount() = itemList.size

    inner class OrderViewHolder(view: View):RecyclerView.ViewHolder(view) {
        fun bind(item: Item){
            binding.apply {
                txtMultiple.text =item.quantity.toString()
                txtMealUntilPrice.text = item.unit_price.toString()
                txtOrderMealName.text = item.meal_name
                if(item.meal_category == "Vegetarian"){
                    Glide.with(context)
                        .load(R.drawable.ic_veg)
                        .into(imgVeg)
                }else{
                    Glide.with(context)
                        .load(R.drawable.ic_non_veg)
                        .into(imgVeg)
                }
            }
        }
    }

}