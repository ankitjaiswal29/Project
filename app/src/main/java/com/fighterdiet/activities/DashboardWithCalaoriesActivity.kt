package com.fighterdiet.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fighterdiet.R
import com.fighterdiet.adapters.ViewPagerWithCalDashboardAdapter
import com.fighterdiet.databinding.ActivityDashboardWithCalaoriesBinding
import com.fighterdiet.fragments.FavouriteFragment
import com.fighterdiet.fragments.HomeFragment
import com.fighterdiet.fragments.HotFragment
import com.google.android.material.tabs.TabLayout


class DashboardWithCalaoriesActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardWithCalaoriesBinding
    private lateinit var adapter: ViewPagerWithCalDashboardAdapter
    private val fragments = ArrayList<Fragment>()
    private val tabIcons = intArrayOf(
        R.mipmap.icn_search,
        R.mipmap.icn_trending,
        R.mipmap.icn_favourite,
        R.mipmap.icn_shopping,
        R.mipmap.icn_cb,
        R.mipmap.icn_settings,
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard_with_calaories)
        initialise()
    }

    private fun initialise() {

        var home: HomeFragment = HomeFragment()
        var trending: HotFragment = HotFragment()
        var favourite: FavouriteFragment = FavouriteFragment()
        var shop: FavouriteFragment = FavouriteFragment()
        var cb: FavouriteFragment = FavouriteFragment()
        var setting: FavouriteFragment = FavouriteFragment()


        fragments.add(home)
        fragments.add(trending)
        fragments.add(favourite)
        fragments.add(shop)
        fragments.add(cb)
        fragments.add(setting)

        binding.tabs.addTab(binding.tabs.newTab().setText(""))
        binding.tabs.addTab(binding.tabs.newTab().setText(""))
        binding.tabs.addTab(binding.tabs.newTab().setText(""))
        binding.tabs.addTab(binding.tabs.newTab().setText(""))
        binding.tabs.addTab(binding.tabs.newTab().setText(""))
        binding.tabs.addTab(binding.tabs.newTab().setText(""))
        binding.tabs.tabGravity = TabLayout.GRAVITY_FILL

        setupTabs()
        setupTabIcons()
    }

    private fun setupTabIcons() {
        binding.tabs.getTabAt(0)?.setIcon(tabIcons .get(0))
        binding.tabs.getTabAt(1)?.setIcon(tabIcons .get(1))
        binding.tabs.getTabAt(2)?.setIcon(tabIcons .get(2))
        binding.tabs.getTabAt(3)?.setIcon(tabIcons .get(3))
        binding.tabs.getTabAt(4)?.setIcon(tabIcons .get(4))
        binding.tabs.getTabAt(5)?.setIcon(tabIcons .get(5))
    }

    private fun setupTabs() {
        val adapter = ViewPagerWithCalDashboardAdapter(
            fragments,
            supportFragmentManager,
            binding.tabs!!.tabCount
        )
        binding.viewPagerDash!!.adapter = adapter
        binding.viewPagerDash!!.setOffscreenPageLimit(3)

        binding.viewPagerDash.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                binding.tabs
            )
        )

        binding.tabs!!.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPagerDash!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
}