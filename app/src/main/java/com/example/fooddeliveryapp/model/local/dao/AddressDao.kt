package com.example.fooddeliveryapp.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fooddeliveryapp.model.local.entities.Address

@Dao
interface AddressDao {

    @Insert
    fun insert(address: Address):Long

    @Delete
    fun delete(address: Address)

    @Update
    fun update(address: Address)

    @Query("SELECT * FROM Address")
    fun getAllAddress():LiveData<List<Address>>

    @Query("SELECT * FROM Address WHERE addressId=:addressId")
    fun getAddressByAddressId(addressId:Long): Address

}