package com.example.fooddeliveryapp.model.local.utils

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.example.fooddeliveryapp.view.auth.login.LoginActivity

class Utils {
    companion object{
        fun showDialog(context:Context, title:String, message:String,toastMessage:String){
            val builder = AlertDialog.Builder(context)
            builder.apply {
                setTitle(title)
                setMessage(message)
                setPositiveButton("Confirm"){_,_ ->
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)

                }
                setNegativeButton("Cancel"){_,_ ->
                }
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }
}