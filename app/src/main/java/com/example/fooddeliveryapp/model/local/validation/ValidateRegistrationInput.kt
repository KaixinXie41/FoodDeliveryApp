package com.example.fooddeliveryapp.model.local.validation

object ValidateRegistrationInput {
    private val existingUsers = listOf("Jack","Chris","Kai")
    private val existingMobiles = listOf("2140090089","2140080078","2140090099")
    private val existingEmails = listOf("kxie0766@gmail.com", "kaixin@gmail.com","kaixin@qq.com")

    fun isValidRegistrationInput(
        email:String,
        password:String,
        mobile:String,
        username:String
    ):Boolean{
        if(username.isEmpty()||password.isEmpty()||email.isEmpty()||mobile.isEmpty()){
            return false
        }
        if(username in existingUsers){
            return false
        }
        if(password.count{it.isDigit()}<6){
            return false
        }
        if(mobile in existingMobiles){
            return false
        }
        if(email in existingEmails){
            return false
        }
        return true
    }
}