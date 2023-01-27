package com.example.fooddeliveryapp.view.fragment.search

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.fooddeliveryapp.view.fragment.meal.meallist.MealListFragment
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentSearchBinding
import com.example.fooddeliveryapp.di.AppModule
import com.example.fooddeliveryapp.model.local.data.category.CategoryRepository
import com.example.fooddeliveryapp.model.local.data.meal.MealResponse
import com.example.fooddeliveryapp.model.remote.ApiService
import com.example.fooddeliveryapp.viewmodel.CategoryViewModel
import com.example.fooddeliveryapp.viewmodel.CategoryViewModelProvider
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var appModule : AppModule
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: CategoryViewModel
    private lateinit var mealLivedata:MutableLiveData<MealResponse>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
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
                searchViewModel.searchByMealName(searchText)
                val mealListFragment = MealListFragment()
                mealListFragment.arguments = bundle
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout_main,mealListFragment)
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