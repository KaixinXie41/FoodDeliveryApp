package com.example.fooddeliveryapp.view.auth.registration.phone

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.model.local.entities.Phone

class PhoneAdapter (private val arrayList: ArrayList<Phone>): BaseAdapter() {

    lateinit var textCode: TextView
    lateinit var imgCountry: ImageView

    override fun getCount()=arrayList.size
    override fun getItem(p0: Int) = p0
    override fun getItemId(p0: Int)=p0.toLong()

    @SuppressLint("ViewHolder")

    override fun getView(position: Int, p1: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        textCode = view.findViewById(R.id.country_code)
        imgCountry = view.findViewById(R.id.countryFlag)

        arrayList[position].apply {
            textCode.text = areaCode
            imgCountry.setImageResource(CountryFlag)
        }
        return view
    }
}