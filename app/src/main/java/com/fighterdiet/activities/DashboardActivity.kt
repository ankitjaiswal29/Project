package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fighterdiet.R
import com.fighterdiet.adapters.MyFragmentStateAdapter
import com.fighterdiet.adapters.ViewPagerWithCalDashboardAdapter
import com.fighterdiet.databinding.ActivityDashboardWithCalaoriesBinding
import com.fighterdiet.fragments.*
import com.fighterdiet.utils.Constants
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardWithCalaoriesBinding
    private lateinit var adapter: ViewPagerWithCalDashboardAdapter
    private val fragments = ArrayList<Fragment>()
    lateinit var tab6Titles: Array<String>
    lateinit var tab4Titles: Array<String>
    lateinit var tabIcons4Selected: Array<Int>
    lateinit var tabIcons4: Array<Int>
    lateinit var tabIcons6Selected: Array<Int>
    lateinit var tabIcons6: Array<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard_with_calaories)

        if (Constants.isQuestonnaireCompleted) {
            initialise6Tab()
        } else {
            initialise4Tab()
        }
    }


    companion object {
        const val TAG = "DashboardWithCalaoriesActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, DashboardActivity::class.java)
        }
    }

    private fun initialise6Tab() {
        tab6Titles = arrayOf(
            "", "", "", "", "", ""
        )
        tabIcons6Selected = arrayOf(
            R.mipmap.icn_search,
            R.mipmap.icn_trending,
            R.mipmap.icn_favourite,
            R.mipmap.icn_shopping,
            R.mipmap.icn_cb,
            R.mipmap.icn_settings,
        )
        tabIcons6 = arrayOf(
            R.mipmap.icn_search,
            R.mipmap.icn_fire_trending,
            R.mipmap.icn_favourite_dark,
            R.mipmap.icn_shopping,
            R.mipmap.icn_cb,
            R.mipmap.icn_settings,
        )

        setupTabLayoutFor6()
    }

    private fun setupTabLayoutFor6() {
        val pagerAdapter =
            MyFragmentStateAdapter(
                supportFragmentManager, lifecycle
            )
        pagerAdapter.addFragment(HomeFragment(), "")
        pagerAdapter.addFragment(TrendingFragment(), "")
        pagerAdapter.addFragment(FavouriteFragment(), "")
        pagerAdapter.addFragment(WeeklyGroceryFragment(), "")
        pagerAdapter.addFragment(PersonalChartFragment(), "")
        pagerAdapter.addFragment(SettingFragment(), "")

        binding.viewPagerDash.adapter = pagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPagerDash) { tab, position ->
            Log.e(">>>>>","position"+position)
            tab.text = tab6Titles[position]
            binding.viewPagerDash.setCurrentItem(tab.position, true)

            if (tab.isSelected) {
                tab.icon = getDrawable(tabIcons6Selected[position])
            } else {
                tab.icon = getDrawable(tabIcons6[position])
            }

        }.attach()

    }

    private fun initialise4Tab() {
        tab4Titles = arrayOf(
            "", "", "", ""
        )
        tabIcons4Selected = arrayOf(
            R.mipmap.icn_search,
            R.mipmap.icn_trending,
            R.mipmap.icn_favourite,
            R.mipmap.icn_settings,
        )
        tabIcons4 = arrayOf(
            R.mipmap.icn_search,
            R.mipmap.icn_fire_trending,
            R.mipmap.icn_favourite_dark,
            R.mipmap.icn_settings,
        )

        setupTabLayoutFor4()
    }

    private fun setupTabLayoutFor4() {
        val pagerAdapter =
            MyFragmentStateAdapter(
                supportFragmentManager, lifecycle
            )
        pagerAdapter.addFragment(HomeFragment(), "")
        pagerAdapter.addFragment(TrendingFragment(), "")
        pagerAdapter.addFragment(FavouriteFragment(), "")
        pagerAdapter.addFragment(SettingFragment(), "")

        binding.viewPagerDash.adapter = pagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPagerDash,
            { tab, position ->
                binding.viewPagerDash.setCurrentItem(tab.position, true)
                tab.text = tab4Titles[position]
                if (tab.isSelected) {
                    tab.icon = getDrawable(tabIcons4Selected[position])
                } else {
                    tab.icon = getDrawable(tabIcons4[position])
                }

            }).attach()

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // binding.viewPagerDash.currentItem = tab.position

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

}

