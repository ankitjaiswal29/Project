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
        var volumeCount :Int = 0
        var mealCount :Int = 0
        var dietaryCount :Int = 0
        const val TAG = "filterActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, FilterActivity::class.java)
        }
    }

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
        setupViewModel()
        setupObserver()
        setupUI()
        initialise()
    }

    override fun setupUI() {

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
                    Handler(Looper.getMainLooper()).postDelayed({
                        initialiseViewPager()
                    },300)
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
            dietaryInfoFragment = DietryInfoFragment.newInstance(it)
            dietaryInfoFragment?.let { fragment ->
                pagerAdapter.addFragment(fragment, "Dietary Info")
            }
        }
        volumeListModel?.let {
            volumeListFragment = VolumeFragment.newInstance(it)
            volumeListFragment?.let { fragment ->
                    pagerAdapter.addFragment(fragment, "Volume")
                }
        }
        mealListModel?.let {
            mealListFragment = MealsFragment.newInstance(it)
            mealListFragment?.let { fragment ->
                pagerAdapter.addFragment(fragment, "Meals")
            }
        }

        binding.viewPager.adapter=pagerAdapter
        TabLayoutMediator( binding.tab,   binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()
//        binding.viewPager.currentItem = currentFragmentPos
    }

    override fun onClick(view: View?) {
        when(view?.id){

            R.id.tv_apply ->{
//                val result = Intent()
                finish()
            }

            R.id.tv_cancel ->{
                finish()
            }

            R.id.tv_clear_all ->{
//                binding.clBottom.visibility = GONE
                binding.tvFilterCount.text = ""
                when(currentScreenType){
                    0->{
                        dietaryListModel?.let { list->
                            dietaryInfoFragment?.clearDietaryData()
                            dietaryCount = 0
                        }

                    }
                    1->{
                        volumeListModel?.let { list->
                            volumeListFragment?.clearVolumeData()
                            volumeCount = 0
                        }
                    }
                    2->{
                        mealListModel?.let { list->
                            mealListFragment?.clearMealData()
                            mealCount = 0
                        }
                    }
                }
            }
        }
    }


    override fun mealsInfoCount(pos: Int, id: Int, isItemAdd: Boolean) {
//        Utils.showToast(this,count.toString())
        mealListModel?.apply {
            this.result[pos].isChecked = isItemAdd
        }
        if(isItemAdd){
            mealCount++
        }
        else{
            mealCount--
        }
        binding.clBottom.visibility = VISIBLE
        binding.tvFilterCount.text = "$mealCount filters selected"
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
        binding.clBottom.visibility = VISIBLE
        binding.tvFilterCount.text = "$dietaryCount filters selected"
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
        binding.clBottom.visibility = VISIBLE
        binding.tvFilterCount.text = "$volumeCount filters selected"
    }

    override fun initFilterSelectionUi(screenType: Int) {
        currentScreenType = screenType
        when(screenType){
            0 -> {
                binding.tvFilterCount.text = "$dietaryCount filters selected"
            }
            1 -> {
                binding.tvFilterCount.text = "$volumeCount filters selected"
            }
            2 -> {
                binding.tvFilterCount.text = "$mealCount filters selected"

            }
        }
    }
}