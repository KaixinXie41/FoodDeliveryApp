package com.example.fooddeliveryapp.view.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.view.fragment.meal.category.CategoryListFragment
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.ItemViewCategoryBinding
import com.example.fooddeliveryapp.model.local.data.category.Category


class HomePageCategoryAdapter(
    val viewModel: ViewModel,
    val categoryList: List<Category>,
    val context: HomeFragment,
)
    :RecyclerView.Adapter<HomePageCategoryAdapter.CategoryHolder>(){
    private lateinit var binding: ItemViewCategoryBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ItemViewCategoryBinding.inflate(layoutInflater, parent, false)
        return CategoryHolder(binding.root)
    }

    override fun getItemCount() = categoryList.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.apply {
            val category = categoryList[position]
            categoryName.text = category.strCategory
            categoryDesc.text = category.strCategoryDescription
            Glide.with(context)
                .load(category.strCategoryThumb)
                .into(imgCategory)
            itemView.setOnClickListener { p0 ->
                val activity = p0!!.context as AppCompatActivity
                val categoryListFragment = CategoryListFragment()
                val bundle = Bundle()
                val mealCategory = category.strCategory
                bundle.putString(MEAL_CATEGORY, mealCategory)
                categoryListFragment.arguments = bundle
                activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout_main, categoryListFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
    inner class CategoryHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val imgCategory: ImageView = binding.imgCategory
        val categoryName: TextView = binding.txtCategoryName
        val categoryDesc: TextView = binding.txtCategoryDesc
    }

    companion object{
        const val MEAL_CATEGORY = "Category"
    }

}