package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.FaqAdapter
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.model.responseModel.FaqListResponseModel
import com.fighterdiet.data.model.responseModel.TrendingListResponseModel
import com.fighterdiet.data.repository.FaqRepository
import com.fighterdiet.data.repository.LoginRepository
import com.fighterdiet.databinding.ActivityFaqBinding
import com.fighterdiet.model.FaqModel
import com.fighterdiet.utils.Status
import com.fighterdiet.viewModel.FaqViewModel
import com.fighterdiet.viewModel.FaqViewModelProvider
import com.fighterdiet.viewModel.LoginViewModel
import com.fighterdiet.viewModel.LoginViewModelProvider
import java.util.*

class FaqActivity : BaseActivity(), View.OnClickListener {
    private lateinit var viewModel: FaqViewModel
    private lateinit var binding: ActivityFaqBinding
    private lateinit var productInfoAdapter: FaqAdapter
    val faqList: MutableList<FaqListResponseModel.Result> = ArrayList<FaqListResponseModel.Result>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* getWindow().setFlags(
             WindowManager.LayoutParams.FLAG_SECURE,
             WindowManager.LayoutParams.FLAG_SECURE
         )*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faq)
        initialise()
        setupRecyclerView()
        setupViewModel()
        setupObserver()
        binding.etSearch.addTextChangedListener {
            searchBasedOnFAQ(it.toString())
        }
    }

    private fun searchBasedOnFAQ(searchText: String) {
        val filterDataList = faqList.filter {
            it.question.contains(searchText.toString(), true)
        }
        productInfoAdapter.setDataList(filterDataList);
    }

    override fun setupUI() {

    }

    override fun setupViewModel() {

        viewModel = ViewModelProvider(
            this,
            FaqViewModelProvider(FaqRepository(RetrofitBuilder.apiService))
        ).get(FaqViewModel::class.java)
        viewModel.getFaqList()


    }


    override fun setupObserver() {

        viewModel.faqListResource.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data?.data?.result.isNullOrEmpty())
                        return@observe
                    faqList.addAll(it.data?.data?.result!!)
                    productInfoAdapter.notifyDataSetChanged()
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })
    }


    private fun initialise() {
        binding.ivBack.setOnClickListener(this)
        binding.etSearch.setOnClickListener(this)

    }

    private fun setupRecyclerView() {
        productInfoAdapter =
            FaqAdapter(this@FaqActivity, faqList, binding.mainView)
        binding.rvFaq.layoutManager = LinearLayoutManager(this)
        binding.rvFaq.adapter = productInfoAdapter
    }


    companion object {
        const val TAG = "FaqActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, FaqActivity::class.java)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }

}