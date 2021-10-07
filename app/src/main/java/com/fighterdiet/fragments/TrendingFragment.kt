package com.fighterdiet.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.TrendingFragmentRecyAdapter
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.model.responseModel.TrendingListResponseModel
import com.fighterdiet.data.repository.TrendingRepository
import com.fighterdiet.databinding.FragmentTrendingBinding
import com.fighterdiet.utils.Status
import com.fighterdiet.viewModel.TrendingViewModel
import com.fighterdiet.viewModel.TrendingViewModelProvider

class TrendingFragment : BaseFragment() {
    private lateinit var viewModel: TrendingViewModel
    lateinit var binding: FragmentTrendingBinding
    private lateinit var trendingAdapter: TrendingFragmentRecyAdapter
   // var homeList: ArrayList<HomeModel> = ArrayList()
    var trendingList:ArrayList<TrendingListResponseModel.Result> =ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trending, container, false)
        return binding.root
    }

    companion object {

        fun getInstance(context: Context): Fragment {
            val bundle = Bundle()
            val fragment = TrendingFragment()
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
        setupViewModel()
        setupObserver()
    }

    private fun initialise() {
       // setUpHomeList()
        setUptrendingRecyclerView()
    }
    fun setupViewModel() {
        viewModel = ViewModelProvider(this, TrendingViewModelProvider(
            TrendingRepository(
                RetrofitBuilder.apiService)
        )
        )
            .get(TrendingViewModel::class.java)
        viewModel.getTrendingList()
    }
    fun setupObserver() {
        viewModel.trendingListResource.observe(viewLifecycleOwner, {
            when(it.status){
                Status.SUCCESS -> {
                    if (it.data?.data?.result.isNullOrEmpty())
                        return@observe
                    trendingList.addAll(it.data?.data?.result!!)
                    trendingAdapter.notifyDataSetChanged()
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })
    }

    private fun setUpHomeList() {
       /* homeList.add(HomeModel(R.mipmap.easy_chicken, false, false))
        homeList.add(HomeModel(R.mipmap.rice_pudding, false, false))
        homeList.add(HomeModel(R.mipmap.banana, false, false))

        homeList.add(HomeModel(R.mipmap.easy_chicken, false, false))
        homeList.add(HomeModel(R.mipmap.rice_pudding, false, false))
        homeList.add(HomeModel(R.mipmap.banana, false, false))

        homeList.add(HomeModel(R.mipmap.easy_chicken, false, false))
        homeList.add(HomeModel(R.mipmap.rice_pudding, false, false))
        homeList.add(HomeModel(R.mipmap.banana, false, false))

        homeList.add(HomeModel(R.mipmap.easy_chicken, false, false))
   */ }

    private fun setUptrendingRecyclerView() {

        binding.rvTrendingRecycler.layoutManager = LinearLayoutManager(activity)
        trendingAdapter = TrendingFragmentRecyAdapter(activity, trendingList) { position, view ->
           // Utils.showSnackBar(binding.rvTrendingRecycler, "mes")
        }
        binding.rvTrendingRecycler.adapter = trendingAdapter
    }

}