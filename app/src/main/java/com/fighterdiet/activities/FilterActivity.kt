package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.VISIBLE
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.R
import com.fighterdiet.adapters.FilterPagerAdapter
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.model.responseModel.GetDietaryResponseModel
import com.fighterdiet.data.model.responseModel.GetMealResponseModel
import com.fighterdiet.data.model.responseModel.GetVolumeResponseModel
import com.fighterdiet.data.repository.FilterRecipeModelProvider
import com.fighterdiet.data.repository.FilterRecipeRepository
import com.fighterdiet.databinding.ActivityFilterBinding
import com.fighterdiet.fragments.*
import com.fighterdiet.utils.Constants
import com.fighterdiet.utils.Status
import com.fighterdiet.viewModel.FilterRecipeViewModel
import com.google.android.material.tabs.TabLayoutMediator

class FilterActivity : BaseActivity(), View.OnClickListener ,
    DietryInfoFragment.DietaryInfoInterface, MealsFragment.MealsInfoInterface,
    VolumeFragment.VolumeFragInterface {

    companion object {
        const val TAG = "filterActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, FilterActivity::class.java)
        }
    }

    private var isNewChanges: Boolean = false
    var volumeCount :Int = 0
    var mealCount :Int = 0
    var dietaryCount :Int = 0
    var newChangesInDietarySelection = false
    var newChangesInVolumeSelection = false
    var newChangesInMealSelection = false
    private var mealListFragment: MealsFragment? = null
    private var volumeListFragment: VolumeFragment? = null
    private var dietaryInfoFragment: DietryInfoFragment? = null
    private var currentScreenType: Int = -1
    private var dietaryListModel: GetDietaryResponseModel? = null
    private var volumeListModel: GetVolumeResponseModel? = null
    private var mealListModel: GetMealResponseModel? = null
    private lateinit var binding : ActivityFilterBinding
    private lateinit var tabTitles:Array<String>
    private lateinit var filterViewModel : FilterRecipeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_filter)
        setupUI()
        setupViewModel()
        setupObserver()
        initialise()
    }

    override fun setupUI() {
        updateIsFilterAppliedUI()
    }

    private fun updateIsFilterAppliedUI() {
        binding.tvFilterCount.text = "${Constants.RecipeFilter.totalFilterCount} ${getString(R.string.filters_selected)}"

    }

    override fun setupViewModel() {
        filterViewModel = ViewModelProvider(this, FilterRecipeModelProvider(FilterRecipeRepository(RetrofitBuilder.apiService)))
            .get(FilterRecipeViewModel::class.java)
        filterViewModel.getDietaryApi()
    }

    override fun setupObserver() {
        filterViewModel.getDietaryResource.observe(this,{
            when(it.status){

                Status.SUCCESS -> {
                    dietaryListModel = it.data?.data
                    Constants.RecipeFilter.selectedDietaryFilter.forEach {
                        dietaryCount++
                        dietaryListModel?.let { dietaryResponseModel ->
                            dietaryResponseModel.result[it.key].isChecked = it.value.isChecked
                        }
                    }
                    filterViewModel.getVolumeApi()
                }

                Status.LOADING -> {

                }

                Status.ERROR -> {

                }

            }
        })

        filterViewModel.getVolumeResource.observe(this,{
            when(it.status){

                Status.SUCCESS -> {
                    volumeListModel = it.data?.data
                    Constants.RecipeFilter.selectedVolumeFilter.forEach {
                        volumeCount++
                        volumeListModel?.let { volumeResponseModel ->
                            volumeResponseModel.result[it.key].isChecked = it.value.isChecked
                        }
                    }
                    filterViewModel.getMealApi()
                }

                Status.LOADING -> {

                }

                Status.ERROR -> {

                }

            }
        })

        filterViewModel.getMealResource.observe(this,{
            when(it.status){

                Status.SUCCESS -> {
                    mealListModel = it.data?.data

                    Constants.RecipeFilter.selectedMealFilter.forEach {
                        mealCount++
                        mealListModel?.let { mealResponseModel ->
                            mealResponseModel.result[it.key].isChecked = it.value.isChecked
                        }
                    }

                    Handler(Looper.getMainLooper()).postDelayed({
                        initialiseViewPager()
                    },100)
                }

                Status.LOADING -> {

                }

                Status.ERROR -> {

                }

            }
        })
    }

    private fun initialise() {
        tabTitles = arrayOf("Dietary Info","Volume","Meals")
        binding.tvApply.setOnClickListener(this)
        binding.tvCancel.setOnClickListener(this)
        binding.tvClearAll.setOnClickListener(this)
    }


    private fun initialiseViewPager() {
        val pagerAdapter =
            FilterPagerAdapter(
                supportFragmentManager, lifecycle
            )

        binding.tab.addTab(binding.tab.newTab().setText("Dietary Info"));
        binding.tab.addTab(binding.tab.newTab().setText("Volume"));
        binding.tab.addTab(binding.tab.newTab().setText("Meals"));

        dietaryListModel?.let {
            dietaryInfoFragment = DietryInfoFragment.newInstance(it, Constants.RecipeFilter.isDietaryListCleared)
            dietaryInfoFragment?.let { fragment ->
                pagerAdapter.addFragment(fragment, "Dietary Info")
            }
        }

        volumeListModel?.let {
            volumeListFragment = VolumeFragment.newInstance(it, Constants.RecipeFilter.isVolumeListCleared)
            volumeListFragment?.let { fragment ->
                    pagerAdapter.addFragment(fragment, "Volume")
                }
        }

        mealListModel?.let {
            mealListFragment = MealsFragment.newInstance(it, Constants.RecipeFilter.isMealListCleared)
            mealListFragment?.let { fragment ->
                pagerAdapter.addFragment(fragment, "Meals")
            }
        }

        binding.viewPager.adapter=pagerAdapter
        TabLayoutMediator( binding.tab,   binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()
    }

    @Synchronized
    override fun onClick(view: View?) {
        when(view?.id){

            R.id.tv_apply ->{
                Constants.RecipeFilter.isFilterApplied = true
                finish()
            }

            R.id.tv_cancel ->{
                Constants.RecipeFilter.isFilterApplied = false
                finish()
            }

            R.id.tv_clear_all ->{
                mealCount = 0
                volumeCount = 0
                dietaryCount = 0
                Constants.RecipeFilter.totalFilterCount = 0
                Constants.RecipeFilter.selectedMealFilter.clear()
                Constants.RecipeFilter.selectedDietaryFilter.clear()
                Constants.RecipeFilter.selectedVolumeFilter.clear()
                updateTotalFilterCountText()
                when(currentScreenType){
                    0-> {
                        dietaryInfoFragment?.clearDietaryData()
                        Constants.RecipeFilter.isDietaryListCleared = true
                    }

                    1-> {
                        volumeListFragment?.clearVolumeData()
                        Constants.RecipeFilter.isVolumeListCleared = true
                    }

                    2-> {
                        mealListFragment?.clearMealData()
                        Constants.RecipeFilter.isMealListCleared = true
                    }
                }
                isNewChanges = false
            }
        }
    }


    override fun mealsInfoCount(pos: Int, id: Int, isItemAdd: Boolean) {
        mealListModel?.apply {
            this.result[pos].isChecked = isItemAdd
        }
        if(isItemAdd){
            mealCount++
        }
        else{
            mealCount--
        }
        updateTotalFilterCountText()
        binding.clBottom.visibility = VISIBLE
        newChangesInMealSelection = true
    }

    override fun dietarySelectedInfo(pos: Int, id: Int, isItemAdd: Boolean) {
        dietaryListModel?.apply {
            this.result[pos].isChecked = isItemAdd
        }
        if(isItemAdd){
            dietaryCount++
        }
        else{
            dietaryCount--
        }
        updateTotalFilterCountText()
        binding.clBottom.visibility = VISIBLE
        newChangesInDietarySelection = true
    }

    override fun volumefragCount(pos: Int, id: Int, isItemAdd: Boolean) {
        volumeListModel?.apply {
            this.result[pos].isChecked = isItemAdd
        }
        if(isItemAdd){
            volumeCount++
        }
        else{
            volumeCount--
        }
        updateTotalFilterCountText()
        binding.clBottom.visibility = VISIBLE
        newChangesInVolumeSelection = true
    }

    private fun updateTotalFilterCountText() {
        binding.tvFilterCount.text = "${dietaryCount+volumeCount+mealCount} ${getString(R.string.filters_selected)}"
    }

    override fun getCurrFragmentType(screenType: Int) {
        currentScreenType = screenType
    }

    override fun onPause() {
        super.onPause()

        if(!Constants.RecipeFilter.isFilterApplied){
            return
        }

        if(dietaryCount==0){
            Constants.RecipeFilter.selectedDietaryFilter.clear()
        }else{
            if(newChangesInDietarySelection){
                Constants.RecipeFilter.selectedDietaryFilter.forEach {
                    it.value.isChecked = false
                }
                Constants.RecipeFilter.isDietaryListCleared = false
            }
        }

        if(mealCount==0){
            Constants.RecipeFilter.selectedMealFilter.clear()
        }else{
            if(newChangesInMealSelection){
                Constants.RecipeFilter.selectedMealFilter.forEach {
                    it.value.isChecked = false
                }
                Constants.RecipeFilter.isMealListCleared = false
            }
        }

        if(volumeCount==0){
            Constants.RecipeFilter.selectedVolumeFilter.clear()
        }else{
            if(newChangesInVolumeSelection){
                Constants.RecipeFilter.selectedVolumeFilter.forEach {
                    it.value.isChecked = false
                }
                Constants.RecipeFilter.isVolumeListCleared = false
            }
        }


        val totalSelection = dietaryCount+mealCount+volumeCount
        if(Constants.RecipeFilter.totalFilterCount != totalSelection){
            Constants.RecipeFilter.totalFilterCount = totalSelection
        }

        dietaryListModel?.let { model ->
            model.result.forEachIndexed { index, result ->
                if(result.isChecked){
                     Constants.RecipeFilter.selectedDietaryFilter[index] = result
                }
            }
        }

        volumeListModel?.let { model ->
            model.result.forEachIndexed { index, result ->
                if(result.isChecked){
                    Constants.RecipeFilter.selectedVolumeFilter[index] = result
                }
            }
        }

        mealListModel?.let { model ->
            model.result.forEachIndexed { index, result ->
                if(result.isChecked){
                    Constants.RecipeFilter.selectedMealFilter[index] = result
                }
            }
        }

    }


}