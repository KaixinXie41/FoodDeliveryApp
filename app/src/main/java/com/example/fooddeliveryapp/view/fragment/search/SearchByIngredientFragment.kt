package com.example.fooddeliveryapp.view.fragment.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentSearchByIngredientBinding
import com.example.fooddeliveryapp.di.AppModule
import com.example.fooddeliveryapp.model.local.data.category.CategoryRepository
import com.example.fooddeliveryapp.model.remote.ApiService
import com.example.fooddeliveryapp.view.fragment.meal.ingredient.IngredientsFragment
import com.example.fooddeliveryapp.viewmodel.CategoryViewModel
import com.example.fooddeliveryapp.viewmodel.CategoryViewModelProvider
import javax.inject.Inject

class SearchByIngredientFragment : Fragment() {

    @Inject
    lateinit var appModule: AppModule
    private lateinit var binding: FragmentSearchByIngredientBinding
    private lateinit var searchViewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchByIngredientBinding.inflate(inflater, container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appModule = AppModule
        setUpViewModel()
        setUpObserver()
        initView()
    }

    private fun initView() {
        binding.apply {
            imgBtnSearch.setOnClickListener{
                val searchText = edtSearch.text.toString()
                val bundle = Bundle()
                bundle.putString(SEARCH_TEXT, searchText)
                searchViewModel.searchByMealIngredient(searchText)
                val ingredientsFragment = IngredientsFragment()
                ingredientsFragment.arguments = bundle
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout_main,ingredientsFragment)
                    .commit()
            }
        }
    }

    private fun setUpObserver() {
        searchViewModel.mealLiveData.observe(viewLifecycleOwner){
            binding.edtSearch.text.toString()
        }
    }

    private fun setUpViewModel() {
        val repository = CategoryRepository(appModule.providesRetrofit().create(ApiService::class.java))
        val factory = CategoryViewModelProvider(repository)
        searchViewModel = ViewModelProvider(this,factory)[CategoryViewModel::class.java]
    }

    companion object{
        const val SEARCH_TEXT = "searchText"
    }
}