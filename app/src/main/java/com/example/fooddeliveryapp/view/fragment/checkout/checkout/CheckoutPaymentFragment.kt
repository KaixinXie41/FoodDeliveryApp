package com.example.fooddeliveryapp.view.fragment.checkout.checkout

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentCheckoutPaymentBinding
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.dao.CartDao
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.Account_Information

class CheckoutPaymentFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentCheckoutPaymentBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var cartDao: CartDao
    private lateinit var payment:String
    private lateinit var appDatabase: AppDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutPaymentBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(this.requireContext())
        cartDao = appDatabase.getCartDao()
        sharedPreferences = this.requireActivity().getSharedPreferences(
            Account_Information,
            AppCompatActivity.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
        payment = "cod"
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }

    private fun setListener() {
        binding.btnPay.setOnClickListener(this)
        binding.btnCodPay.setOnClickListener(this)
        binding.btnGiftPay.setOnClickListener(this)
        binding.btnPaypalPay.setOnClickListener(this)
        binding.rtnCod.setOnCheckedChangeListener  { buttonView, isChecked ->
            if(isChecked){
                binding.cardViewCashOnDeliveryInfo.visibility = View.VISIBLE
                binding.rtnDcCc.isChecked = false
                binding.rtnPaypal.isChecked = false
                binding.rtnGiftCard.isChecked = false
                payment = binding.rtnCod.text.toString()
                editor.putString(PAYMENT,payment)
                editor.apply()
            }else{
                binding.cardViewCashOnDeliveryInfo.visibility = View.GONE
            }
        }
        binding.rtnPaypal.setOnCheckedChangeListener  { buttonView, isChecked ->
            if(isChecked){
                binding.cardViewPaypalQrCode.visibility = View.VISIBLE
                binding.rtnDcCc.isChecked = false
                binding.rtnCod.isChecked = false
                binding.rtnGiftCard.isChecked = false
                payment = binding.rtnPaypal.text.toString()
                editor.putString(PAYMENT,payment)
                editor.apply()
            }else{
                binding.cardViewPaypalQrCode.visibility = View.GONE
            }
        }
        binding.rtnGiftCard.setOnCheckedChangeListener  { buttonView, isChecked ->
            if(isChecked){
                binding.cardViewGiftCardInformation.visibility = View.VISIBLE
                binding.btnGiftPay.visibility = View.VISIBLE
                binding.rtnDcCc.isChecked = false
                binding.rtnPaypal.isChecked = false
                binding.rtnCod.isChecked = false
                payment = binding.rtnGiftCard.text.toString()
                editor.putString(PAYMENT,payment)
                editor.apply()
            }else{
                binding.cardViewGiftCardInformation.visibility = View.GONE
                binding.btnGiftPay.visibility = View.GONE

            }
        }
        binding.rtnDcCc.setOnCheckedChangeListener  { buttonView, isChecked ->
            if(isChecked){
                binding.cardViewCreditCardInformation.visibility = View.VISIBLE
                binding.btnPay.visibility = View.VISIBLE
                binding.rtnCod.isChecked = false
                binding.rtnPaypal.isChecked = false
                binding.rtnGiftCard.isChecked = false
                payment = binding.rtnDcCc.text.toString()
                editor.putString(PAYMENT,payment)
                editor.apply()
            }else{
                binding.cardViewCreditCardInformation.visibility = View.GONE
                binding.btnPay.visibility = View.GONE

            }
        }
    }

    companion object{
        const val PAYMENT ="payment"
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_cod_pay,
            R.id.btn_pay,
            R.id.btn_paypal_pay,
            R.id.btn_gift_pay -> {
                (this.parentFragment as CheckoutMealFragment).nextPager()
            }
        }
    }

}