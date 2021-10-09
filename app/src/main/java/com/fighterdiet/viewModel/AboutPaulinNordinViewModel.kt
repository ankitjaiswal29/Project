package com.fighterdiet.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.responseModel.AboutPaulinNordinResponseModel
import com.fighterdiet.data.model.responseModel.FaqListResponseModel
import com.fighterdiet.data.repository.AboutPaulinNordinRepository
import com.fighterdiet.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AboutPaulinNordinViewModel(private val aboutPaulinNordinRepository: AboutPaulinNordinRepository):ViewModel() {
    private val errorMsg = MutableLiveData<String>()
    val aboutPaulinNordinListResource =
        MutableLiveData<Resource<ApiResponse<AboutPaulinNordinResponseModel>>>()
    fun getResources() = aboutPaulinNordinListResource
    fun getErrorMsg(): LiveData<String> {
        return errorMsg
    }

    fun getAboutPaulinNordinList(){
        viewModelScope.launch {
            try {
                aboutPaulinNordinListResource.postValue(Resource.loading(null))
                val apiResponse = aboutPaulinNordinRepository.aboutListApi( )
                withContext(Dispatchers.Main){
                    try {
                        if (apiResponse.status) {
                            aboutPaulinNordinListResource.postValue(Resource.success(data = apiResponse))
                        }
                    } catch (e: HttpException) {
                        aboutPaulinNordinListResource.postValue(Resource.error(null,e.localizedMessage!!))
                    } catch (e: Throwable) {
                        aboutPaulinNordinListResource.postValue(Resource.error(null,e.localizedMessage!!))
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                aboutPaulinNordinListResource.postValue(Resource.error(null, e.localizedMessage?:"error null"))
            }
        }
    }
}