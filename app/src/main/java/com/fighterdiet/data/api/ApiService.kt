package com.fighterdiet.data.api

import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.requestModel.ForgotPasswordRequestModel
import com.fighterdiet.data.model.requestModel.LoginRequestModel
import com.fighterdiet.data.model.requestModel.RegisterRequestModel
import com.fighterdiet.data.model.requestModel.VerifyOtpRequestModel
import com.fighterdiet.data.model.responseModel.*

import retrofit2.http.*

interface ApiService {

    @POST("register")
    suspend fun registerApi(@Body registerRequestModel: RegisterRequestModel): ApiResponse<RegistrationResponseModel>


    @POST("login")
    suspend fun loginApi(@Body registerRequestModel: LoginRequestModel): ApiResponse<LoginResponseModel>

    @POST("forgot-password")
    suspend fun forgotpasswordApi(@Body forgotPasswordRequestModel: ForgotPasswordRequestModel): ApiResponse<ForgotPasswordResponseModel>

    @GET("recipe-list")
    suspend fun getRecipeListApi(
        @Query("offset") offset:Int,
        @Query("limit") limit:Int,
        @Query("search") search:String
    ): ApiResponse<RecipeListResponseModel>

    @GET("recipe-list")
    suspend fun getFavouriteListApi(
        @Query("offset") offset:Int,
        @Query("limit") limit:Int,
        @Query("search") search:String
    ): ApiResponse<FavouriteListResponseModel>

    @GET("recipe-trending")
    suspend fun getTrendingListApi(
        @Query("limit") limit:Int,
    ): ApiResponse<TrendingListResponseModel>

    @GET("faqs-list")
    suspend fun getFaqListApi(
        @Query("limit") limit:Int, @Query("offset") offset:Int
    ): ApiResponse<FaqListResponseModel>

    @POST("verify-otp")
    suspend fun verifyotpApi(@Body verifyOtpRequestModel: VerifyOtpRequestModel):ApiResponse<VerifyOtpResponseModel>
    @POST("resend-otp")
    suspend fun resendOtpApi(@Body forgotPasswordRequestModel: ForgotPasswordRequestModel): ApiResponse<ForgotPasswordResponseModel>

}