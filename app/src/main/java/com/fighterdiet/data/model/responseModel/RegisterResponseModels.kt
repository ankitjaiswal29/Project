package com.fighterdiet.data.model.responseModel

data class RegisterResponseModels(
    val code: Int,
    val `data`: Data,
    val message: String,
    val status: Boolean
)
