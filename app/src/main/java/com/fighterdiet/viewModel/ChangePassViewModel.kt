package com.fighterdiet.viewModel

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.requestModel.ChangePasswordRequestModel
import com.fighterdiet.data.repository.ChangePasswordRepository
import com.fighterdiet.utils.Resource
import kotlinx.coroutines.launch

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
                    resources.postValue(Resource.error(null, e.localizedMessage!!))
                }

            }
        }

    }
    fun isValid() :Boolean{
        if (TextUtils.isEmpty(oldPassword)){
            errorMsg.value = "Please enter old password"
            return false
        }
        if (TextUtils.isEmpty(password)){
            errorMsg.value = "Please enter password"
            return false
        }
        if (TextUtils.isEmpty(confiPassword)){
            errorMsg.value = "Please re-enter password"
            return false
        }
        return true
    }
}