package com.fighterdiet.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.adapters.FilterPagerAdapter
import com.fighterdiet.adapters.MyFragmentStateAdapter
import com.fighterdiet.databinding.ActivityFilterBinding
import com.fighterdiet.fragments.FavouriteFragment
import com.fighterdiet.fragments.HomeFragment
import com.fighterdiet.fragments.TrendingFragment

class FilterActivity : BaseActivity() {
    private lateinit var binding : ActivityFilterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_filter)

        initialise()
    }

    private fun initialise() {

        initialiseViewPager()
    }

    private fun initialiseViewPager() {
        val pagerAdapter =
            FilterPagerAdapter(
                supportFragmentManager, lifecycle
            )

        pagerAdapter.addFragment(HomeFragment(), "")
        pagerAdapter.addFragment(TrendingFragment(), "")
        pagerAdapter.addFragment(FavouriteFragment(), "")

        binding.viewPager.adapter=pagerAdapter
    }
}