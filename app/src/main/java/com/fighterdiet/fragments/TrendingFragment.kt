package com.fighterdiet.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.activities.RecipeDetailsActivity
import com.fighterdiet.adapters.TrendingFragmentRecyAdapter
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.model.responseModel.TrendingListResponseModel
import com.fighterdiet.data.repository.TrendingRepository
import com.fighterdiet.databinding.FragmentTrendingBinding
import com.fighterdiet.interfaces.DashboardCallback
import com.fighterdiet.utils.*
import com.fighterdiet.viewModel.TrendingViewModel
import com.fighterdiet.viewModel.TrendingViewModelProvider

class TrendingFragment(val dashboardCallback: DashboardCallback) : BaseFragment() {
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

    companion object{
        fun initFragment(dashboardCallback: DashboardCallback):TrendingFragment{
            return TrendingFragment(dashboardCallback)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
        setupViewModel()
        setupObserver()

        binding.rvTrendingRecyclerSwipe.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                viewModel.getTrendingList(0, 8)

            },50)
        }
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

        dashboardCallback.onStartLoader()
        viewModel.getTrendingList(offset, limit)
    }
    fun setupObserver() {
        viewModel.trendingListResource.observe(viewLifecycleOwner, {
            when(it.status){
                Status.SUCCESS -> {
                    if(binding.rvTrendingRecyclerSwipe.isRefreshing){
                        binding.rvTrendingRecyclerSwipe.isRefreshing = false
                        trendingAdapter.clearAll()
                    }

                    if (it.data?.data?.result.isNullOrEmpty())
                        return@observe

                    dashboardCallback.onDataLoaded()
//                    binding.pbTrending.visibility = View.GONE
                    trendingList.addAll(it.data?.data?.result!!)
                    val currSize = binding.rvTrendingRecycler.adapter?.itemCount?:0
                    if(currSize>0)
                        trendingAdapter.notifyItemRangeInserted(currSize, trendingList.size - 1);

                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    binding.rvTrendingRecyclerSwipe.isRefreshing = false

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

            Constants.DashboardDetails.isApiRequestNeeded = false
//            if(PrefManager.getBoolean(PrefManager.IS_SUBSCRIBED)){
                val act = RecipeDetailsActivity.getStartIntent(requireContext())
                    .putExtra(Constants.RECIPE_ID, data.id.toString())
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