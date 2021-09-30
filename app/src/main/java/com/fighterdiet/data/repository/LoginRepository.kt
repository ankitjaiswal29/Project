package com.fighterdiet.data.repository

import com.fighterdiet.data.api.ApiService
import com.fighterdiet.data.model.requestModel.LoginRequestModel
import com.fighterdiet.data.model.requestModel.RegisterRequestModel

class LoginRepository(private val apiService: ApiService) {
    suspend fun loginApi(loginRequestModel: LoginRequestModel) = apiService.loginApi(registerRequestModel = loginRequestModel)

}