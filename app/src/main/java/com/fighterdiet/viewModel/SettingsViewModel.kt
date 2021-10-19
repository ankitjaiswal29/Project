package com.fighterdiet.viewModel

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.requestModel.LogoutRequestModel
import com.fighterdiet.data.repository.SettingsRepository
import com.fighterdiet.utils.PrefManager
import com.fighterdiet.utils.Resource
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsRepository: SettingsRepository):ViewModel() {

   var device_token:String?=""


    private val resourcesLogout =
        MutableLiveData<Resource<ApiResponse<Any>>>()

    fun getResourcesLogout() = resourcesLogout
    fun getErrorMsg(): LiveData<String> {
        return errorMsg
    }
    private val errorMsg = MutableLiveData<String>()

    fun getLogOutApi(){
        device_token=PrefManager.getString(PrefManager.KEY_AUTH_TOKEN).toString()
//        System.out.println("tok"+device_token)
        if (isValid()){
            val logoutRequestModel= LogoutRequestModel(device_token)

            viewModelScope.launch {
                try {
                    resourcesLogout.postValue(Resource.loading(data = null))
                    val apiResponse=settingsRepository.logoutAPI(logoutRequestModel)
                    resourcesLogout.postValue(Resource.success(data = apiResponse))
                }catch (e: Exception) {
                    e.printStackTrace()
                    resourcesLogout.postValue(Resource.error(null, e.localizedMessage!!))
                }

            }
        }

    }

    fun isValid() :Boolean{
        if (TextUtils.isEmpty(device_token)){
            errorMsg.value = "Please login"
            return false
        }
        return true
    }
}