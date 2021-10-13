package com.fighterdiet.data.repository

import com.fighterdiet.data.api.ApiService
import com.fighterdiet.data.model.requestModel.CheckUserNameRequest
import com.fighterdiet.data.model.requestModel.RegisterRequestModel

class RegisterRepository(private val apiService: ApiService) {
    suspend fun registerApi(registerRequestModel: RegisterRequestModel) = apiService.registerApi(registerRequestModel = registerRequestModel)
    suspend fun checkUserName(model: CheckUserNameRequest) = apiService.checkUserName(model)
}