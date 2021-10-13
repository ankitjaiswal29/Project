package com.fighterdiet.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.responseModel.RecipeListResponseModel
import com.fighterdiet.data.model.responseModel.TrendingListResponseModel
import com.fighterdiet.data.repository.TrendingRepository
import com.fighterdiet.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class TrendingViewModel(private val trendingRepository: TrendingRepository):ViewModel() {
    private val errorMsg = MutableLiveData<String>()
    val trendingListResource =
        MutableLiveData<Resource<ApiResponse<TrendingListResponseModel>>>()

    fun getErrorMsg(): LiveData<String> {
        return errorMsg
    }

    fun getTrendingList(offset:Int, limit:Int){
        viewModelScope.launch {
            try {
                trendingListResource.postValue(Resource.loading(null))
                val apiResponse = trendingRepository.trendingListApi(offset, limit )
                withContext(Dispatchers.Main){
                    try {
                        if (apiResponse.status) {
                            trendingListResource.postValue(Resource.success(data = apiResponse))
                        }
                    } catch (e: HttpException) {
                        trendingListResource.postValue(Resource.error(null,e.localizedMessage!!))
                    } catch (e: Throwable) {
                        trendingListResource.postValue(Resource.error(null,e.localizedMessage!!))
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                trendingListResource.postValue(Resource.error(null, e.localizedMessage?:"error null"))
            }
        }
    }
}