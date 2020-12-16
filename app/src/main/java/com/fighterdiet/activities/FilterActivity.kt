package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.adapters.FilterPagerAdapter
import com.fighterdiet.adapters.MyFragmentStateAdapter
import com.fighterdiet.databinding.ActivityFilterBinding
import com.fighterdiet.fragments.*
import com.google.android.material.tabs.TabLayoutMediator

class FilterActivity : BaseActivity() {
    private lateinit var binding : ActivityFilterBinding
    private lateinit var tabTitles:Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_filter)
        initialise()
    }

    private fun initialise() {
        tabTitles = arrayOf("Dietry Info","Volume","Meals")
        initialiseViewPager()
    }


    companion object {
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

        binding.tab.addTab(binding.tab.newTab().setText("Dietry Info"));
        binding.tab.addTab(binding.tab.newTab().setText("Volume"));
        binding.tab.addTab(binding.tab.newTab().setText("Meals"));

        pagerAdapter.addFragment(DietryInfoFragment(), "Dietry Info")
        pagerAdapter.addFragment(VolumeFragment(), "Volume")
        pagerAdapter.addFragment(MealsFragment(), "Meals")

        binding.viewPager.adapter=pagerAdapter
        TabLayoutMediator( binding.tab,   binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()
    }
}