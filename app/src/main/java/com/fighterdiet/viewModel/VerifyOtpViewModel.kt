package com.fighterdiet.viewModel

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.requestModel.VerifyOtpRequestModel
import com.fighterdiet.data.model.responseModel.VerifyOtpResponseModel
import com.fighterdiet.data.repository.VerifyOtpRepository
import com.fighterdiet.utils.Resource
import kotlinx.coroutines.launch

class VerifyOtpViewModel(private val verifyOtpRepository: VerifyOtpRepository): ViewModel() {
    var otp1: String = ""
    var otp2: String = ""
    var otp3: String = ""
    var otp4: String = ""
    var otp:String= ""
    var user_id:String= ""
  /*  var otp=otp1+otp2+otp3+otp4
   var  user_id:String="331"*/
    private val resources= MutableLiveData<Resource<ApiResponse<VerifyOtpResponseModel>>>()

    fun getResources() = resources
    fun getErrorMsg(): LiveData<String> {
        return errorMsg
    }
    private val errorMsg = MutableLiveData<String>()

    fun getVerifyOtpApi(view: View, userid:String){
         otp=otp1+otp2+otp3+otp4
       user_id= userid
        if (isValid()){

            val verifyOtpRequestModel=VerifyOtpRequestModel(otp,user_id)
            viewModelScope.launch {
                try {
                    resources.postValue(Resource.loading(null))
                    val apiResponse=verifyOtpRepository.verifyotpApi(verifyOtpRequestModel)
                    resources.postValue(Resource.success(data = apiResponse))
                }catch (e: Exception) {
                    e.printStackTrace()
                    resources.postValue(Resource.error(null, e.localizedMessage!!))
                }

            }
        }

    }
    fun isValid() :Boolean{
        if (TextUtils.isEmpty(otp1)){
            errorMsg.value = "Please enter otp"
            return false
        }else if (TextUtils.isEmpty(otp2)) {
            errorMsg.value = "Please enter otp"
            return false
        }else if (TextUtils.isEmpty(otp3)){
            errorMsg.value = "Please enter otp"
            return false
        }else if (TextUtils.isEmpty(otp4)){
            errorMsg.value = "Please enter otp"
            return false
        }
        return true
    }
}