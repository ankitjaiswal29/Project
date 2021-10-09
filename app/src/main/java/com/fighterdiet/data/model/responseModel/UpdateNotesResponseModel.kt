package com.fighterdiet.data.model.responseModel

data class UpdateNotesResponseModel(
    val user_id: Int,
    val recipe_id: Int,
    val description: String
)