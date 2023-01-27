package com.example.fooddeliveryapp.view.fragment.checkout.checkout

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CheckoutAdapter (
    checkoutFragment: CheckoutMealFragment,
    private val count:Int)
    : FragmentStateAdapter(checkoutFragment){

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> CheckoutCartMealFragment()
            1 -> CheckoutDeliveryFragment()
            2 -> CheckoutPaymentFragment()
            3 -> CheckoutSummaryFragment()
            else -> CheckoutCartMealFragment()
        }
    }

    override fun getItemCount() = count

}