package com.fighterdiet.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.requestModel.PaymentRequestModel
import com.fighterdiet.data.model.responseModel.AboutPaulinNordinResponseModel
import com.fighterdiet.data.model.responseModel.FaqListResponseModel
import com.fighterdiet.data.repository.AboutPaulinNordinRepository
import com.fighterdiet.data.repository.MembershipRepository
import com.fighterdiet.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MembershipViewModel(private val membershipRepository: MembershipRepository):ViewModel() {
    private val errorMsg = MutableLiveData<String>()
    private val resource =
        MutableLiveData<Resource<ApiResponse<Any>>>()
    fun getResources() = resource
    fun getErrorMsg(): LiveData<String> {
        return errorMsg
    }

    fun callMembershipApi(paymentRequestModel: PaymentRequestModel){
        viewModelScope.launch {
            try {
                resource.postValue(Resource.loading(null))
                val apiResponse = membershipRepository.callPaymentApi(paymentRequestModel)
                withContext(Dispatchers.Main){
                    try {
                        if (apiResponse.status) {
                            resource.postValue(Resource.success(data = apiResponse))
                        }
                    } catch (e: HttpException) {
                        resource.postValue(Resource.error(null,e.localizedMessage!!))
                    } catch (e: Throwable) {
                        resource.postValue(Resource.error(null,e.localizedMessage!!))
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                resource.postValue(Resource.error(null, e.localizedMessage?:"error null"))
            }
        }
    }
}