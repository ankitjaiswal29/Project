package com.fighterdiet.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.requestModel.*
import com.fighterdiet.data.model.responseModel.*
import com.fighterdiet.fragments.RecipeInfoRepository
import com.fighterdiet.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RecipeInfoViewModel(val recipeInfoRepo: RecipeInfoRepository) : ViewModel() {
    private val errorMsg = MutableLiveData<String>()
    private val recipeInfoResource =
        MutableLiveData<Resource<ApiResponse<RecipeContentResponseModel>>>()

    private val addedToFavResource =
        MutableLiveData<Resource<ApiResponse<AddToFavResponseModel>>>()

    private val addNoteResource =
        MutableLiveData<Resource<ApiResponse<AddNotesResponseModel>>>()

    private val updateNoteResource =
        MutableLiveData<Resource<ApiResponse<UpdateNotesResponseModel>>>()

    fun getErrorMsg(): LiveData<String> {
        return errorMsg
    }

    fun getRecipeInfoResource() : MutableLiveData<Resource<ApiResponse<RecipeContentResponseModel>>>{
        return recipeInfoResource
    }

    fun getAddToFavResource() : MutableLiveData<Resource<ApiResponse<AddToFavResponseModel>>>{
        return addedToFavResource
    }

    fun getAddNotesResource() : MutableLiveData<Resource<ApiResponse<AddNotesResponseModel>>>{
        return addNoteResource
    }

    fun getUpdateNotesResource() : MutableLiveData<Resource<ApiResponse<UpdateNotesResponseModel>>>{
        return updateNoteResource
    }

    fun getRecipeContent(contentModel: RecipeContentRequestModel){
        viewModelScope.launch {
            try {
                recipeInfoResource.postValue(Resource.loading(null))
                val apiResponse = recipeInfoRepo.getRecipeContentApi(contentModel)
                withContext(Dispatchers.Main){
                    try {
                        if (apiResponse.status) {
                            recipeInfoResource.postValue(Resource.success(data = apiResponse))
                        }
                    } catch (e: HttpException) {
                        recipeInfoResource.postValue(Resource.error(null,e.localizedMessage!!))
                    } catch (e: Throwable) {
                        recipeInfoResource.postValue(Resource.error(null,e.localizedMessage!!))
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                recipeInfoResource.postValue(Resource.error(null, e.localizedMessage?:"error null"))
            }
        }
    }

    fun addToFavApi(addFavReqModel: AddToFavRequestModel){
        viewModelScope.launch {
            try {
                addedToFavResource.postValue(Resource.loading(null))
                val apiResponse = recipeInfoRepo.addRecipeToFavApi(addFavReqModel)
                withContext(Dispatchers.Main){
                    try {
                        if (apiResponse.status) {
                            addedToFavResource.postValue(Resource.success(data = apiResponse))
                        }
                    } catch (e: HttpException) {
                        addedToFavResource.postValue(Resource.error(null,e.localizedMessage!!))
                    } catch (e: Throwable) {
                        addedToFavResource.postValue(Resource.error(null,e.localizedMessage!!))
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                addedToFavResource.postValue(Resource.error(null, e.localizedMessage?:"error null"))
            }
        }
    }

    fun addNotesApi(addNotesRequestModel: AddNotesRequestModel){
        viewModelScope.launch {
            try {
                addNoteResource.postValue(Resource.loading(null))
                val apiResponse = recipeInfoRepo.addNotesApi(addNotesRequestModel)
                withContext(Dispatchers.Main){
                    try {
                        if (apiResponse.status) {
                            addNoteResource.postValue(Resource.success(data = apiResponse))
                        }
                    } catch (e: HttpException) {
                        addNoteResource.postValue(Resource.error(null,e.localizedMessage!!))
                    } catch (e: Throwable) {
                        addNoteResource.postValue(Resource.error(null,e.localizedMessage!!))
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                addNoteResource.postValue(Resource.error(null, e.localizedMessage?:"error null"))
            }
        }
    }


    fun updateNotesApi(updateNotesRequestModel: UpdateNotesRequestModel){
        viewModelScope.launch {
            try {
                updateNoteResource.postValue(Resource.loading(null))
                val apiResponse = recipeInfoRepo.updateNotesApi(updateNotesRequestModel)
                withContext(Dispatchers.Main){
                    try {
                        if (apiResponse.status) {
                            updateNoteResource.postValue(Resource.success(data = apiResponse))
                        }
                    } catch (e: HttpException) {
                        updateNoteResource.postValue(Resource.error(null,e.localizedMessage!!))
                    } catch (e: Throwable) {
                        updateNoteResource.postValue(Resource.error(null,e.localizedMessage!!))
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                updateNoteResource.postValue(Resource.error(null, e.localizedMessage?:"error null"))
            }
        }
    }
}