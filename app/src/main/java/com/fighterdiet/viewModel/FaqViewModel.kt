package com.fighterdiet.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.responseModel.FaqListResponseModel
import com.fighterdiet.data.model.responseModel.TrendingListResponseModel
import com.fighterdiet.data.repository.FaqRepository
import com.fighterdiet.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class FaqViewModel(private val faqRepository: FaqRepository):ViewModel() {
    private val errorMsg = MutableLiveData<String>()
    val faqListResource =
        MutableLiveData<Resource<ApiResponse<FaqListResponseModel>>>()

    fun getErrorMsg(): LiveData<String> {
        return errorMsg
    }

    fun getFaqList(){
        viewModelScope.launch {
            try {
                faqListResource.postValue(Resource.loading(null))
                val apiResponse = faqRepository.faqListApi(2,2 )
                withContext(Dispatchers.Main){
                    try {
                        if (apiResponse.status) {
                            faqListResource.postValue(Resource.success(data = apiResponse))
                        }
                    } catch (e: HttpException) {
                        faqListResource.postValue(Resource.error(null,e.localizedMessage!!))
                    } catch (e: Throwable) {
                        faqListResource.postValue(Resource.error(null,e.localizedMessage!!))
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                faqListResource.postValue(Resource.error(null, e.localizedMessage?:"error null"))
            }
        }
    }

}