package com.fighterdiet.data.model.responseModel

data class VerifyOtpResponseModel(
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