package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.adapters.FilterPagerAdapter
import com.fighterdiet.adapters.MyFragmentStateAdapter
import com.fighterdiet.databinding.ActivityFilterBinding
import com.fighterdiet.fragments.*
import com.fighterdiet.utils.Utils
import com.google.android.material.tabs.TabLayoutMediator

class FilterActivity : BaseActivity(), View.OnClickListener ,
    DietryInfoFragment.DietaryInfoInterface, MealsFragment.MealsInfoInterface,
    VolumeFragment.VolumeFragInterface {
    private lateinit var binding : ActivityFilterBinding
    private lateinit var tabTitles:Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_filter)
        initialise()
    }

    override fun setupUI() {

    }

    override fun setupViewModel() {

    }

    override fun setupObserver() {

    }

    private fun initialise() {
        tabTitles = arrayOf("Dietary Info","Volume","Meals")

        binding.tvApply.setOnClickListener(this)
        binding.tvCancel.setOnClickListener(this)
        binding.tvClearAll.setOnClickListener(this)

        initialiseViewPager()
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

        pagerAdapter.addFragment(DietryInfoFragment(), "Dietary Info")
        pagerAdapter.addFragment(VolumeFragment(), "Volume")
        pagerAdapter.addFragment(MealsFragment(), "Meals")

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

    override fun dietaryInfoCount(count: Int) {
//        Utils.showToast(this,count.toString())
        binding.clBottom.visibility = VISIBLE
        binding.tvFilterCount.text = "$count filters selected"
    }

    override fun mealsInfoCount(count: Int) {
//        Utils.showToast(this,count.toString())
        binding.clBottom.visibility = VISIBLE
        binding.tvFilterCount.text = "$count filters selected"
    }

    override fun volumefragCount(count: Int) {
        binding.clBottom.visibility = VISIBLE
        binding.tvFilterCount.text = "$count filters selected"
    }
}