package com.fighterdiet.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.activities.MemberShipActivity
import com.fighterdiet.activities.RecipeDetailsActivity
import com.fighterdiet.adapters.HomeRecipeListRecyclerAdapter
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.model.responseModel.RecipeListResponseModel
import com.fighterdiet.data.repository.HomeRepository
import com.fighterdiet.data.repository.HomeViewModelProvider
import com.fighterdiet.databinding.FragmentHomeBinding
import com.fighterdiet.interfaces.DashboardCallback
import com.fighterdiet.utils.*
import com.fighterdiet.viewModel.HomeViewModel

class HomeFragment : BaseFragment() {
    private var fromDeepLink: Boolean = false
    private var sharedRecipeId: String = ""
    private var sharedRecipeImage: String = ""
    private var sharedRecipeTitle: String = ""
    private var currentPage: Int = 1
    private var totalCountOfData: Int = -1
    private var isFilterMode: Boolean = false
    private var isSearchMode: Boolean = false
    private var mSearchedKeyword: String = ""
    private lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentHomeBinding
    lateinit var recipeListAdapter: HomeRecipeListRecyclerAdapter
    var recipeList: ArrayList<RecipeListResponseModel.Recipies> = ArrayList()
    var dashboardCallback: DashboardCallback?=null
    var offset = 0
    var limit = 8


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        setupViewModel()
        setupObserver()
        initClickListeners()
        setUpHomeRecyclerView()
        initialize()
        getRecipes("",offset,limit)
        return binding.root
    }

    fun passCallback(dashboardCallback: DashboardCallback){
        this.dashboardCallback=dashboardCallback
    }

    fun passArgument(recipeId: String?, recipeImage: String?, recipeTitle: String?) {
        recipeId?.let {
            sharedRecipeId = it
        }
        recipeImage?.let {
            sharedRecipeImage = it
        }
        recipeTitle?.let {
            sharedRecipeTitle = it
        }

        fromDeepLink = true
    }


    fun getRecipes(
        searchKeys: String,
        startFrom: Int,
        endTo: Int
    ){
        isFilterMode = false
        isSearchMode = false
        mSearchedKeyword = ""

        if(searchKeys.isNotBlank()){
            isSearchMode = true
            mSearchedKeyword = searchKeys
        }

        val selectedDietaryMap = HashMap<String, Int>()
        if(Constants.RecipeFilter.selectedDietaryFilter.isNotEmpty()){
            isFilterMode = true
            Constants.RecipeFilter.selectedDietaryFilter.forEach {
                selectedDietaryMap["allergy_id[${it.key}]"] = it.value.allergy_id
            }
        }

        val selectedVolumeMap = HashMap<String, Int>()
        if(Constants.RecipeFilter.selectedVolumeFilter.isNotEmpty()){
            isFilterMode = true
            Constants.RecipeFilter.selectedVolumeFilter.forEach {
                selectedVolumeMap["volume_id[${it.key}]"] = it.value.volume_id
            }
        }

        val selectedMealMap = HashMap<String, Int>()
        if(Constants.RecipeFilter.selectedMealFilter.isNotEmpty()){
            isFilterMode = true
            Constants.RecipeFilter.selectedMealFilter.forEach {
                selectedMealMap["meal_id[${it.key}]"] = it.value.meal_id
            }
        }
        dashboardCallback?.onStartLoader()
        viewModel.getRecipeList(searchKeys, startFrom, endTo, selectedDietaryMap, selectedVolumeMap, selectedMealMap)
    }

    fun setupViewModel() {
        viewModel = ViewModelProvider(this, HomeViewModelProvider(HomeRepository(RetrofitBuilder.apiService)))
            .get(HomeViewModel::class.java)
    }

    fun setupObserver() {
        viewModel.getRecipeListResource().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    dashboardCallback?.onDataLoaded()
                    binding.tvNoData.visibility = GONE

                    Constants.DashboardDetails.recipiesModel = it.data?.data

                    when (Constants.DashboardDetails.recipiesModel?.is_subscribed) {
                        "0", "expired" -> {
                            PrefManager.putBoolean(PrefManager.IS_SUBSCRIBED, false)
                            if(fromDeepLink)
                                startActivity(MemberShipActivity.getStartIntent(requireContext()))
                        }
                        else -> {
                            PrefManager.putBoolean(PrefManager.IS_SUBSCRIBED, true)
                            //recipedetaills
                            if(fromDeepLink)
                            {
                                val activityIntent = RecipeDetailsActivity.getStartIntent(requireContext())
                                activityIntent.putExtra(Constants.RECIPE_ID, sharedRecipeId)
                                activityIntent.putExtra(Constants.RECIPE_IMAGE, sharedRecipeImage)
                                activityIntent.putExtra(Constants.RECIPE_NAME, sharedRecipeTitle)
                                startActivity(activityIntent)
                            }
                        }
                    }

                    totalCountOfData = it.data?.data?.totalRecord ?: 0

                    if (totalCountOfData == 0) {
                        binding.tvNoData.visibility = View.VISIBLE
                        return@observe
                    }

                    if (binding.rvHomeRecyclerSwipe.isRefreshing) {
                        recipeListAdapter.clearAll()
                        binding.rvHomeRecyclerSwipe.isRefreshing = false
                    }

                    if (isFilterMode || isSearchMode) {
                        if (!isSearchMode) {
                            if (Constants.RecipeFilter.totalFilterCount != 0) {
                                binding.tvFilterCount.text =
                                    "${Constants.RecipeFilter.totalFilterCount} ${getString(R.string.filters_selected_tap_to_clear)}"
                                binding.tvFilterCount.visibility = View.VISIBLE
                            } else {
                                binding.tvFilterCount.visibility = View.GONE
                            }
                        }
                        if (!it.data?.data?.result.isNullOrEmpty())
                            recipeListAdapter.updateAll(
                                it.data?.data?.result!!,
                                isSearchMode,
                                mSearchedKeyword
                            )
                        else {
                            if (totalCountOfData == 0)
                                binding.tvNoData.visibility = View.VISIBLE
                        }
                        return@observe
                    }

                    binding.tvFilterCount.visibility = GONE

                    if (!it.data?.data?.result.isNullOrEmpty()) {
                        recipeListAdapter.addAll(it.data?.data?.result!!, mSearchedKeyword)

                    }
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
                    binding.rvHomeRecyclerSwipe.isRefreshing = false
                }
            }
        }
    }

    private fun initialize() {
        if (Constants.isQuestonnaireCompleted) {
            binding.daysLay.visibility = VISIBLE
        } else {
            binding.daysLay.visibility = GONE
        }
    }


    private fun initClickListeners() {
        binding.tvFilterCount.setOnClickListener {
            Constants.RecipeFilter.selectedVolumeFilter.clear()
            Constants.RecipeFilter.selectedDietaryFilter.clear()
            Constants.RecipeFilter.selectedMealFilter.clear()
            Constants.RecipeFilter.totalFilterCount = 0
            Constants.RecipeFilter.isFilterApplied = false
            binding.tvFilterCount.visibility = GONE
            recipeListAdapter.clearAll()
            dashboardCallback?.onStartLoader()
            Handler(Looper.getMainLooper()).postDelayed({
                getRecipes("", offset, limit)
            },50)
        }

        binding.rvHomeRecyclerSwipe.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                getRecipes("", 0, 8)
            },50)
        }

    }

    fun setUpHomeRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvHomeRecycler.layoutManager = layoutManager
        recipeListAdapter = HomeRecipeListRecyclerAdapter(requireActivity(), recipeList) { position, recipe ->
            if(!PrefManager.getBoolean(PrefManager.IS_LOGGED_IN)){
                Utils.loginAlertDialog(requireActivity())
                return@HomeRecipeListRecyclerAdapter
            }

            val act = RecipeDetailsActivity.getStartIntent(requireContext())
                .putExtra(Constants.RECIPE_ID, recipe.id.toString())
                .putExtra(Constants.RECIPE_IMAGE, recipe.recipe_image)
                .putExtra(Constants.RECIPE_NAME, recipe.recipe_name)

            when(Constants.DashboardDetails.recipiesModel?.is_subscribed){
                "0", "expired" -> {
                    startActivity(MemberShipActivity.getStartIntent(requireContext()))
                }
                else -> {
                    Constants.DashboardDetails.isApiRequestNeeded = false
                    startActivity(act)
                }
            }
        }

        binding.rvHomeRecycler.adapter = recipeListAdapter
        binding.rvHomeRecycler.addOnScrollListener(object :
            EndlessScrollViewListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                if(page*limit > totalItemsCount){
                    Log.d("End","End of file")
                }else{
                    getRecipes(mSearchedKeyword, totalItemsCount, limit)
                }
            }

        })
    }

}