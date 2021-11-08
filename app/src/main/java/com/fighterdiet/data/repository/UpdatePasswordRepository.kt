package com.fighterdiet.data.repository

import com.fighterdiet.data.api.ApiService
import com.fighterdiet.data.model.requestModel.ChangePasswordRequestModel
import com.fighterdiet.data.model.requestModel.ForgotPasswordRequestModel
import com.fighterdiet.data.model.requestModel.ResetPasswordRequestModel

class UpdatePasswordRepository(private val apiService: ApiService) {
    suspend fun resetPasswordApi(changePasswordRequestModel: ResetPasswordRequestModel)=apiService.resetPassword(changePasswordRequestModel)
}