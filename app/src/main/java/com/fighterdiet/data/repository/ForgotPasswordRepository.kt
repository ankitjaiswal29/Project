package com.fighterdiet.data.repository

import com.fighterdiet.data.api.ApiService
import com.fighterdiet.data.model.requestModel.ForgotPasswordRequestModel

class ForgotPasswordRepository(private val apiService: ApiService) {
    suspend fun forgotpasswordApi(forgotPasswordRequestModel: ForgotPasswordRequestModel)=apiService.forgotpasswordApi(forgotPasswordRequestModel)
}