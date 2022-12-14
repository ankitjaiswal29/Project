package com.fighterdiet.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.responseModel.GetDietaryResponseModel
import com.fighterdiet.data.model.responseModel.GetMealResponseModel
import com.fighterdiet.data.model.responseModel.GetVolumeResponseModel
import com.fighterdiet.data.repository.FilterRecipeRepository
import com.fighterdiet.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class FilterRecipeViewModel(private val filterRecipeReository: FilterRecipeRepository):ViewModel() {
    private val errorMsg = MutableLiveData<String>()
    fun getErrorMsg(): LiveData<String> {
        return errorMsg
    }

    val getDietaryResource =
        MutableLiveData<Resource<ApiResponse<GetDietaryResponseModel>>>()
    val getVolumeResource =
        MutableLiveData<Resource<ApiResponse<GetVolumeResponseModel>>>()

    val getMealResource =
        MutableLiveData<Resource<ApiResponse<GetMealResponseModel>>>()

    fun getDietaryApi(){
        viewModelScope.launch {
            try {
                getDietaryResource.postValue(Resource.loading(null))
                val apiResponse = filterRecipeReository.getAllergyApi()
                withContext(Dispatchers.Main){
                    try {
                        if (apiResponse.status) {
                            getDietaryResource.postValue(Resource.success(data = apiResponse))
                        }
                    } catch (e: HttpException) {
                        getDietaryResource.postValue(Resource.error(null,e.localizedMessage!!))
                    } catch (e: Throwable) {
                        getDietaryResource.postValue(Resource.error(null,e.localizedMessage!!))
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                getDietaryResource.postValue(Resource.error(null, e.localizedMessage?:"error null"))
            }
        }
    }

    fun getVolumeApi(){
        viewModelScope.launch {
            try {
                getVolumeResource.postValue(Resource.loading(null))
                val apiResponse = filterRecipeReository.getVolumeApi()
                withContext(Dispatchers.Main){
                    try {
                        if (apiResponse.status) {
                            getVolumeResource.postValue(Resource.success(data = apiResponse))
                        }
                    } catch (e: HttpException) {
                        getVolumeResource.postValue(Resource.error(null,e.localizedMessage!!))
                    } catch (e: Throwable) {
                        getVolumeResource.postValue(Resource.error(null,e.localizedMessage!!))
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                getVolumeResource.postValue(Resource.error(null, e.localizedMessage?:"error null"))
            }
        }
    }

    fun getMealApi(){
        viewModelScope.launch {
            try {
                getMealResource.postValue(Resource.loading(null))
                val apiResponse = filterRecipeReository.getMealApi()
                withContext(Dispatchers.Main){
                    try {
                        if (apiResponse.status) {
                            getMealResource.postValue(Resource.success(data = apiResponse))
                        }
                    } catch (e: HttpException) {
                        getMealResource.postValue(Resource.error(null,e.localizedMessage!!))
                    } catch (e: Throwable) {
                        getMealResource.postValue(Resource.error(null,e.localizedMessage!!))
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                getMealResource.postValue(Resource.error(null, e.localizedMessage?:"error null"))
            }
        }
    }
}