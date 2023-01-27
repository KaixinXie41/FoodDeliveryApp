package com.example.fooddeliveryapp.view.fragment.youtube

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import com.example.fooddeliveryapp.databinding.FragmentYoutubeBinding
import com.example.fooddeliveryapp.di.AppModule
import com.example.fooddeliveryapp.model.remote.ApiService
import com.example.fooddeliveryapp.view.fragment.meal.mealdetails.MealDetailsAdapter.Companion.YOUTUBE_URL
import com.example.fooddeliveryapp.viewmodel.CategoryViewModel
import com.example.fooddeliveryapp.viewmodel.CategoryViewModelProvider
import javax.inject.Inject
import com.example.fooddeliveryapp.model.local.data.category.CategoryRepository as CategoryRepository

class YoutubeFragment : Fragment() {

    @Inject
    lateinit var appModule: AppModule
    private lateinit var binding : FragmentYoutubeBinding
    private lateinit var mealViewModel : CategoryViewModel
    private lateinit var websitePage:WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentYoutubeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appModule = AppModule
        setUpViewModel()
        setUpWebsite()


    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebsite() {
        websitePage = binding.webPage
        websitePage.apply {
            webViewClient = WebViewClient()
            websitePage.loadUrl(arguments?.getString(YOUTUBE_URL)?: "")
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true)
        }
    }

    private fun setUpViewModel() {
        val repository = CategoryRepository(appModule.providesRetrofit().create(ApiService::class.java))
        val factory = CategoryViewModelProvider(repository)
        mealViewModel = ViewModelProvider(this,factory)[CategoryViewModel::class.java]
    }
}