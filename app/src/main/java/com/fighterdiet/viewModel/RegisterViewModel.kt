package com.fighterdiet.viewModel

import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.requestModel.RegisterRequestModel
import com.fighterdiet.data.model.responseModel.RegisterResponseModel
import com.fighterdiet.data.repository.RegisterRepository
import com.fighterdiet.utils.Resource
import kotlinx.coroutines.launch

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {

    var firstName: String = ""
    var secondName: String = ""
    var user_name: String = ""
    var email: String = ""
    var password: String = ""
    var confirm_password: String = ""


    private val resources =
        MutableLiveData<Resource<ApiResponse<ArrayList<RegisterResponseModel>>>>()

    fun getResources() = resources
    fun getErrorMsg(): LiveData<String> {
        return errorMsg
    }

    private val errorMsg = MutableLiveData<String>()

    fun getRegisterApi(view: View) {

        if (isValid()){
            val registerRequestModel = RegisterRequestModel(
                firstName, secondName, email, user_name, password, confirm_password
            )
            viewModelScope.launch {
                try {
                    resources.postValue(Resource.loading(data = null))
                    val apiResponse = registerRepository.registerApi(registerRequestModel)
                    resources.postValue(Resource.success(data = apiResponse))
                } catch (e: Exception) {
                    e.printStackTrace()
                    resources.postValue(Resource.error(null, e.localizedMessage!!))
                }
            }
        }

    }

    fun isValid() :Boolean{
        if (TextUtils.isEmpty(user_name)){
            errorMsg.value = "Please enter user name"
            return false
        }else if (TextUtils.isEmpty(firstName)) {
            errorMsg.value = "Please enter first name"
            return false
        } else if (TextUtils.isEmpty(secondName)) {
            errorMsg.value = "Please enter second name"
            return false
        } else if (TextUtils.isEmpty(email)) {
            errorMsg.value = "Please enter email"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMsg.value = "Please enter valid email"
            return false
        } else if (TextUtils.isEmpty(password)) {
            errorMsg.value = "Please enter password"
            return false
        } else if (TextUtils.isEmpty(confirm_password)) {
            errorMsg.value = "Please enter confirm password"
            return false
        } else if (!password.equals(confirm_password)) {
            errorMsg.value = "password and confirm password doesn't match."
            return false
        }
        return true
    }

}