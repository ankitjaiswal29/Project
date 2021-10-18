package com.fighterdiet.viewModel

import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.requestModel.CheckUserNameRequest
import com.fighterdiet.data.model.requestModel.RegisterRequestModel
import com.fighterdiet.data.model.responseModel.CheckUserNameResponseModel
import com.fighterdiet.data.model.responseModel.ErrorResponse
import com.fighterdiet.data.model.responseModel.RegistrationResponseModel
import com.fighterdiet.data.repository.RegisterRepository
import com.fighterdiet.utils.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {

    var user_name: String = ""
    var first_name: String = ""
    var last_name: String = ""
    var email: String = ""
    var password: String = ""
   var  phone_number:String=" "
    var confirm_password: String = ""


    private val resources =
        MutableLiveData<Resource<ApiResponse<RegistrationResponseModel>>>()

    fun getResourcesCheckUser() = resourcesCheckUser

    private val resourcesCheckUser =
        MutableLiveData<Resource<ApiResponse<CheckUserNameResponseModel>>>()

    fun getResources() = resources

    fun getErrorMsg(): LiveData<String> {
        return errorMsg
    }

    private val errorMsg = MutableLiveData<String>()

    fun getRegisterApi(view: View) {

        if (isValid()){
            val registerRequestModel = RegisterRequestModel(
                user_name,first_name,last_name,email,password,phone_number,confirm_password
                //firstName, secondName, email, user_name, password, confirm_password
            )
            viewModelScope.launch {
                 try {
                    resources.postValue(Resource.loading(data = null))
                    val apiResponse = registerRepository.registerApi(registerRequestModel)
                    resources.postValue(Resource.success(data = apiResponse))
                } catch (e: Exception) {
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

    fun checkUserNameApi(userName: String) {
        if (userName.length in 6..12){
            val model = CheckUserNameRequest(user_name = userName)
            viewModelScope.launch {
                try {
                    resourcesCheckUser.postValue(Resource.loading(data = null))
                    val apiResponse = registerRepository.checkUserName(model)
                    resourcesCheckUser.postValue(Resource.success(data = apiResponse))
                } catch (e: Exception) {
                    e.printStackTrace()
                    resourcesCheckUser.postValue(Resource.error(null, e.localizedMessage!!))
                }
            }
        }

    }

    fun isValid() :Boolean{
        if (TextUtils.isEmpty(user_name)){
            errorMsg.value = "Please enter user name"
            return false
        }else if (TextUtils.isEmpty(first_name)) {
            errorMsg.value = "Please enter first name"
            return false
        } else if (TextUtils.isEmpty(last_name)) {
            errorMsg.value = "Please enter last name"
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