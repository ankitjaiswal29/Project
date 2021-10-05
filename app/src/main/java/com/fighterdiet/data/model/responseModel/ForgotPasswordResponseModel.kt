package com.fighterdiet.data.model.responseModel

data class ForgotPasswordResponseModel(
    val email: String,
    val otp: Int,
    val user_id: Int

)
