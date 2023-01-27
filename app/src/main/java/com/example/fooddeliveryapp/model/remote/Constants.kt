package com.example.fooddeliveryapp.model.remote

object Constants {
    const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    const val SEARCH_MEAL_BY_NAME = "search.php?"
    const val LIST_MEAL_BY_FIRST_LETTER = "search.php?f="
    const val SEARCH_MEAL_BY_ID = "lookup.php?"
    const val SEARCH_SINGLE_RANDOM_MEAL = "random.php"
    const val LIST_ALL_MEAL_CATEGORY = "categories.php"
    const val LIST_ALL_CATEGORIES = "list.php?c=list"
    const val LIST_ALL_AREA = "list.php?a=list"
    const val LIST_ALL_INGREDIENTS = "list.php?i=list"
    const val FILTER_BY_INGREDIENT = "filter.php?"
    const val FILTER_BY_CATEGORY = "filter.php?"
    const val FILTER_BY_AREA = "filter.php?"

    const val SEND_ID = "SEND_ID"
    const val RECEIVE_ID = "RECEIVE_ID"
    const val OPEN_ORDER_PAGE = "Opening your order page.."
    const val OPEN_CART_PAGE = "Opening your cart page"
    const val OPEN_RATING_PAGE = "Opening rating page"
    const val SPLASH_TIME1 :Long = 1000
    const val SPLASH_TIME2 :Long = 2500


}