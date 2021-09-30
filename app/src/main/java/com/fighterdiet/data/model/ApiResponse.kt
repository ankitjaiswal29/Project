package com.fighterdiet.data.model

class ApiResponse<T>(val status: Boolean, val message: String, val code :Int) {
    var data: T? = null
}