package com.fighterdiet.data.repository

import com.fighterdiet.data.api.ApiService
import com.fighterdiet.data.model.requestModel.AddCommentRequestModel
import com.fighterdiet.data.model.requestModel.CommentListRequestModel
import com.fighterdiet.data.model.requestModel.SpamCommentRequestModel

class CommentFragmentRepository(private val apiService: ApiService) {
    suspend fun addRecipeCommentApi(addCommentRequestModel: AddCommentRequestModel) = apiService.addRecipeComment(addCommentRequestModel)
    suspend fun getCommentListApi(model: CommentListRequestModel) = apiService.getCommentListApi(model)
    suspend fun deleteCommentApi(comment_id: Int) = apiService.deleteCommentApi(comment_id)
    suspend fun reportSpamCommentApi(model: SpamCommentRequestModel) = apiService.reportSpamCommentApi(model)
}