package com.example.fooddeliveryapp.view.fragment.checkout.order

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.ItemViewOrderBinding
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.dao.ItemDao
import com.example.fooddeliveryapp.model.local.entities.Order
import com.example.fooddeliveryapp.view.activity.foodtracking.GetCurrentDeliveryLocationActivity
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.Account_Information
import com.example.fooddeliveryapp.view.fragment.checkout.orderdetails.OrderDetailsFragment
import com.example.fooddeliveryapp.viewmodel.CheckoutViewModel

class OrderAdapter(
    private val viewModel: CheckoutViewModel,
    private val orderArrayList: List<Order>,
    private val context: Context
)
    :RecyclerView.Adapter<OrderAdapter.OrderViewHolder>(){

    private lateinit var binding: ItemViewOrderBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var appDatabase: AppDatabase
    private lateinit var itemDao: ItemDao

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ItemViewOrderBinding.inflate(layoutInflater, parent, false)
        sharedPreferences = context.getSharedPreferences(Account_Information, AppCompatActivity.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        appDatabase = AppDatabase.getInstance(context)
        itemDao = appDatabase.getItemDao()
        return OrderViewHolder(binding.root)
    }

    override fun getItemCount() = orderArrayList.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(
            order = orderArrayList[position]
        )
    }
    inner class OrderViewHolder(view: View):RecyclerView.ViewHolder(view) {
        fun bind(order: Order) {
            binding.txtOrderAddressTitle.text = order.addressTitle
            binding.txtOrderDate.text = order.order_date
            binding.txtOrderAmountValue.text = order.bill_amount.toString()
            binding.txtOrderStatus.text = order.order_status

            itemView.setOnClickListener { p0 ->
                val activity = p0!!.context as AppCompatActivity
                val orderDetailsFragment = OrderDetailsFragment()
                val bundle = Bundle()
                bundle.putParcelable(ORDER, order)
                orderDetailsFragment.arguments = bundle
                activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout_full, orderDetailsFragment)
                    .addToBackStack(null)
                    .commit()
            }

            binding.btnTrackOrder.setOnClickListener {
                val intent = Intent(context, GetCurrentDeliveryLocationActivity::class.java)
                context.startActivity(intent)

            }
            val itemList = itemDao.getItemByOrderId(order.orderId).value
            if(itemList != null){
                binding.rvOrderListItem.adapter = OrderItemAdapter(itemList, context)
                binding.rvOrderListItem.layoutManager = LinearLayoutManager(context)
            }
        }
    }
    companion object{
        const val ORDER_ID = "order_Id"
        const val ORDER = "order"
    }
}