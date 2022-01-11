package com.fighterdiet.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.activities.RecipeDetailsActivity
import com.fighterdiet.adapters.FavouriteFragmentRecyAdapter
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.model.responseModel.FavouriteListResponseModel
import com.fighterdiet.data.repository.FavouriteRepository
import com.fighterdiet.databinding.FragmentFavouriteBinding
import com.fighterdiet.interfaces.DashboardCallback
import com.fighterdiet.utils.*
import com.fighterdiet.viewModel.FavouriteViewModeProvider
import com.fighterdiet.viewModel.FavouriteViewModel

class FavouriteFragment(val dashboardCallback: DashboardCallback) : BaseFragment() {
    private var isLoadMore: Boolean = false
    private lateinit var viewModel: FavouriteViewModel
    lateinit var binding: FragmentFavouriteBinding
    private lateinit var favouriteAdapter: FavouriteFragmentRecyAdapter
  //  var homeList: ArrayList<HomeModel> = ArrayList()
    var favouriteList: ArrayList<FavouriteListResponseModel.Favourite> = ArrayList()
    var offset = 0
    var limit = 8

    companion object{
        fun initFragment(dashboardCallback: DashboardCallback):FavouriteFragment{
            return FavouriteFragment(dashboardCallback)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
        isLoadMore = false
        setupViewModel()
        setupObserver()

        binding.rvFavouriteRecyclerSwipe.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                viewModel.getFavouriteList(0,8)

            },50)
        }
    }
    fun setupViewModel() {
        viewModel = ViewModelProvider(this, FavouriteViewModeProvider(FavouriteRepository(RetrofitBuilder.apiService)))
            .get(FavouriteViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        if(::viewModel.isInitialized){
//            isLoadMore = false
            dashboardCallback.onStartLoader()
//            setUpFavouriteRecyclerView()
            viewModel.getFavouriteList(offset, limit)
        }
    }

    fun setupObserver() {
        viewModel.favouriteListResource.observe(viewLifecycleOwner, {
            when(it.status){
                Status.SUCCESS -> {
                    dashboardCallback.onDataLoaded()
//                    binding.pbFav.visibility = View.GONE
                    binding.tvNoData.visibility = View.GONE
                    if (!it.data?.data?.result.isNullOrEmpty()){
                        if(!isLoadMore)
                            favouriteList.clear()
                        favouriteList.addAll(it.data?.data?.result!!)
                    }
                    else{
                        if(!isLoadMore){
                            favouriteList.clear()
                        }
                        if(favouriteList.isEmpty())
                            binding.tvNoData.visibility = View.VISIBLE


                    }
                    isLoadMore = false
                    favouriteAdapter.notifyDataSetChanged()


                    if(binding.rvFavouriteRecyclerSwipe.isRefreshing){
                        // trendingAdapter.clearAll()
                        binding.rvFavouriteRecyclerSwipe.isRefreshing = false
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })
    }
    private fun initialise() {
      //  setUpHomeList()
        setUpFavouriteRecyclerView()
    }

    private fun setUpFavouriteRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        binding.rvFavouriteRecycler.layoutManager = layoutManager
        favouriteAdapter = FavouriteFragmentRecyAdapter(activity, favouriteList) { position, favModel ->
            if(!PrefManager.getBoolean(PrefManager.IS_LOGGED_IN)){
                Utils.loginAlertDialog(requireActivity())
                return@FavouriteFragmentRecyAdapter
            }

            Constants.DashboardDetails.isApiRequestNeeded = false
//            if(PrefManager.getBoolean(PrefManager.IS_SUBSCRIBED)){
            val act = RecipeDetailsActivity.getStartIntent(requireContext())
                .putExtra(Constants.RECIPE_ID, favModel.recipe_id)
                .putExtra(Constants.RECIPE_IMAGE, favModel.recipe_image)
                .putExtra(Constants.RECIPE_NAME, favModel.recipe_name)
            startActivity(act)
//            }
//            else
//                startActivity(Intent(requireActivity(), MemberShipActivity::class.java))
        }
        binding.rvFavouriteRecycler.adapter = favouriteAdapter

        binding.rvFavouriteRecycler.addOnScrollListener(object :
            EndlessScrollViewListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                viewModel.getFavouriteList(totalItemsCount,limit)
                isLoadMore = true
            }

        })
    }

}