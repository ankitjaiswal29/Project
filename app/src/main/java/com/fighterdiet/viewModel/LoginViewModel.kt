package com.fighterdiet.viewModel

import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.requestModel.LoginRequestModel
import com.fighterdiet.data.model.responseModel.LoginResponseModel
import com.fighterdiet.data.model.responseModel.RegistrationResponseModel
import com.fighterdiet.data.repository.LoginRepository
import com.fighterdiet.data.repository.RegisterRepository
import com.fighterdiet.utils.Resource
import com.fighterdiet.utils.Utils
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    var email: String = ""
    var password: String = ""

    private val resources = MutableLiveData<Resource<ApiResponse<LoginResponseModel>>>()

    fun getResources() = resources

    fun getErrorMsg(): LiveData<String> {
        return errorMsg
    }
    private val errorMsg = MutableLiveData<String>()

    fun getLoginApi(view:View){
        if (isValid()){
            val loginRequestModel=LoginRequestModel(email,password)
            viewModelScope.launch {
                try {
                    resources.postValue(Resource.loading(data = null))
                    val apiResponse=loginRepository.loginApi(loginRequestModel)
                    resources.postValue(Resource.success(data = apiResponse))
                }catch (e: Exception) {
                    e.printStackTrace()
                    resources.postValue(Resource.error(null, e.localizedMessage?:"error"))
                }
            }
        }

    }

    fun isValid() :Boolean{
        if (TextUtils.isEmpty(email)){
            errorMsg.value = "Please enter email"
            return false
        }
        if (TextUtils.isEmpty(password)) {
            errorMsg.value = "Please enter password"
            return false
        }
        if(password.length > 16 || password.length < 8){
            errorMsg.value = "Enter valid password"
            return false
        }
        return true
    }
}