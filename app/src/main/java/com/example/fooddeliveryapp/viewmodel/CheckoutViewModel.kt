package com.example.fooddeliveryapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.model.local.database.AppDatabase
import com.example.fooddeliveryapp.model.local.entities.*
import com.example.fooddeliveryapp.model.local.repository.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class CheckoutViewModel(application: Application): AndroidViewModel(application) {
    private val appDatabase = AppDatabase.getInstance(application)
    private val orderRepository: OrderRepository = OrderRepository(application)
    private val cartRepository: CartRepository = CartRepository(application)
    private val addressRepository: AddressRepository = AddressRepository(application)
    private val restaurantRepository: RestaurantRepository = RestaurantRepository(application)
    private val favoriteRepository: FavoriteRepository = FavoriteRepository(application)



    val allOrder: LiveData<List<Order>> = orderRepository.allOrder
    val allRestaurant: LiveData<List<Restaurant>> = restaurantRepository.allRestaurant
    val allCart: LiveData<List<Cart>> = cartRepository.allCart
    val allAddress: LiveData<List<Address>> = addressRepository.allAddress
    val allFavorite: LiveData<List<Favorite>> = favoriteRepository.allFavorite
    val allItem:LiveData<List<Item>> = appDatabase.getItemDao().getAllItem()

    val id =MutableLiveData<Long>()
    val msg = MutableLiveData<String>()

    val items = appDatabase.getItemDao()
    //Items
    fun addItem(item: Item){
        viewModelScope.launch(IO){
            val itemId:Long = appDatabase.getItemDao().insert(item)
            id.postValue(itemId)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch(IO) {
            val deleteOrder = appDatabase.getItemDao().delete(item)
            if(deleteOrder > 0 ){
                msg.postValue("the order id: ${item.itemId} is deleted")
            }else {
                msg.postValue("Failed to delete order")
            }
        }
    }
    fun getItemByOrderId(orderId: Long){
        appDatabase.getItemDao().getItemByOrderId(orderId)
    }



    //Order
    fun addOrder(order: Order) :Long{
        var orderId: Long =0
        viewModelScope.launch(IO) {
            orderId = appDatabase.getOrderDao().insert(order)
            id.postValue(orderId)
        }
        return orderId
    }

    fun removeOrder(order: Order) {
        viewModelScope.launch(IO) {
            val deleteOrder = appDatabase.getOrderDao().delete(order)
            if(deleteOrder > 0 ){
                msg.postValue("the order id: ${order.orderId} is deleted")
            }else {
                msg.postValue("Failed to delete order")
            }
        }
    }

    fun getOrderByOrderId(orderId: Long) {
        orderRepository.getOrderByOrderId(orderId)
    }

    //Restaurant
    fun addRestaurant(restaurant: Restaurant) {
        restaurantRepository.insert(restaurant)
    }

    fun removeRestaurant(restaurant: Restaurant) {
        restaurantRepository.delete(restaurant)
    }

    fun updateRestaurant(restaurant: Restaurant) {
        restaurantRepository.update(restaurant)
    }

    fun getRestaurantByRestaurantId(resId: Long) {
        restaurantRepository.getRestaurantById(resId)
    }

    //Cart
    val totalAmount = MutableLiveData<Double>()
    var total = 0.0

    fun addCart(cart: Cart) {
        cartRepository.insert(cart)
    }

    fun removeCart(cart: Cart) {
        cartRepository.delete(cart)
    }

    fun updateCart(cart: Cart, isAdded: Boolean) {
        if (isAdded) {
            total += cart.totalPrice
        } else {
            total -= cart.totalPrice
        }
        cartRepository.update(cart)
        totalAmount.postValue(total)
    }

    fun getCartByCartId(cartId: Long) {
        cartRepository.getCartMealByCartId(cartId)
    }

    fun getCartByMealId(mealId: String) {
        cartRepository.getCartMealByMealId(mealId)
    }

    //Address

    fun addAddress(address: Address) {
        addressRepository.insert(address)
    }

    fun removeAddress(address: Address) {
        addressRepository.delete(address)
    }

    fun updateAddress(address: Address) {
        addressRepository.update(address)
    }

    fun getAddressByAddressId(addressId: Long) {
        addressRepository.getAddressByAddressId(addressId)
    }


    //Favorite

    fun addFavorite(favorite: Favorite) {
        favoriteRepository.insert(favorite)
    }

    fun deleteFavorite(favorite: Favorite) {
        favoriteRepository.delete(favorite)
    }

    fun updateFavorite(favorite: Favorite) {
        favoriteRepository.update(favorite)
    }

    fun getFavoriteByUserId(userId:Int) {
        favoriteRepository.getFavoriteByUserId(userId)
    }
}
