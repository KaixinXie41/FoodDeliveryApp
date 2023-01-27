package com.example.fooddeliveryapp.view.fragment.meal.ingredient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.databinding.FragmentIngredientsBinding
import com.example.fooddeliveryapp.di.AppModule
import com.example.fooddeliveryapp.model.local.data.category.CategoryRepository
import com.example.fooddeliveryapp.model.remote.ApiService
import com.example.fooddeliveryapp.view.fragment.search.SearchFragment
import com.example.fooddeliveryapp.viewmodel.CategoryViewModel
import com.example.fooddeliveryapp.viewmodel.CategoryViewModelProvider
import javax.inject.Inject

class IngredientsFragment : Fragment() {
    @Inject
    lateinit var appModule : AppModule
    private lateinit var binding: FragmentIngredientsBinding
    private lateinit var mealViewModel : CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appModule = AppModule
        setUpView()
        setUpViewModel()
        setUpObserver()
    }


    private fun setUpViewModel() {
        val repository = CategoryRepository(appModule.providesRetrofit().create(ApiService::class.java))
        val factory = CategoryViewModelProvider(repository)
        mealViewModel = ViewModelProvider(this,factory)[CategoryViewModel::class.java]
        mealViewModel.searchByMealIngredient(arguments?.getString(SearchFragment.SEARCH_TEXT) ?: "" )
    }

    private fun setUpView(){
        binding.apply {
            rvIngredientMealList.layoutManager = LinearLayoutManager(context)
        }
    }
    private fun setUpObserver(){
        mealViewModel.mealLiveData.observe(viewLifecycleOwner){
            binding.rvIngredientMealList.adapter = IngredientAdapter(
                mealViewModel, it.meals ,this.requireContext())
        }
    }
}