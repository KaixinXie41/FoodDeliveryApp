package com.example.fooddeliveryapp.view.fragment.checkout.checkout

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.view.fragment.checkout.checkout.CheckoutDeliveryFragment.Companion.ADDRESS
import com.example.fooddeliveryapp.view.fragment.checkout.checkout.CheckoutDeliveryFragment.Companion.ADDRESS_TITLE
import com.example.fooddeliveryapp.view.fragment.checkout.checkout.CheckoutDeliveryFragment.Companion.Delivery_TYPE
import com.example.fooddeliveryapp.view.fragment.checkout.checkout.CheckoutPaymentFragment.Companion.PAYMENT
import com.example.fooddeliveryapp.databinding.FragmentCheckoutSummaryBinding
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.dao.CartDao
import com.example.fooddeliveryapp.model.local.dao.OrderDao
import com.example.fooddeliveryapp.model.local.entities.Order
import com.example.fooddeliveryapp.view.activity.MainActivity
import com.example.fooddeliveryapp.view.activity.foodtracking.GetCurrentDeliveryLocationActivity
import com.example.fooddeliveryapp.view.activity.supportchat.ui.SupportChatActivity
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.Account_Information
import com.example.fooddeliveryapp.view.fragment.checkout.cart.CartFragmentAdapter.Companion.TOTAL_PRICE
import com.example.fooddeliveryapp.view.fragment.checkout.order.OrderAdapter.Companion.ORDER_ID
import com.example.fooddeliveryapp.viewmodel.CheckoutViewModel
import java.text.SimpleDateFormat
import java.util.*

class CheckoutSummaryFragment : Fragment() {

    private lateinit var cartDao: CartDao
    private lateinit var orderDao: OrderDao
    private lateinit var checkoutViewModel: CheckoutViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var binding: FragmentCheckoutSummaryBinding
    private lateinit var appDatabase: AppDatabase

    private val CHANNEL_ID ="food_notification"
    private val notificationId = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutSummaryBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(this.requireContext())
        cartDao = appDatabase.getCartDao()
        orderDao = appDatabase.getOrderDao()
        sharedPreferences = this.requireActivity().getSharedPreferences(
            Account_Information,
            AppCompatActivity.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpViewModel()
        setUpObserver()
        initView()
        createNotificationChannel()
    }

    private fun initView() {
        val payment = sharedPreferences.getString(PAYMENT,"NONE")
        val title = sharedPreferences.getString(ADDRESS_TITLE,"NONE")
        val address = sharedPreferences.getString(ADDRESS,"NONE")
        val deliveryType = sharedPreferences.getString(Delivery_TYPE, "NONE")

        if(payment!=null){
            binding.txtPaymentInfo.text = payment
        }
        if(address!= null && title != null){
            binding.txtSummaryAddressTitle.text = title
            binding.txtSummaryAddress.text = address
        }
        if(deliveryType != null){
            binding.txtPickUpOption.text = deliveryType
        }

    }

    private fun setUpObserver() {
        checkoutViewModel.allCart.observe(viewLifecycleOwner) {
            binding.recyclerViewSummaryCart.adapter = CheckoutCartMealAdapter(
                checkoutViewModel, it, this.requireContext()
            )
            val total = sharedPreferences.getString(TOTAL_PRICE, "None")
            binding.txtSummaryTotalBillAmountValue.text = total.toString()
            val totalAmount = binding.txtSummaryTotalBillAmountValue.text.toString()
            val payment = sharedPreferences.getString(PAYMENT, "NONE")
            val title = sharedPreferences.getString(ADDRESS_TITLE, "NONE")
            val address = sharedPreferences.getString(ADDRESS, "NONE")
            val deliveryType = sharedPreferences.getString(Delivery_TYPE, "NONE")
            var orderId: Long
            binding.btnSummaryConfirmPlace.setOnClickListener {
                if (payment != null && address != null && title != null) {
                    val date = Calendar.getInstance().time
                    val formatter = SimpleDateFormat.getDateTimeInstance()
                    val orderDate = formatter.format(date)

                    orderId = checkoutViewModel.addOrder(
                        Order(
                            0,
                            address,
                            title,
                            totalAmount.toDouble(),
                            orderDate.toString(),
                            "out of delivery",
                            payment,
                            deliveryType.toString()
                        )
                    )
                    editor.putLong(ORDER_ID, orderId)
                    editor.apply()

                    /*
                    val cartItem: Cart = arguments?.getParcelable(CART)!!
                    val itemMealId = cartItem.mealId.toInt()
                    val itemCount = cartItem.count
                    val itemMealPrice = cartItem.mealPrice.toInt()
                    val itemMealName = cartItem.mealName
                    val itemMealCategory = cartItem.mealCategory
                    val item = Item(
                        0,
                        itemMealId,
                        itemCount,
                        itemMealPrice,
                        itemMealName,
                        orderId,
                        itemMealCategory
                    )
                    checkoutViewModel.addItem(item)

                     */
                    cartDao.delete()
                }
                sendNotification()
                val intent = Intent(this.requireActivity(), MainActivity::class.java)
                startActivity(intent)

                val timer = object : CountDownTimer(10000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {}

                    override fun onFinish() {
                        setAlarmDelivery()
                    }
                }
                timer.start()
            }

        }
    }

    private fun setUpViewModel() {
        checkoutViewModel = ViewModelProvider(this)[CheckoutViewModel::class.java]
    }

    private fun setUpView() {
        binding.apply {
            recyclerViewSummaryCart.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager : NotificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(){
        val intent = Intent(this.requireContext(), MainActivity::class.java)
        intent.putExtra(SupportChatActivity.ORDER_PAGE, true)
            intent.apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this.requireContext(), 0,intent, PendingIntent.FLAG_MUTABLE)

        val builder = NotificationCompat.Builder(this.requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_liquor_24)
            .setContentTitle("Food is preparing!")
            .setContentText("Hi! We got your order, Your food is preparing right now!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(this.requireContext())){
            notify(notificationId, builder.build())
        }
    }



    private fun setAlarmDelivery() {
        val calender: Calendar = Calendar.getInstance()
        calender.set(Calendar.MINUTE, 0)
        calender.set(Calendar.SECOND, 5)
        val thuReq: Long = Calendar.getInstance().timeInMillis + 1
        val reqReqCode = thuReq.toInt()
        if (calender.timeInMillis < System.currentTimeMillis()) {
            calender.add(Calendar.DAY_OF_YEAR, 1)
        }
        val intent = Intent(this.requireContext(), GetCurrentDeliveryLocationActivity::class.java)
        intent.putExtra(SupportChatActivity.ORDER_PAGE, true)
        intent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        val pendingIntent:PendingIntent = PendingIntent.getActivity(this.requireContext(), reqReqCode, intent, PendingIntent.FLAG_MUTABLE)
        val builder = NotificationCompat.Builder(this.requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_liquor_24)
            .setContentTitle("Food is delivering!")
            .setContentText("Hi! Your delicious food is on the way to you now!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(this.requireContext())){
            notify(notificationId, builder.build())
        }

    }

    companion object{
        const val BILL_TOTAL = "billTotal"
    }
}