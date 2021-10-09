package com.fighterdiet.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.requestModel.AddCommentRequestModel
import com.fighterdiet.data.model.requestModel.CommentListRequestModel
import com.fighterdiet.data.model.responseModel.AddCommentResponseModel
import com.fighterdiet.data.model.responseModel.CommentListResponseModel
import com.fighterdiet.data.repository.CommentFragmentRepository
import com.fighterdiet.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.lang.Exception

class CommentFragmentViewModel(val repository: CommentFragmentRepository): ViewModel(){
    private val errorMsg = MutableLiveData<String>()
    val addCommentResponseResource =
        MutableLiveData<Resource<ApiResponse<AddCommentResponseModel>>>()

    fun getErrorMsg(): LiveData<String> {
        return errorMsg
    }

    private val commentListResource =
        MutableLiveData<Resource<ApiResponse<CommentListResponseModel>>>()

    fun getCommentListResource() : MutableLiveData<Resource<ApiResponse<CommentListResponseModel>>>{
        return commentListResource
    }

    private val deleteCommentResource =
        MutableLiveData<Resource<ApiResponse<ResponseBody>>>()

    fun getDeleteCommentResource() : MutableLiveData<Resource<ApiResponse<ResponseBody>>>{
        return deleteCommentResource
    }


    fun getCommentList(commentListRequestModel: CommentListRequestModel){
        viewModelScope.launch {
            try {
                commentListResource.postValue(Resource.loading(null))
                val apiResponse = repository.getCommentListApi(commentListRequestModel)
                withContext(Dispatchers.Main){
                    try {
                        if (apiResponse.status) {
                            commentListResource.postValue(Resource.success(data = apiResponse))
                        }
                    } catch (e: HttpException) {
                        commentListResource.postValue(Resource.error(null,e.localizedMessage!!))
                    } catch (e: Throwable) {
                        commentListResource.postValue(Resource.error(null,e.localizedMessage!!))
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                commentListResource.postValue(Resource.error(null, e.localizedMessage?:"error null"))
            }
        }
    }


    fun addRecipeComment(model: AddCommentRequestModel){
        viewModelScope.launch {
            try {
                addCommentResponseResource.postValue(Resource.loading(null))
                val apiResponse = repository.addRecipeCommentApi(model)
                withContext(Dispatchers.Main){
                    try {
                        if (apiResponse.status) {
                            addCommentResponseResource.postValue(Resource.success(data = apiResponse))
                        }
                    } catch (e: HttpException) {
                        addCommentResponseResource.postValue(Resource.error(null,e.localizedMessage!!))
                    } catch (e: Throwable) {
                        addCommentResponseResource.postValue(Resource.error(null,e.localizedMessage!!))
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                addCommentResponseResource.postValue(Resource.error(null,e.localizedMessage?:"error null"))
            }
        }
    }

    fun deleteRecipeComment(id: Int){
        viewModelScope.launch {
            try {
                deleteCommentResource.postValue(Resource.loading(null))
                val apiResponse = repository.deleteCommentApi(id)
                withContext(Dispatchers.Main){
                    try {
                        if (apiResponse.status) {
                            deleteCommentResource.postValue(Resource.success(data = apiResponse))
                        }
                    } catch (e: HttpException) {
                        deleteCommentResource.postValue(Resource.error(null,e.localizedMessage!!))
                    } catch (e: Throwable) {
                        deleteCommentResource.postValue(Resource.error(null,e.localizedMessage!!))
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                deleteCommentResource.postValue(Resource.error(null,e.localizedMessage?:"error null"))
            }
        }
    }
}