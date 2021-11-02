package com.fighterdiet.viewModel

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.requestModel.ChangePasswordRequestModel
import com.fighterdiet.data.model.responseModel.ErrorResponse
import com.fighterdiet.data.repository.ChangePasswordRepository
import com.fighterdiet.utils.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ChangePassViewModel(private val changePasswordRepository: ChangePasswordRepository):ViewModel(){
    var oldPassword: String = ""
    var password: String = ""
    var confiPassword: String = ""
    private val resources= MutableLiveData<Resource<ApiResponse<Any>>>()

    fun getResources() = resources
    fun getErrorMsg(): LiveData<String> {
        return errorMsg
    }
    private val errorMsg = MutableLiveData<String>()

    fun changePasswordApi(view: View){

        if (isValid()){
            val changePasswordRequestModel= ChangePasswordRequestModel(confirm_password = confiPassword, password = password, old_password = oldPassword)

            viewModelScope.launch {
                try {
                    resources.postValue(Resource.loading(null))
                    val apiResponse=changePasswordRepository.changePasswordApi(changePasswordRequestModel)
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
        if (TextUtils.isEmpty(oldPassword)){
            errorMsg.value = "Please enter old password"
            return false
        }

        if (oldPassword.length < 8 || oldPassword.length>20) {
            errorMsg.value = "Old password should be 8 digits minimum"
            return false
        }

        if (TextUtils.isEmpty(password)){
            errorMsg.value = "Please enter password"
            return false
        }

        if (password.length < 8 || password.length>20) {
            errorMsg.value = "New password should be 8 digits minimum"
            return false
        }

        if (TextUtils.isEmpty(confiPassword)){
            errorMsg.value = "Please re-enter password"
            return false
        }

        if (password != confiPassword) {
            errorMsg.value = "Confirm password doesn't match"
            return false
        }
        return true
    }
}