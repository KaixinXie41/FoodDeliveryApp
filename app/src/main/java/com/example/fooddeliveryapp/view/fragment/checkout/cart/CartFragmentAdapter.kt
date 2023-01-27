package com.example.fooddeliveryapp.view.fragment.checkout.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.ItemViewCartBinding
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.dao.CartDao
import com.example.fooddeliveryapp.model.local.entities.Cart
import com.example.fooddeliveryapp.view.fragment.meal.mealdetails.MealDetailsFragment
import com.example.fooddeliveryapp.view.fragment.meal.meallist.MealListAdapter.Companion.MEAL_ID
import com.example.fooddeliveryapp.viewmodel.CheckoutViewModel

class CartFragmentAdapter(
    private val viewModel: CheckoutViewModel,
    private val cartList: MutableList<Cart>,
    private val context: Context)
    :RecyclerView.Adapter<CartFragmentAdapter.CartViewHolder>(){

    private lateinit var binding : ItemViewCartBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var cartDao: CartDao


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        binding = ItemViewCartBinding.inflate(layoutInflater, parent, false)
        appDatabase = AppDatabase.getInstance(context)
        cartDao = appDatabase.getCartDao()
        return CartViewHolder(binding.root)
    }

    override fun getItemCount() = cartList.size


    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.apply {
            val info = cartList[position]
            var countNumber = info.count
            txtMealName.text = info.mealName
            txtMealPrice.text = info.mealPrice.toString()
            txtCount.text = info.count.toString()
            var cartTotal = info.totalPrice

            btnSub.setOnClickListener {
                if(countNumber <1){
                    viewModel.removeCart(info)
                    notifyItemChanged(position)
                    cartList.removeAt(position)
                    notifyItemRangeChanged(position, cartList.size)
                }else{
                    countNumber -= 1
                    cartTotal = (info.mealPrice * countNumber)
                    info.count = countNumber
                    info.totalPrice = cartTotal
                    txtCount.text = countNumber.toString()
                    viewModel.updateCart(info,false)
                }
            }
            btnAdd.setOnClickListener {
                countNumber += 1
                cartTotal = (info.mealPrice * countNumber)
                info.totalPrice = cartTotal
                info.count = countNumber
                txtCount.text = info.count.toString()
                viewModel.updateCart(info,true)

            }

            itemView.setOnClickListener { p0 ->
                val activity = p0!!.context as AppCompatActivity
                val mealDetailsFragment = MealDetailsFragment()
                val bundle = Bundle()
                val mealId = info.mealId
                bundle.putString(MEAL_ID, mealId)
                mealDetailsFragment.arguments = bundle
                activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout_full, mealDetailsFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    inner class CartViewHolder(view: View):RecyclerView.ViewHolder(view) {

        val txtMealName = binding.txtMealName
        val txtMealPrice = binding.txtPrice
        val btnAdd = binding.btnPlus
        val btnSub = binding.btnMin
        val txtCount = binding.txtMealCount
    }

    companion object {
        const val TOTAL_PRICE = "totalPrice"
    }
}