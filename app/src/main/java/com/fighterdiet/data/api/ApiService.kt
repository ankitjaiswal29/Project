package com.fighterdiet.data.api

import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.requestModel.ForgotPasswordRequestModel
import com.fighterdiet.data.model.requestModel.LoginRequestModel
import com.fighterdiet.data.model.requestModel.RegisterRequestModel
import com.fighterdiet.data.model.requestModel.VerifyOtpRequestModel
import com.fighterdiet.data.model.responseModel.ForgotPasswordResponseModel
import com.fighterdiet.data.model.responseModel.LoginResponseModel

import com.fighterdiet.data.model.responseModel.RegistrationResponseModel
import com.fighterdiet.data.model.responseModel.VerifyOtpResponseModel
import retrofit2.http.*

interface ApiService {

    @POST("register")
    suspend fun registerApi(@Body registerRequestModel: RegisterRequestModel): ApiResponse<RegistrationResponseModel>


    @POST("login")
    suspend fun loginApi(@Body registerRequestModel: LoginRequestModel): ApiResponse<LoginResponseModel>

    @POST("forgot-password")
    suspend fun forgotpasswordApi(@Body forgotPasswordRequestModel: ForgotPasswordRequestModel): ApiResponse<ForgotPasswordResponseModel>

    @POST("verify-otp")
    suspend fun verifyotpApi(@Body verifyOtpRequestModel: VerifyOtpRequestModel):ApiResponse<VerifyOtpResponseModel>
}