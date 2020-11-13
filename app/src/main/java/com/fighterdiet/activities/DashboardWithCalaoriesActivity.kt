package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fighterdiet.R
import com.fighterdiet.adapters.ViewPagerWithCalDashboardAdapter
import com.fighterdiet.databinding.ActivityDashboardWithCalaoriesBinding
import com.fighterdiet.fragments.*
import com.fighterdiet.utils.Constants
import com.google.android.material.tabs.TabLayout


class DashboardWithCalaoriesActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardWithCalaoriesBinding
    private lateinit var adapter: ViewPagerWithCalDashboardAdapter
    private val fragments = ArrayList<Fragment>()
    private val tabIconsFor6 = intArrayOf(
        R.mipmap.icn_search,
        R.mipmap.icn_trending,
        R.mipmap.icn_favourite,
        R.mipmap.icn_shopping,
        R.mipmap.icn_cb,
        R.mipmap.icn_settings,
    )
    private val tabIconsFor4 = intArrayOf(
        R.mipmap.icn_search,
        R.mipmap.icn_trending,
        R.mipmap.icn_favourite,
        R.mipmap.icn_settings,
    )


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
            return Intent(context, ResetPasswordActivity::class.java)
        }
    }

    private fun initialise6Tab() {
        fragments.add(HomeFragment())
        fragments.add(TrendingFragment())
        fragments.add(FavouriteFragment())
        fragments.add(WeeklyGroceryFragment())
        fragments.add(FavouriteFragment())
        fragments.add(SettingFragment())

        binding.tabs.addTab(binding.tabs.newTab().setText(""))
        binding.tabs.addTab(binding.tabs.newTab().setText(""))
        binding.tabs.addTab(binding.tabs.newTab().setText(""))
        binding.tabs.addTab(binding.tabs.newTab().setText(""))
        binding.tabs.addTab(binding.tabs.newTab().setText(""))
        binding.tabs.addTab(binding.tabs.newTab().setText(""))
        binding.tabs.tabGravity = TabLayout.GRAVITY_FILL

        setupTabs()
        setupTabIconsFor6()
    }

    private fun initialise4Tab() {
        fragments.add(HomeFragment())
        fragments.add(TrendingFragment())
        fragments.add(FavouriteFragment())
        fragments.add(SettingFragment())

        binding.tabs.addTab(binding.tabs.newTab().setText(""))
        binding.tabs.addTab(binding.tabs.newTab().setText(""))
        binding.tabs.addTab(binding.tabs.newTab().setText(""))
        binding.tabs.addTab(binding.tabs.newTab().setText(""))
        binding.tabs.tabGravity = TabLayout.GRAVITY_FILL

        setupTabs()
        setupTabIconsFor4()
    }

    private fun setupTabIconsFor6() {
        binding.tabs.getTabAt(0)?.setIcon(tabIconsFor6.get(0))
        binding.tabs.getTabAt(1)?.setIcon(tabIconsFor6.get(1))
        binding.tabs.getTabAt(2)?.setIcon(tabIconsFor6.get(2))
        binding.tabs.getTabAt(3)?.setIcon(tabIconsFor6.get(3))
        binding.tabs.getTabAt(4)?.setIcon(tabIconsFor6.get(4))
        binding.tabs.getTabAt(5)?.setIcon(tabIconsFor6.get(5))
    }

    private fun setupTabIconsFor4() {
        binding.tabs.getTabAt(0)?.setIcon(tabIconsFor4.get(0))
        binding.tabs.getTabAt(1)?.setIcon(tabIconsFor4.get(1))
        binding.tabs.getTabAt(2)?.setIcon(tabIconsFor4.get(2))
        binding.tabs.getTabAt(3)?.setIcon(tabIconsFor4.get(3))
    }

    private fun setupTabs() {
        val adapter = ViewPagerWithCalDashboardAdapter(
            fragments,
            supportFragmentManager,
            binding.tabs.tabCount
        )
        binding.viewPagerDash.adapter = adapter
        // binding.viewPagerDash.setOffscreenPageLimit(3)

        binding.viewPagerDash.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                binding.tabs
            )
        )

        binding.tabs.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPagerDash.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
}