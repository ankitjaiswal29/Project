package com.fighterdiet.data.model.responseModel

data class Data(
    val email: String,
    val first_name: String,
    val last_name: String,
    val otp: Int,
    val token: String,
    val user_id: Int,
    val user_name: String
)