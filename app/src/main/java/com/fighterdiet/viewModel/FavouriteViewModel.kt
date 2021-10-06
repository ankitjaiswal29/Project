package com.fighterdiet.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.responseModel.FavouriteListResponseModel
import com.fighterdiet.data.model.responseModel.RecipeListResponseModel
import com.fighterdiet.data.repository.FavouriteRepository
import com.fighterdiet.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class FavouriteViewModel(private val favouriteRepository: FavouriteRepository):ViewModel() {

    private val errorMsg = MutableLiveData<String>()
    val favouriteListResource =
        MutableLiveData<Resource<ApiResponse<FavouriteListResponseModel>>>()

    fun getErrorMsg(): LiveData<String> {
        return errorMsg
    }

    fun getFavouriteList(){
        viewModelScope.launch {
            try {
                favouriteListResource.postValue(Resource.loading(null))
                val apiResponse = favouriteRepository.favouriteListApi(2, 2,"")
                withContext(Dispatchers.Main){
                    try {
                        if (apiResponse.status) {
                            favouriteListResource.postValue(Resource.success(data = apiResponse))
                        }
                    } catch (e: HttpException) {
                        favouriteListResource.postValue(Resource.error(null,e.localizedMessage!!))
                    } catch (e: Throwable) {
                        favouriteListResource.postValue(Resource.error(null,e.localizedMessage!!))
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                favouriteListResource.postValue(Resource.error(null, e.localizedMessage?:"error null"))
            }
        }
    }
}