package com.fighterdiet.data.api

import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.requestModel.*
import com.fighterdiet.data.model.responseModel.*
import com.fighterdiet.data.model.responseModel.AddCommentResponseModel
import org.json.JSONObject

import retrofit2.http.*

interface ApiService {

    @POST("register")
    suspend fun registerApi(@Body registerRequestModel: RegisterRequestModel): ApiResponse<RegistrationResponseModel>

    @POST("login")
    suspend fun loginApi(@Body registerRequestModel: LoginRequestModel): ApiResponse<LoginResponseModel>

    @POST("forgot-password")
    suspend fun forgotPasswordApi(@Body forgotPasswordRequestModel: ForgotPasswordRequestModel): ApiResponse<ForgotPasswordResponseModel>

    @GET("recipe-list")
    suspend fun getRecipeListApi(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("search") search: String,
        @QueryMap selectedDietaryMap: HashMap<String, Int>,
        @QueryMap selectedVolumeMap: HashMap<String, Int>,
        @QueryMap selectedMealMap: HashMap<String, Int>
        ): ApiResponse<RecipeListResponseModel>

    @GET("favourite-list")
    suspend fun getFavouriteListApi(
        @Query("offset") offset:Int,
        @Query("limit") limit:Int
    ): ApiResponse<FavouriteListResponseModel>

    @GET("recipe-trending")
    suspend fun getTrendingListApi(
        @Query("offset") offset:Int,
        @Query("limit") limit:Int,
    ): ApiResponse<TrendingListResponseModel>

    @GET("faqs-list")
    suspend fun getFaqListApi(
        @Query("limit") limit:Int, @Query("offset") offset:Int
    ): ApiResponse<FaqListResponseModel>

    @POST("logout")
    suspend fun logOutApi(@Body logoutRequestModel: LogoutRequestModel):ApiResponse<Any>

    @GET("about_us")
    suspend fun getaboutApi():ApiResponse<AboutPaulinNordinResponseModel>

    @POST("verify-otp")
    suspend fun verifyotpApi(@Body verifyOtpRequestModel: VerifyOtpRequestModel):ApiResponse<VerifyOtpResponseModel>

    @POST("recipe-content")
    suspend fun getRecipeContentApi(@Body recipeContentRequestModel: RecipeContentRequestModel):ApiResponse<RecipeContentResponseModel>

    @POST("resend-otp")
    suspend fun resendOtpApi(@Body forgotPasswordRequestModel: ForgotPasswordRequestModel): ApiResponse<ForgotPasswordResponseModel>

    @POST("change-password")
    suspend fun changePassword(@Body changePasswordRequestModel: ChangePasswordRequestModel): ApiResponse<Any>

    @PUT("reset-password")
    suspend fun resetPassword(@Body changePasswordRequestModel: ResetPasswordRequestModel): ApiResponse<Any>

    @POST("check-username")
    suspend fun checkUserName(@Body model: CheckUserNameRequest): ApiResponse<CheckUserNameResponseModel>

    @POST("comment-add")
    suspend fun addRecipeComment(@Body addCommentRequestModel: AddCommentRequestModel): ApiResponse<AddCommentResponseModel>

    @POST("favourite-add")
    suspend fun getRecipeToFavApi(@Body model: AddToFavRequestModel): ApiResponse<AddToFavResponseModel>

    @POST("recipe-note-add")
    suspend fun addNotesApi(@Body model: AddNotesRequestModel): ApiResponse<AddNotesResponseModel>

    @POST("recipe-note-update")
    suspend fun updateNotesApi(@Body model: UpdateNotesRequestModel): ApiResponse<UpdateNotesResponseModel>

    @POST("comment-list")
    suspend fun getCommentListApi(@Body model: CommentListRequestModel): ApiResponse<CommentListResponseModel>

    @DELETE("comment-delete/{id}")
    suspend fun deleteCommentApi(@Path("id") id: Int): ApiResponse<JSONObject>

    @POST("report-spam-comment")
    suspend fun reportSpamCommentApi(@Body model: SpamCommentRequestModel): ApiResponse<SpamCommentResponseModel>

    @GET("get-allergy")
    suspend fun getAllergyApi(): ApiResponse<GetDietaryResponseModel>

    @GET("get-volume")
    suspend fun getVolumeApi(): ApiResponse<GetVolumeResponseModel>

    @GET("get-meal")
    suspend fun getMealApi(): ApiResponse<GetMealResponseModel>

    @POST("androidPayment")
    suspend fun callSubscriptionApi(@Body model: PaymentRequestModel): ApiResponse<Any>

}