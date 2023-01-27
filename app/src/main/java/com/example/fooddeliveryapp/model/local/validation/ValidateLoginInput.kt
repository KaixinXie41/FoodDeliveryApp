package com.example.fooddeliveryapp.model.local.validation

object ValidateLoginInput {
    private val existingUser1 = listOf("kaixin@yahoo.com", "12345678")
    private val existingUser2 = listOf("kaixin@gmail.com", "87654321")


    fun isValidLoginInput(
        email:String,
        password:String
    ):Boolean{
        if(password.isEmpty()||email.isEmpty()){
            return false
        }
        if(password.count{it.isDigit()}<6){
            return false
        }
        if(email !in existingUser1 && email !in existingUser2){
            return false
        }
        if(email in existingUser1 && password in existingUser2){
            return false
        }
        return true
    }
}