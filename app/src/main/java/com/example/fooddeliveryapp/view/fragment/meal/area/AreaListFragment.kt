package com.example.fooddeliveryapp.view.fragment.meal.area

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.databinding.FragmentAreaListBinding
import com.example.fooddeliveryapp.di.AppModule
import com.example.fooddeliveryapp.model.local.data.category.CategoryRepository
import com.example.fooddeliveryapp.model.remote.ApiService
import com.example.fooddeliveryapp.view.fragment.search.SearchByAreaFragment
import com.example.fooddeliveryapp.viewmodel.CategoryViewModel
import com.example.fooddeliveryapp.viewmodel.CategoryViewModelProvider
import javax.inject.Inject

class AreaListFragment : Fragment() {
    @Inject
    lateinit var appModule : AppModule
    private lateinit var binding: FragmentAreaListBinding
    private lateinit var mealViewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAreaListBinding.inflate(inflater, container, false)
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
        mealViewModel.searchByMealArea(arguments?.getString(SearchByAreaFragment.SEARCH_TEXT) ?: "")
    }

    private fun setUpView() {
        binding.apply {
            rvAreaMealList.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setUpObserver() {
        mealViewModel.areaLiveData.observe(viewLifecycleOwner) {
            binding.rvAreaMealList.adapter = AreaListAdapter(
                mealViewModel, it.meals, this.requireContext())
        }
    }
}




