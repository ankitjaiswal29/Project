package com.fighterdiet.data.repository

import com.fighterdiet.data.api.ApiService
import com.fighterdiet.data.model.requestModel.ChangePasswordRequestModel
import com.fighterdiet.data.model.requestModel.ForgotPasswordRequestModel

class ChangePasswordRepository(private val apiService: ApiService) {
    suspend fun changePasswordApi(changePasswordRequestModel: ChangePasswordRequestModel)=apiService.changePassword(changePasswordRequestModel)
}