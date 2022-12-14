package com.fighterdiet.fragments

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.CommentAdapter
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.model.requestModel.AddCommentRequestModel
import com.fighterdiet.data.model.requestModel.CommentListRequestModel
import com.fighterdiet.data.model.requestModel.SpamCommentRequestModel
import com.fighterdiet.data.model.responseModel.CommentListResponseModel
import com.fighterdiet.data.repository.CommentFragmentRepository
import com.fighterdiet.data.repository.CommentFragmentViewModelProvider
import com.fighterdiet.databinding.FragmentCommentBinding
import com.fighterdiet.interfaces.RecyclerItemClickListener
import com.fighterdiet.utils.Constants
import com.fighterdiet.utils.PrefManager
import com.fighterdiet.utils.ProgressDialog
import com.fighterdiet.utils.Status
import com.fighterdiet.viewModel.CommentFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CommentFragment(val recipeId:String) : BottomSheetDialogFragment(), View.OnClickListener {

    private var commentAdapter: CommentAdapter? = null
    private var commentList: ArrayList<CommentListResponseModel.CommentRecipe> = ArrayList()
    private lateinit var mContext: Context
    private lateinit var binding: FragmentCommentBinding
    private lateinit var viewModel: CommentFragmentViewModel

    companion object {
        fun newInstance(recipeId:String): CommentFragment {
            return CommentFragment(recipeId)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val sheetView: View =
            layoutInflater.inflate(R.layout.fragment_comment, container, false)
        binding = DataBindingUtil.bind(sheetView)!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val content = SpannableString(getString(R.string.comments))
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        binding.tvTitle.text = content
        binding.ivBack.setOnClickListener(this)
        setupViewModel()
        setupObserver()
        setupListener()
        callGetCommentListApi()
    }

    private fun setupListener() {
        binding.tvNext.setOnClickListener {
            val userId = PrefManager.getString(PrefManager.KEY_USER_ID)?:""
            if(userId.isNotEmpty() && recipeId.isNotBlank() && binding.etComment.text.toString().isNotBlank()){
                viewModel.addRecipeComment(
                    AddCommentRequestModel(
                        recipeId,
                        userId,
                        binding.etComment.text.toString()
                    )
                )
            }
        }

        binding.etComment.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    ProgressDialog.showProgressDialog(requireContext())
                    val userId = PrefManager.getString(PrefManager.KEY_USER_ID)?:""
                    if(userId.isNotEmpty() && recipeId.isNotBlank() && binding.etComment.text.toString().isNotBlank()) {
                        viewModel.addRecipeComment(
                            AddCommentRequestModel(
                                recipeId,
                                userId,
                                binding.etComment.text.toString()
                            )
                        )
                    }
                    true
                }
                else -> false
            }
        }

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this,
            CommentFragmentViewModelProvider(CommentFragmentRepository(RetrofitBuilder.apiService)))
            .get(CommentFragmentViewModel::class.java)
    }

    private fun callGetCommentListApi() {
        ProgressDialog.showProgressDialog(requireContext())
        viewModel.getCommentList(
            CommentListRequestModel(
                offset = 0,
                limit = 8,
                recipe_id = recipeId
            )
        )
    }

    private fun setupRecyclerView() {
        if(commentList.isEmpty())
            return

        binding.rvComment.layoutManager = LinearLayoutManager(activity)
        commentAdapter = CommentAdapter(commentList, object :RecyclerItemClickListener{
            override fun onItemClick(operationType: Int, selectedItem: Any?, pos: Int) {
                val item = selectedItem as CommentListResponseModel.CommentRecipe
                when(operationType){
                    Constants.OPERATION_DELETE ->{
                        ProgressDialog.showProgressDialog(requireContext())
                        viewModel.deleteRecipeComment(item.id)
                    }

                    Constants.OPERATION_REPORT_SPAM ->{
                        ProgressDialog.showProgressDialog(requireContext())
                        viewModel.reportSpamComment(SpamCommentRequestModel(
                            comment_id = item.id.toString(),
                            recipe_id = recipeId
                        ))
                    }
                }
            }
        })
        binding.rvComment.adapter = commentAdapter

    }

    private fun setupObserver() {

        viewModel.getCommentListResource().observe(this, {
            when(it.status){
                Status.SUCCESS -> {

                    it.data?.data?.let { comments ->
                        commentList.clear()
                        binding.etComment.text.clear()
                        if(comments.comment_recipe.isNotEmpty())
                            commentList.addAll(comments.comment_recipe)
                        setupRecyclerView()
                        ProgressDialog.hideProgressDialog()
                    }

                    commentAdapter?.notifyDataSetChanged()
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })

        viewModel.addCommentResponseResource.observe(this, {
            when(it.status){

                Status.SUCCESS -> {
                    callGetCommentListApi()
                }

                Status.LOADING -> {

                }

                Status.ERROR -> {

                }

            }
        })

        viewModel.getDeleteCommentResource().observe(this, {
            when(it.status){

                Status.SUCCESS -> {
                    callGetCommentListApi()
                }

                Status.LOADING -> {

                }

                Status.ERROR -> {

                }

            }
        })

        viewModel.getReportSpamResource().observe(this, {
            when(it.status){
                Status.SUCCESS -> {
                    callGetCommentListApi()
                }

                Status.LOADING -> {

                }

                Status.ERROR -> {

                }

            }
        })
    }

    override fun onClick(view: View?) {
        dismiss()
    }
}
