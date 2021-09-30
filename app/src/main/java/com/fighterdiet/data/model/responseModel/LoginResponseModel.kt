package com.fighterdiet.data.model.responseModel

data class LoginResponseModel(
    val code: Int,
    val `data`: LoginData,
    val message: String,
    val status: Boolean
)
data class LoginData(
    val active: Int,
    val created: String,
    val email: String,
    val first_name: String,
    val is_subscribed: String,
    val last_name: String,
    val token: String,
    val user_id: Int,
    val user_name: String
)