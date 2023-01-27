package com.example.fooddeliveryapp.model.local.validation

import java.util.regex.Pattern

object ValidateProfileInput {
    private val existingEmails = listOf("kaixin@yahoo.com", "kaixin@gmail.com","kaixin@qq.com")
    private val existingMobiles = listOf("2140090089","2140080078","2140090099")
    private val existingUsers = listOf("Jack","Chris","Mark")
    private const val originalUserName = "Kai"
    private const val originalUserEmails = "xkx@gmail.com"
    private const val originalUserMobile = "2140091100"
    private val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun isValidProfileInput(
        email:String,
        mobile:String,
        username:String
    ):Boolean{
        if(username.isEmpty()||email.isEmpty()||mobile.isEmpty()){
            return false
        }

        if(username != originalUserName && username in existingUsers){
            return false
        }

        if(mobile != originalUserMobile && mobile in existingMobiles ){
            return false
        }

        if(mobile.count{ it.isDigit() }<10){
            return false
        }

        if(email != originalUserEmails && email in existingEmails){
            return false
        }

        if(!EMAIL_ADDRESS_PATTERN.matcher(email).matches()){
            return false
        }
        return true
    }
}