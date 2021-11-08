package com.fighterdiet.viewModel

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.requestModel.ChangePasswordRequestModel
import com.fighterdiet.data.model.requestModel.ResetPasswordRequestModel
import com.fighterdiet.data.model.responseModel.ErrorResponse
import com.fighterdiet.data.repository.UpdatePasswordRepository
import com.fighterdiet.utils.PrefManager
import com.fighterdiet.utils.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UpdatePassViewModel(private val updatePasswordRepository: UpdatePasswordRepository):ViewModel(){
    var newPassword: String = ""
    var confirmPassword: String = ""
    private val resources= MutableLiveData<Resource<ApiResponse<Any>>>()

    fun getResources() = resources
    fun getErrorMsg(): LiveData<String> {
        return errorMsg
    }
    private val errorMsg = MutableLiveData<String>()

    fun updatePasswordApi(view: View){

        if (isValid()){
            val changePasswordRequestModel= ResetPasswordRequestModel(password = confirmPassword, user_id = PrefManager.getString(PrefManager.KEY_USER_ID)?:"")
            viewModelScope.launch {
                try {
                    resources.postValue(Resource.loading(null))
                    val apiResponse=updatePasswordRepository.resetPasswordApi(changePasswordRequestModel)
                    resources.postValue(Resource.success(data = apiResponse))
                }catch (e: Exception) {
                    e.printStackTrace()
                    if(e.message!!.contains("Bad Request", true)){
                        val gson = Gson()
                        val type = object : TypeToken<ErrorResponse>() {}.type
                        val errorResponse: ErrorResponse? = gson.fromJson((e as HttpException).response()?.errorBody()!!.charStream(), type)
                        resources.postValue(Resource.error(null,
                            errorResponse?.message?:e.localizedMessage))
                    }
                    else
                        resources.postValue(Resource.error(null, e.localizedMessage))
                }

            }
        }


    }
    private fun isValid() :Boolean{
        if (TextUtils.isEmpty(newPassword)){
            errorMsg.value = "Please enter new password"
            return false
        }

        if (newPassword.length < 8 || newPassword.length>20) {
            errorMsg.value = "New password should be 8 digits minimum"
            return false
        }

        if (TextUtils.isEmpty(confirmPassword)){
            errorMsg.value = "Please re-enter password"
            return false
        }

        if (newPassword != confirmPassword) {
            errorMsg.value = "Confirm Password doesn't match"
            return false
        }
        return true
    }
}