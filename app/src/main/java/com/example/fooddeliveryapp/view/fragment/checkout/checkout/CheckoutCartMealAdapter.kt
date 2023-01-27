package com.example.fooddeliveryapp.view.fragment.checkout.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.databinding.ItemViewPurchaseMealBinding
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.dao.CartDao
import com.example.fooddeliveryapp.model.local.entities.Cart
import com.example.fooddeliveryapp.viewmodel.CheckoutViewModel

class CheckoutCartMealAdapter(
    private val viewModel: CheckoutViewModel,
    val cartArrayList: List<Cart>,
    private val context: Context
) : RecyclerView.Adapter<CheckoutCartMealAdapter.CartProductViewHolder>(){

    private lateinit var binding: ItemViewPurchaseMealBinding
    private lateinit var cartDao: CartDao
    private lateinit var appDatabase: AppDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        binding = ItemViewPurchaseMealBinding.inflate(layoutInflater, parent, false)
        return CartProductViewHolder(binding.root)
    }

    override fun getItemCount() = cartArrayList.size

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        holder.apply {
            val cart = cartArrayList[position]
            cartMealName.text = cart.mealName
            unitQuantity.text = cart.count.toString()
            mealPrice.text = cart.mealPrice.toString()
        }
    }

    inner class CartProductViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        val cartMealName: TextView = binding.txtMealName
        val unitQuantity: TextView = binding.txtQuantityValue
        val mealPrice : TextView = binding.txtUnitPriceValue

    }
}