package com.fighterdiet.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.activities.LoginActivity
import com.fighterdiet.activities.MemberShipActivity
import com.fighterdiet.activities.RecipeDetailsActivity
import com.fighterdiet.adapters.HomeRecipeListRecyclerAdapter
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.model.responseModel.RecipeListResponseModel
import com.fighterdiet.data.repository.HomeRepository
import com.fighterdiet.data.repository.HomeViewModelProvider
import com.fighterdiet.databinding.FragmentHomeBinding
import com.fighterdiet.utils.Constants
import com.fighterdiet.utils.EndlessScrollViewListener
import com.fighterdiet.utils.PrefManager
import com.fighterdiet.utils.Status
import com.fighterdiet.viewModel.HomeViewModel

class HomeFragment : BaseFragment() {
    private var isFilterMode: Boolean = false
    private var isSearchMode: Boolean = false
    private var mSearchedKeyword: String = ""
    private lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentHomeBinding
    private lateinit var recipeListAdapter: HomeRecipeListRecyclerAdapter
    var recipeList: ArrayList<RecipeListResponseModel.Recipies> = ArrayList()

    var offset = 0
    var limit = 8

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObserver()
        initialize()
    }

    override fun onStart() {
        super.onStart()
        if(Constants.DashboardDetails.isApiRequestNeeded){
            getRecipes("",offset,limit)
            Constants.DashboardDetails.isApiRequestNeeded = true
        }
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
        viewModel.getRecipeList(searchKeys, startFrom, endTo, selectedDietaryMap, selectedVolumeMap, selectedMealMap)
    }

    fun setupViewModel() {
            viewModel = ViewModelProvider(this, HomeViewModelProvider(HomeRepository(RetrofitBuilder.apiService)))
            .get(HomeViewModel::class.java)
    }

    fun setupObserver() {
        viewModel.getRecipeListResource().observe(viewLifecycleOwner, {
            when(it.status){
                Status.SUCCESS -> {
                    binding.tvNoData.visibility = GONE

                    if(isFilterMode||isSearchMode)
                    {
                        if(!isSearchMode){
                            binding.tvFilterCount.text = "${Constants.RecipeFilter.totalFilterCount} ${ getString(R.string.filters_selected_tap_to_clear) }"
                            binding.tvFilterCount.visibility = View.VISIBLE
                        }
                        if(!it.data?.data?.result.isNullOrEmpty())
                            recipeListAdapter.updateAll(it.data?.data?.result!!, isSearchMode)
                        else{
                            if(isSearchMode)
                                binding.tvNoData.visibility = View.VISIBLE
                        }
                        return@observe
                    }

                    binding.tvFilterCount.visibility = GONE
                    Constants.DashboardDetails.recipiesModel = it.data?.data

                    if(!it.data?.data?.result.isNullOrEmpty())
                        recipeListAdapter.addAll(it.data?.data?.result!!)
                    recipeListAdapter.notifyDataSetChanged()


                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })
    }

    private fun initialize() {
        if (Constants.isQuestonnaireCompleted) {
            binding.daysLay.visibility = VISIBLE
        } else {
            binding.daysLay.visibility = GONE
        }
        initClickListeners()
        setUpHomeRecyclerView()
    }

    @Synchronized
    private fun initClickListeners() {
        binding.tvFilterCount.setOnClickListener {
            Constants.RecipeFilter.selectedVolumeFilter.clear()
            Constants.RecipeFilter.selectedDietaryFilter.clear()
            Constants.RecipeFilter.selectedMealFilter.clear()
            Constants.RecipeFilter.totalFilterCount = 0
            Constants.RecipeFilter.isFilterApplied = false
            binding.tvFilterCount.visibility = GONE
            recipeListAdapter.clearAll()
            Handler(Looper.getMainLooper()).postDelayed({
                getRecipes("", offset, limit)
            },100)
        }
    }

    private fun setUpHomeRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvHomeRecycler.layoutManager = layoutManager
        recipeListAdapter = HomeRecipeListRecyclerAdapter(requireActivity(), recipeList) { position, recipe ->
            if(!PrefManager.getBoolean(PrefManager.IS_LOGGED_IN)){
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                return@HomeRecipeListRecyclerAdapter
            }

            Constants.DashboardDetails.isApiRequestNeeded = false
            val act = RecipeDetailsActivity.getStartIntent(requireContext())
                .putExtra(Constants.RECIPE_ID, recipe.id)
                .putExtra(Constants.RECIPE_IMAGE, recipe.recipe_image)
                .putExtra(Constants.RECIPE_NAME, recipe.recipe_name)
            when(Constants.DashboardDetails.recipiesModel?.is_subscribed){
                "1" -> {

                    startActivity(act)
                }
                "0" -> {
                    if(position == 0){
                        startActivity(MemberShipActivity.getStartIntent(requireContext()))
                    }
                    else{
                        startActivity(act)
                    }
                }

            }
        }
        binding.rvHomeRecycler.adapter = recipeListAdapter

        binding.rvHomeRecycler.addOnScrollListener(object :
            EndlessScrollViewListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                if(!isSearchMode)
                    getRecipes(mSearchedKeyword, totalItemsCount, limit)
            }

        })
    }
}