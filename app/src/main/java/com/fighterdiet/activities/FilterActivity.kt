package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.VISIBLE
import android.widget.Toast
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
import com.fighterdiet.utils.Utils
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

    private var dietaryListModel: GetDietaryResponseModel? = null
    private var volumeListModel: GetVolumeResponseModel? = null
    private var mealListModel: GetMealResponseModel? = null

    private var currentScreenType: Int = -1

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
        binding.pbFilter.visibility = View.VISIBLE
        filterViewModel.getDietaryApi()
    }

    override fun setupObserver() {
        filterViewModel.getDietaryResource.observe(this,{
            when(it.status){
                Status.SUCCESS -> {
                    dietaryListModel = it.data?.data
                    Constants.RecipeFilter.selectedDietaryFilter.forEach { selectedDietaryFilter ->
                        if(selectedDietaryFilter.value.isChecked){
                            dietaryListModel?.let { dietaryResponseModel ->
                                dietaryResponseModel.result[selectedDietaryFilter.key].isChecked = selectedDietaryFilter.value.isChecked
                            }
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
                        if(it.value.isChecked){
                            volumeListModel?.let { volumeResponseModel ->
                                volumeResponseModel.result[it.key].isChecked = it.value.isChecked
                            }
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
                        if(it.value.isChecked){
                            mealListModel?.let { mealResponseModel ->
                                mealResponseModel.result[it.key].isChecked = it.value.isChecked
                            }
                        }

                    }

                    binding.pbFilter.visibility = View.GONE
                    Handler(Looper.getMainLooper()).postDelayed({
                        initialiseViewPager()
                    },10)
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

        dietaryCount = Constants.RecipeFilter.selectedDietaryFilter.size
        volumeCount = Constants.RecipeFilter.selectedVolumeFilter.size
        mealCount = Constants.RecipeFilter.selectedMealFilter.size

        val pagerAdapter =
            FilterPagerAdapter(
                supportFragmentManager, lifecycle
            )

        binding.tab.addTab(binding.tab.newTab().setText("Dietary Info"))
        binding.tab.addTab(binding.tab.newTab().setText("Volume"))
        binding.tab.addTab(binding.tab.newTab().setText("Meals"))

        dietaryListModel?.let {
            dietaryInfoFragment = DietryInfoFragment.newInstance(it)
            dietaryInfoFragment?.let { fragment ->
                pagerAdapter.addFragment(fragment)
            }
        }

        volumeListModel?.let {
            try {
                volumeListFragment = VolumeFragment.newInstance(it)
                volumeListFragment?.let { fragment ->
                        pagerAdapter.addFragment(fragment)
                    }
            } catch (e: Exception) {
                Toast.makeText(this, ""+e.printStackTrace(), Toast.LENGTH_SHORT).show()
            }
        }

        mealListModel?.let {
            mealListFragment = MealsFragment.newInstance()
            mealListFragment!!.passData(it)
            mealListFragment?.let { fragment ->
                pagerAdapter.addFragment(fragment)
            }
        }

        binding.viewPager.adapter=pagerAdapter
        TabLayoutMediator( binding.tab,binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()
    }

    @Synchronized
    override fun onClick(view: View?) {
        when(view?.id){

            R.id.tv_apply ->{
                Constants.RecipeFilter.isFilterApplied = true
                    //Constants.RecipeFilter.dietaryItemCount+Constants.RecipeFilter.volumeItemCount+Constants.RecipeFilter.mealItemCount != 0

                filterOps()

                startActivity(DashboardActivity.getStartIntent(this))
                Constants.DashboardDetails.isApiRequestNeeded = true
                finishAffinity()
            }

            R.id.tv_cancel ->{
                Constants.RecipeFilter.isFilterApplied = false
                finish()
            }

            R.id.tv_clear_all ->{
//                if(mealCount+volumeCount+dietaryCount > 0){
                    mealCount = 0
                    volumeCount = 0
                    dietaryCount = 0
                    Constants.RecipeFilter.totalFilterCount = 0
                    Constants.RecipeFilter.isFilterCleared = true
                    Constants.RecipeFilter.isFilterApplied = false
                    Constants.RecipeFilter.selectedMealFilter.clear()
                    Constants.RecipeFilter.selectedVolumeFilter.clear()
                    Constants.RecipeFilter.selectedDietaryFilter.clear()
                    Constants.DashboardDetails.isApiRequestNeeded = true

                    updateTotalFilterCountText()

                dietaryInfoFragment?.clearDietaryData()
                volumeListFragment?.clearVolumeData()
                mealListFragment?.clearMealData()
                isNewChanges = false

                    filterOps()
//                }
//                else{
//                    Utils.showToast(this, "Filter is already cleared!")
//                }
            }
        }
    }

    private fun filterOps() {
        Constants.RecipeFilter.selectedDietaryFilter.clear()
        Constants.RecipeFilter.selectedVolumeFilter.clear()
        Constants.RecipeFilter.selectedMealFilter.clear()

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

    override fun mealsInfoCount(pos: Int, id: Int, isItemAdd: Boolean) {
        mealListModel?.apply {
            this.result[pos].isChecked = isItemAdd
        }
        if(isItemAdd){
            mealCount += 1
        }
        else{
            mealCount -= 1
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
            dietaryCount += 1
        }
        else{
            dietaryCount -= 1
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
            volumeCount += 1
        }
        else{
            volumeCount -= 1
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

    override fun onStop() {
        super.onStop()
        if(!Constants.RecipeFilter.isFilterApplied){
            return
        }


    }

    fun isShowLoader(isShow: Boolean) {
        binding.pbFilter.visibility = if(isShow) View.VISIBLE else View.GONE
    }
}