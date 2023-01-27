package com.example.fooddeliveryapp.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.model.local.data.category.CategoryRepository
import com.example.fooddeliveryapp.model.local.data.category.CategoryResponse
import com.example.fooddeliveryapp.model.local.data.meal.MealResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(internal val repository: CategoryRepository): ViewModel() {

    val categoryLiveData = MutableLiveData<CategoryResponse>()
    val mealLiveData = MutableLiveData<MealResponse>()
    val areaLiveData = MutableLiveData<MealResponse>()
    val booleanLiveData = ObservableBoolean(false)
    private lateinit var disposable : Disposable
    val compositeDisposable = CompositeDisposable()
    val processing = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun getAllCategory(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                processing.postValue(true)
                val response: Response<CategoryResponse> = repository.getAllCategory()
                processing.postValue(false)
                val result = response.body()

                result?.let {
                    categoryLiveData.postValue(result)
                }?:run{
                    error.postValue("Empty result from server.")
                    return@launch
                }
            }catch (e:Exception){
                processing.postValue(false)
                error.postValue(e.toString())
            }

        }
    }

    fun searchByMealName(message:String){
        disposable = repository.searchByMealName(message)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{
                booleanLiveData.set(true)
            }
            .doOnTerminate {
                booleanLiveData.set(false)
            }
            .subscribe( {response ->
                    mealLiveData.value = response

            },{t->
                t.printStackTrace()
            })
        compositeDisposable.add(disposable)

    }
    fun searchByMealArea(message:String){
        disposable = repository.searchByArea(message)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{
                booleanLiveData.set(true)
            }
            .doOnTerminate {
                booleanLiveData.set(false)
            }
            .subscribe( {response ->
                areaLiveData.value = response

            },{t->
                t.printStackTrace()
            })
        compositeDisposable.add(disposable)

    }
    fun searchByMealIngredient(message:String){

        disposable = repository.searchByIngredient(message)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{
                booleanLiveData.set(true)
            }
            .doOnTerminate {
                booleanLiveData.set(false)
            }
            .subscribe( {response ->
                mealLiveData.value = response

            },{t->
                t.printStackTrace()
            })
        compositeDisposable.add(disposable)
    }
    fun searchByMealId(message:String){

        disposable = repository.searchByMealId(message)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{
                booleanLiveData.set(true)
            }
            .doOnTerminate {
                booleanLiveData.set(false)
            }
            .subscribe( {response ->
                mealLiveData.postValue(response)

            },{t->
                t.printStackTrace()
            })
        compositeDisposable.add(disposable)
    }

    fun searchByMealCategory(message: String) {
        disposable = repository.searchByMealCategory(message)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{
                booleanLiveData.set(true)
            }
            .doOnTerminate {
                booleanLiveData.set(false)
            }
            .subscribe( {response ->
                mealLiveData.postValue(response)

            },{t->
                t.printStackTrace()
            })
        compositeDisposable.add(disposable)
    }

}