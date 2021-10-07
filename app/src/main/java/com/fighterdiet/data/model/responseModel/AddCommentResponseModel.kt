package com.fighterdiet.data.model.responseModel

data class AddCommentResponseModel(
    val comments: String,
    val created_at: String,
    val id: Int,
    val receipe_id: String,
    val updated_at: String,
    val user_id: Int
)