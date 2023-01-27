package com.example.fooddeliveryapp.view.activity.supportchat.utils

import com.example.fooddeliveryapp.model.remote.Constants.OPEN_CART_PAGE
import com.example.fooddeliveryapp.model.remote.Constants.OPEN_ORDER_PAGE
import com.example.fooddeliveryapp.model.remote.Constants.OPEN_RATING_PAGE

object BotResponse {

    fun basicResponses(_message: String): String {
        val random = (0..2).random()
        val message = _message.lowercase()

        return when {

            //Hello
            message.contains("hello") -> {
                when (random) {
                    0 -> "Hello there! How may I help you?"
                    1 -> "Hi this is your Support Robot, How may I help you?"
                    2 -> "Hi there! How may I help you today?"
                    else -> "error"
                }
            }

            //How are you
            message.contains("how are you") -> {
                when (random) {
                    0 -> "I am doing fine, thanks for asking! How may I help you?"
                    1 -> "So far so good! How may I help you?"
                    2 -> "Pretty good! How may I help you today?"
                    else -> "error"
                }
            }

            message.contains("order") && message.contains("status") ->{
                OPEN_ORDER_PAGE
            }

            message.contains("cart")  ->{
                OPEN_CART_PAGE
            }

            message.contains("rating") || message.contains("reviews") ->{
                OPEN_RATING_PAGE
            }

            //Error
            else -> {
                when (random) {
                    0 -> "Sorry I don't get it?"
                    1 -> "Sorry I don't understand"
                    2 -> "Try asking me something different please~"
                    else -> "error"
                }
            }
        }
    }
}