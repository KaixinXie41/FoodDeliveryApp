package com.example.fooddeliveryapp.view.fragment.checkout.order

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.databinding.ItemViewOrderListMealBinding
import com.example.fooddeliveryapp.model.local.entities.Item

class OrderItemAdapter(
    private val itemList:List<Item>,
    private val context:Context)
    :RecyclerView.Adapter<OrderItemAdapter.ItemViewHolder>(){

    private lateinit var binding: ItemViewOrderListMealBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ItemViewOrderListMealBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(
            item = itemList[position]
        )
    }

    override fun getItemCount() = itemList.size

    inner class ItemViewHolder(view: View):RecyclerView.ViewHolder(view) {
        fun bind(item: Item){
            binding.apply {
                txtOrderMealName.text = item.meal_name
                txtMultiple.text = item.quantity.toString()
            }
        }
    }
}