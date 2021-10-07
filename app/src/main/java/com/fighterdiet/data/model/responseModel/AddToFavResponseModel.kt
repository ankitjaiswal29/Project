package com.fighterdiet.data.model.responseModel

data class AddToFavResponseModel(
    val recipe_id: String,
    val favourite_status: String,
    val user_id: Int
)