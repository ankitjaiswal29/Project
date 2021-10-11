package com.fighterdiet.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.responseModel.RecipeListResponseModel
import com.fighterdiet.data.repository.HomeRepository
import com.fighterdiet.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class HomeViewModel(private val homeRepository: HomeRepository): ViewModel() {
    private val errorMsg = MutableLiveData<String>()
    private val recipeListResource =
        MutableLiveData<Resource<ApiResponse<RecipeListResponseModel>>>()

    fun getErrorMsg(): LiveData<String> {
        return errorMsg
    }

    fun getRecipeListResource() : MutableLiveData<Resource<ApiResponse<RecipeListResponseModel>>>{
        return recipeListResource
    }

    fun getRecipeList(
        searchKey: String,
        offset: Int,
        limit: Int,
        selectedDietaryMap: HashMap<String, Int>,
        selectedVolumeMap: HashMap<String, Int>,
        selectedMealMap: HashMap<String, Int>
    ) {
        viewModelScope.launch {
            try {
                recipeListResource.postValue(Resource.loading(null))
                val apiResponse = homeRepository.recipeListApi(offset.toInt(), limit.toInt(), searchKey, selectedDietaryMap, selectedVolumeMap, selectedMealMap)
                withContext(Dispatchers.Main){
                    try {
                        if (apiResponse.status) {
                            recipeListResource.postValue(Resource.success(data = apiResponse))
                        }
                    } catch (e: HttpException) {
                        recipeListResource.postValue(Resource.error(null,e.localizedMessage!!))
                    } catch (e: Throwable) {
                        recipeListResource.postValue(Resource.error(null,e.localizedMessage!!))
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                recipeListResource.postValue(Resource.error(null, e.localizedMessage?:"error null"))
            }
        }
    }

}