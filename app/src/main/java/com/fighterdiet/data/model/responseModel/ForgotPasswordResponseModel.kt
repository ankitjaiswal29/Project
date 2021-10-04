package com.fighterdiet.data.model.responseModel

data class ForgotPasswordResponseModel(
    val code: Int,
    val `data`: ForgotPasswordData,
    val message: String,
    val status: Boolean
)
data class ForgotPasswordData(
    val email: String,
    val otp: Int,
    val user_id: Int
)