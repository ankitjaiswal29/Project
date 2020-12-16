package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
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
            R.drawable.tb_search_icon_blue,
            R.mipmap.filter,
            R.drawable.tb_trending_blue_icon,
            R.drawable.tb_favorite_icon_blue,
           R.drawable.tb_grocery_icon_blue,
            R.drawable.tb_calorie_budget_icon_blue,
            R.drawable.tb_settings_icon_blue,
        )
        tabIcons6 = arrayOf(
            R.drawable.tb_search_icon_blue,
            R.mipmap.filter,
            R.drawable.tb_trending_blue_icon,
            R.drawable.tb_favorite_icon_blue,
            R.drawable.tb_grocery_icon_blue,
            R.drawable.tb_calorie_budget_icon_blue,
            R.drawable.tb_settings_icon_blue,
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

        TabLayoutMediator(binding.tabs, binding.viewPagerDash, true) { tab, position ->
            Log.e(">>>>>", "position" + position)
            tab.text = tab6Titles[position]
            binding.viewPagerDash.setCurrentItem(tab.position, true)

            if (tab.isSelected) {
                tab.icon = getDrawable(tabIcons6Selected[position])
            } else {
                tab.icon = getDrawable(tabIcons6[position])
            }

        }.attach()

    }

    private fun update4Tab(position:Int){
        when(position)
        {
            0->{
                binding.tabs.getTabAt(0)?.icon=ContextCompat.getDrawable(this,tabIcons4Selected[0])
                binding.tabs.getTabAt(1)?.icon=ContextCompat.getDrawable(this,tabIcons4[1])
                binding.tabs.getTabAt(2)?.icon=ContextCompat.getDrawable(this,tabIcons4[2])
                binding.tabs.getTabAt(3)?.icon=ContextCompat.getDrawable(this,tabIcons4[3])
            }
            1->{
                binding.tabs.getTabAt(0)?.icon=ContextCompat.getDrawable(this,tabIcons4[0])
                binding.tabs.getTabAt(1)?.icon=ContextCompat.getDrawable(this,tabIcons4Selected[1])
                binding.tabs.getTabAt(2)?.icon=ContextCompat.getDrawable(this,tabIcons4[2])
                binding.tabs.getTabAt(3)?.icon=ContextCompat.getDrawable(this,tabIcons4[3])
            }
            2->{
                binding.tabs.getTabAt(0)?.icon=ContextCompat.getDrawable(this,tabIcons4[0])
                binding.tabs.getTabAt(1)?.icon=ContextCompat.getDrawable(this,tabIcons4[1])
                binding.tabs.getTabAt(2)?.icon=ContextCompat.getDrawable(this,tabIcons4Selected[2])
                binding.tabs.getTabAt(3)?.icon=ContextCompat.getDrawable(this,tabIcons4[3])
            }
            3->{
                binding.tabs.getTabAt(0)?.icon=ContextCompat.getDrawable(this,tabIcons4[0])
                binding.tabs.getTabAt(1)?.icon=ContextCompat.getDrawable(this,tabIcons4[1])
                binding.tabs.getTabAt(2)?.icon=ContextCompat.getDrawable(this,tabIcons4[2])
                binding.tabs.getTabAt(3)?.icon=ContextCompat.getDrawable(this,tabIcons4Selected[3])
            }
        }

    }

    private fun update6Tab(){

    }

    private fun initialise4Tab() {
        tab4Titles = arrayOf(
            "", "", "", ""
        )
        tabIcons4Selected = arrayOf(
            R.drawable.tb_search_icon_blue,
            R.mipmap.filter,
            R.drawable.tb_trending_blue_icon,
            R.drawable.tb_favorite_icon_blue,
            R.drawable.tb_settings_icon_blue,
        )
        tabIcons4 = arrayOf(
            R.drawable.tb_search_icon_blue,
            R.mipmap.filter,
            R.drawable.tb_trending_blue_icon,
            R.drawable.tb_favorite_icon_blue,
            R.drawable.tb_settings_icon_blue,
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

        TabLayoutMediator(
            binding.tabs, binding.viewPagerDash, true,
            { tab, position ->
                tab.text = tab4Titles[position]
                if (tab.isSelected) {
                    tab.icon = ContextCompat.getDrawable(this, tabIcons4Selected[position])
                } else {
                    tab.icon = ContextCompat.getDrawable(this, tabIcons4[position])
                }

            }).attach()

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.e(">>>>>", ">>>>>" + tab.position)
//                update4Tab(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

}

