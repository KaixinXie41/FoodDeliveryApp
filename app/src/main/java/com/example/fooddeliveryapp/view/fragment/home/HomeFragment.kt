package com.example.fooddeliveryapp.view.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentHomeBinding
import com.example.fooddeliveryapp.di.AppModule
import com.example.fooddeliveryapp.model.local.data.Offer
import com.example.fooddeliveryapp.model.local.data.category.CategoryRepository
import com.example.fooddeliveryapp.model.remote.ApiService
import com.example.fooddeliveryapp.viewmodel.CategoryViewModel
import com.example.fooddeliveryapp.viewmodel.CategoryViewModelProvider
import javax.inject.Inject

class HomeFragment : Fragment() {
    @Inject
    lateinit var appModule :AppModule
    private lateinit var binding: FragmentHomeBinding
    private lateinit var offerAdapter: HomePageOfferAdapter
    private val offer = ArrayList<Offer>()
    private lateinit var categoryViewModel : CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appModule = AppModule
        setUpView()
        setUpViewModel()
        setUpObserver()
        initView()

    }


    private fun setUpObserver() {
        categoryViewModel.categoryLiveData.observe(viewLifecycleOwner){
            binding.rvCategory.adapter = HomePageCategoryAdapter(
                categoryViewModel,  it.categories ,this)
        }
    }

    private fun setUpViewModel() {

        val repository = CategoryRepository(appModule.providesRetrofit().create(ApiService::class.java))
        val factory = CategoryViewModelProvider(repository)
        categoryViewModel = ViewModelProvider(this, factory)[CategoryViewModel::class.java]
        categoryViewModel.getAllCategory()
        binding.viewModel = categoryViewModel
    }

    private fun setUpView() {
        binding.apply {
            rvCategory.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
    }

    private fun initView() {
        offer.apply {
            add(
                Offer(
                    "FirePot Weekend!",
                    "Are you ready for the weekend party?! Let's get 25% off of party meal!",
                    "Sept.22 to Sept.24",
                    R.drawable.partymeal)
            )
            add(
                Offer(
                    "Crazy Thursday!",
                    "Crazy Thursday is coming back!! Let's get $10 OFF of your First Order!",
                    "Oct.12 to Oct.19",
                    R.drawable.fried_chicken)
            )
            add(
                Offer(
                    "FirePot Weekend!",
                    "Are you ready for the weekend party?! Let's get 25% off of party meal!",
                    "Sept.22 to Sept.24",
                    R.drawable.partymeal)
            )
            add(
                Offer(
                    "Crazy Thursday!",
                    "Crazy Thursday is coming back!! Let's get $10 OFF of your First Order!",
                    "Oct.12 to Oct.19",
                    R.drawable.fried_chicken)
            )
        }
        offerAdapter = HomePageOfferAdapter(offer)
        binding.rvOffer.layoutManager= LinearLayoutManager(context, RecyclerView.HORIZONTAL,false)
        binding.rvOffer.adapter = offerAdapter
    }

}