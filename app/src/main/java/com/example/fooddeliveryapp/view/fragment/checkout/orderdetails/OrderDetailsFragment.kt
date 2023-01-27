package com.example.fooddeliveryapp.view.fragment.checkout.orderdetails

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.databinding.FragmentOrderDetailsBinding
import com.example.fooddeliveryapp.model.local.entities.Order
import com.example.fooddeliveryapp.view.activity.AboutMeActivity
import com.example.fooddeliveryapp.view.activity.MainActivity
import com.example.fooddeliveryapp.view.activity.foodtracking.GetCurrentDeliveryLocationActivity
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.Account_Information
import com.example.fooddeliveryapp.view.fragment.checkout.order.OrderAdapter.Companion.ORDER
import com.example.fooddeliveryapp.view.fragment.checkout.order.OrderAdapter.Companion.ORDER_ID
import com.example.fooddeliveryapp.viewmodel.CheckoutViewModel


class OrderDetailsFragment : Fragment() {

    private lateinit var binding : FragmentOrderDetailsBinding
    private lateinit var orderViewModel: CheckoutViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var order: Order


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        sharedPreferences = this.requireActivity().getSharedPreferences(
            Account_Information,
            AppCompatActivity.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpViewModel()
        setUpObserver()
    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpObserver() {
        orderViewModel.allItem.observe(viewLifecycleOwner) {
            binding.recyclerOrderItems.adapter = OrderDetailsAdapter(
                orderViewModel, it, this.requireContext()
            )

            binding.imageClose.setOnClickListener {
                val intent = Intent(this.requireContext(), MainActivity::class.java)
                startActivity(intent)

            }
            binding.txtOrderStatusValue.setOnClickListener {
                val intent = Intent(this.requireContext(), GetCurrentDeliveryLocationActivity::class.java)
                startActivity(intent)
            }
            binding.imageCall.setOnClickListener{
                val intent = Intent(this.requireContext(), AboutMeActivity::class.java)
                startActivity(intent)
            }
        }
        val bundle =this.arguments
        if(bundle != null){
            order = bundle.getParcelable(ORDER)!!
        }
            binding.apply {
                txtOrderStatusValue.text = order.order_status
                txtItemTotalPrice.text = order.bill_amount.toString()
                val deliveryFee = (order.bill_amount.toInt())/10.toDouble()
                txtDeliveryPrice.text = deliveryFee.toString()
                txtTotalPrice.text = (order.bill_amount.toInt() + deliveryFee).toString()
                txtOrderTime.text = order.order_date
                txtOrderIdValue.text = order.orderId.toString()
                txtDeliveryLocation.text = "${order.addressTitle}\n + ${order.address}"
                txtTransactionId.text = (order.orderId + 1413).toString()

        }
    }

    private fun setUpViewModel() {
        orderViewModel = ViewModelProvider(this)[CheckoutViewModel::class.java]
        orderViewModel.getOrderByOrderId(arguments?.getLong(ORDER_ID)?:0)
    }

    private fun setUpView() {
        binding.apply {
            recyclerOrderItems.layoutManager = LinearLayoutManager(context)
        }
    }

}
