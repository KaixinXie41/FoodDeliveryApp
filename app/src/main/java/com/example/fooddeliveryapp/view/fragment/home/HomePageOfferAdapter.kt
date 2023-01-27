package com.example.fooddeliveryapp.view.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.databinding.ItemViewOfferDiscountsBinding
import com.example.fooddeliveryapp.model.local.data.Offer

class HomePageOfferAdapter(
    private val offer: List<Offer>)
    :RecyclerView.Adapter<HomePageOfferAdapter.OfferViewHolder>() {

    private lateinit var binding: ItemViewOfferDiscountsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ItemViewOfferDiscountsBinding.inflate(layoutInflater, parent, false)
        return OfferViewHolder(binding.root)
    }

    override fun getItemCount() = offer.size

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind(offer[position])
    }

    inner class OfferViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(offer: Offer) {
            offer.apply {
                binding.apply {
                    imgOffer.setImageResource(image)
                    txtOfferName.text = name
                    txtOfferDetails.text = details
                    txtOfferTimeRange.text = timeRange
                }

            }
        }
    }
}