package com.fighterdiet.viewModel

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.requestModel.ForgotPasswordRequestModel
import com.fighterdiet.data.model.requestModel.LoginRequestModel
import com.fighterdiet.data.model.responseModel.ForgotPasswordResponseModel
import com.fighterdiet.data.repository.ForgotPasswordRepository
import com.fighterdiet.data.repository.LoginRepository
import com.fighterdiet.utils.Resource
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val forgotPasswordRepository: ForgotPasswordRepository):ViewModel(){
    var email: String = ""
    private val resources=MutableLiveData<Resource<ApiResponse<ForgotPasswordResponseModel>>>()

    fun getResources() = resources
    fun getErrorMsg(): LiveData<String> {
        return errorMsg
    }
    private val errorMsg = MutableLiveData<String>()

    fun getForgotPasswordApi(view: View){
        if (isValid()){
            val forgotPasswordRequestModel= ForgotPasswordRequestModel(email)

            viewModelScope.launch {
                try {
                    resources.postValue(Resource.loading(null))
                    val apiResponse=forgotPasswordRepository.forgotpasswordApi(forgotPasswordRequestModel)
                    resources.postValue(Resource.success(data = apiResponse))
                }catch (e: Exception) {
                    e.printStackTrace()
                    resources.postValue(Resource.error(null, e.localizedMessage!!))
                }

            }
        }

    }
    fun isValid() :Boolean{
        if (TextUtils.isEmpty(email)){
            errorMsg.value = "Please enter email"
            return false
        }
        return true
    }
}