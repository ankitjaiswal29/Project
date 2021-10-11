package com.fighterdiet.fragments

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.fighterdiet.utils.Status
import com.fighterdiet.viewModel.CommentFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_comment.*


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
        setupRecyclerView()
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
                        etComment.text.toString()
                    )
                )
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this,
            CommentFragmentViewModelProvider(CommentFragmentRepository(RetrofitBuilder.apiService)))
            .get(CommentFragmentViewModel::class.java)
    }

    private fun setupObserver() {

        viewModel.getCommentListResource().observe(this, {
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.data?.let { comments ->
                        commentList.clear()
                        binding.etComment.text.clear()
                        commentList.addAll(comments.comment_recipe)
                        setupRecyclerView()
                    }
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

    private fun callGetCommentListApi() {
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
            override fun onItemClick(operationType: Int, selectedItem: Any?) {
                val item = selectedItem as CommentListResponseModel.CommentRecipe
                when(operationType){
                    Constants.OPERATION_DELETE ->{
                        viewModel.deleteRecipeComment(item.id)
                    }

                    Constants.OPERATION_REPORT_SPAM ->{
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

    override fun onClick(view: View?) {
        dismiss()
    }
}