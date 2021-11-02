package com.fighterdiet.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.activities.LoginActivity
import com.fighterdiet.activities.MemberShipActivity
import com.fighterdiet.activities.RecipeDetailsActivity
import com.fighterdiet.adapters.TrendingFragmentRecyAdapter
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.model.responseModel.TrendingListResponseModel
import com.fighterdiet.data.repository.TrendingRepository
import com.fighterdiet.databinding.FragmentTrendingBinding
import com.fighterdiet.utils.*
import com.fighterdiet.viewModel.TrendingViewModel
import com.fighterdiet.viewModel.TrendingViewModelProvider

class TrendingFragment : BaseFragment() {
    private lateinit var viewModel: TrendingViewModel
    lateinit var binding: FragmentTrendingBinding
    private lateinit var trendingAdapter: TrendingFragmentRecyAdapter
   // var homeList: ArrayList<HomeModel> = ArrayList()
    var trendingList:ArrayList<TrendingListResponseModel.Result> =ArrayList()
    var offset = 0
    var limit = 8

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
        setUpTrendingRecyclerView()
    }
    fun setupViewModel() {
        viewModel = ViewModelProvider(this, TrendingViewModelProvider(
            TrendingRepository(
                RetrofitBuilder.apiService)
        )
        )
            .get(TrendingViewModel::class.java)
        viewModel.getTrendingList(offset, limit)
    }
    fun setupObserver() {
        viewModel.trendingListResource.observe(viewLifecycleOwner, {
            when(it.status){
                Status.SUCCESS -> {
                    if (it.data?.data?.result.isNullOrEmpty())
                        return@observe
                    trendingList.addAll(it.data?.data?.result!!)
                    val currSize = binding.rvTrendingRecycler.adapter?.itemCount?:0
                    if(currSize>0)
                        trendingAdapter.notifyItemRangeInserted(currSize, trendingList.size - 1);
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })
    }

    private fun setUpTrendingRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        binding.rvTrendingRecycler.layoutManager = layoutManager
        trendingAdapter = TrendingFragmentRecyAdapter(activity, trendingList) { position, data ->
            if(!PrefManager.getBoolean(PrefManager.IS_LOGGED_IN)){
                Utils.loginAlertDialog(requireActivity())
                return@TrendingFragmentRecyAdapter
            }

//            if(PrefManager.getBoolean(PrefManager.IS_SUBSCRIBED)){
                val act = RecipeDetailsActivity.getStartIntent(requireContext())
                    .putExtra(Constants.RECIPE_ID, data.id)
                    .putExtra(Constants.RECIPE_IMAGE, data.recipe_image)
                    .putExtra(Constants.RECIPE_NAME, data.recipe_name)
                startActivity(act)
//            }
//            else
//                startActivity(Intent(requireActivity(), MemberShipActivity::class.java))

        }
        binding.rvTrendingRecycler.adapter = trendingAdapter

        binding.rvTrendingRecycler.addOnScrollListener(object :
            EndlessScrollViewListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
//                viewModel.getTrendingList(totalItemsCount,limit)
            }

        })
    }

}