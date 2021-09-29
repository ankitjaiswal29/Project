package com.fighterdiet.viewModel

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
    var phone_number: String = ""
    var confirm_password: String = ""


    private val resources =
        MutableLiveData<Resource<ApiResponse<ArrayList<RegisterResponseModel>>>>()

    fun getResources() = resources
    fun getErrorMsg(): LiveData<String> {
        return errorMsg
    }

    private val errorMsg = MutableLiveData<String>()

    fun getRegisterApi(view: View) {

        val registerRequestModel = RegisterRequestModel(
            firstName, secondName, email, user_name, password, phone_number, confirm_password
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