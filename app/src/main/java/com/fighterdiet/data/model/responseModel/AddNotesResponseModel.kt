package com.fighterdiet.data.model.responseModel

data class AddNotesResponseModel(
    val recipe_id: String,
    val user_id: Int,
    val description: String
)