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
import com.fighterdiet.data.model.responseModel.GetAllergyResponseModel
import com.fighterdiet.data.model.responseModel.GetMealResponseModel
import com.fighterdiet.data.model.responseModel.GetVolumeResponseModel
import com.fighterdiet.data.repository.FilterRecipeModelProvider
import com.fighterdiet.data.repository.FilterRecipeRepository
import com.fighterdiet.databinding.ActivityFilterBinding
import com.fighterdiet.fragments.*
import com.fighterdiet.utils.Status
import com.fighterdiet.viewModel.FilterRecipeViewModel
import com.google.android.material.tabs.TabLayoutMediator

class FilterActivity : BaseActivity(), View.OnClickListener ,
    DietryInfoFragment.DietaryInfoInterface, MealsFragment.MealsInfoInterface,
    VolumeFragment.VolumeFragInterface {
    private var allergyList: GetAllergyResponseModel? = null
    private var volumeList: GetVolumeResponseModel? = null
    private var mealList: GetMealResponseModel? = null
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
    }

    override fun setupObserver() {
        filterViewModel.getAllergyResource.observe(this,{
            when(it.status){

                Status.SUCCESS -> {
                    filterViewModel.getVolumeApi()
                    allergyList = it.data?.data
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
                    filterViewModel.getMealApi()
                    volumeList = it.data?.data
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
                    mealList = it.data?.data
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


    companion object {
        var count :Int = 0
        const val TAG = "filterActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, FilterActivity::class.java)
        }
    }

    private fun initialiseViewPager() {
        val pagerAdapter =
            FilterPagerAdapter(
                supportFragmentManager, lifecycle
            )

        binding.tab.addTab(binding.tab.newTab().setText("Dietary Info"));
        binding.tab.addTab(binding.tab.newTab().setText("Volume"));
        binding.tab.addTab(binding.tab.newTab().setText("Meals"));

        allergyList?.let {
            pagerAdapter.addFragment(DietryInfoFragment.newInstance(it), "Dietary Info")
        }
        volumeList?.let {
            pagerAdapter.addFragment(VolumeFragment.newInstance(it), "Volume")
        }
        mealList?.let {
            pagerAdapter.addFragment(MealsFragment.newInstance(it), "Meals")
        }

        binding.viewPager.adapter=pagerAdapter
        TabLayoutMediator( binding.tab,   binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()
    }

    override fun onClick(view: View?) {
        when(view?.id){

            R.id.tv_apply ->{
                finish()
            }

            R.id.tv_cancel ->{
                finish()
            }

            R.id.tv_clear_all ->{
//                binding.clBottom.visibility = GONE
                binding.tvFilterCount.text = ""
            }


        }
    }


    override fun mealsInfoCount(pos: Int, id: Int) {
//        Utils.showToast(this,count.toString())
        binding.clBottom.visibility = VISIBLE
        binding.tvFilterCount.text = "$count filters selected"
    }

    override fun dietarySelectedInfo(pos: Int, id: Int) {

        binding.clBottom.visibility = VISIBLE
        binding.tvFilterCount.text = "$pos filters selected"
    }

    override fun volumefragCount(pos: Int, id: Int) {
        binding.clBottom.visibility = VISIBLE
        binding.tvFilterCount.text = "$count filters selected"
    }
}