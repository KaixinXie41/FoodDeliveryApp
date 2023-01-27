package com.example.fooddeliveryapp.view.fragment.checkout.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.databinding.FragmentOrderBinding
import com.example.fooddeliveryapp.viewmodel.CheckoutViewModel


class OrderFragment : Fragment() {

    private lateinit var binding : FragmentOrderBinding
    private lateinit var orderViewModel: CheckoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpViewModel()
        setUpObserver()
    }



    private fun setUpObserver() {
        orderViewModel.allOrder.observe(viewLifecycleOwner){
            binding.rvOrderList.adapter = OrderAdapter(
                orderViewModel, it ,this.requireContext())
        }
    }

    private fun setUpView() {
        binding.apply {
            rvOrderList.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setUpViewModel() {
        orderViewModel = ViewModelProvider(this)[CheckoutViewModel::class.java]
    }
}