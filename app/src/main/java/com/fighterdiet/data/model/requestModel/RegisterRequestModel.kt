package com.fighterdiet.data.model.requestModel

class RegisterRequestModel(
    val user_name : String? ="",
    val first_name : String? ="",
    val last_name : String? ="",
    val email : String? ="",
    val password : String? ="",
    val phone_number : String? ="",
    val  confirm_password : String? =""
)