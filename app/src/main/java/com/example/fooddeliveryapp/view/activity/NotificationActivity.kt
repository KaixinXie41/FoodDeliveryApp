package com.example.fooddeliveryapp.view.activity

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.ActivityNotificationBinding
import com.example.fooddeliveryapp.view.activity.foodtracking.GetCurrentDeliveryLocationActivity

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onReceive(this)
    }

    private fun  onReceive(context: Context) {
        Log.d("this", "notify")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val intent = Intent(context, GetCurrentDeliveryLocationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val builder = NotificationCompat.Builder(context, "111")
                .setSmallIcon(R.drawable.ic_baseline_directions_car_24)
                .setContentTitle("Alarm is running")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)

            val notificationManagerCompat = NotificationManagerCompat.from(context)
            notificationManagerCompat.notify(123, builder.build())

        }

    }
}

