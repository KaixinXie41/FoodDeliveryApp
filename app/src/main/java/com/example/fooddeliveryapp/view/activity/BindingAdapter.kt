package com.example.fooddeliveryapp.view.activity

import android.view.View
import androidx.databinding.BindingAdapter

class BindingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("setVisibility")
        fun setVisibility(view: View, boolean: Boolean) {
            if (boolean) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.GONE
            }
        }
    }
}