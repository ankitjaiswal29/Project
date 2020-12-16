package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityDashboardWithCalaoriesBinding
import com.fighterdiet.fragments.*
import com.fighterdiet.utils.Constants
import com.google.android.material.tabs.TabLayout


class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardWithCalaoriesBinding
    lateinit var tab6Titles: Array<String>
    lateinit var tab4Titles: Array<String>
    lateinit var tabIcons4UnSelected: Array<Int>
    lateinit var tabIcons4Selected: Array<Int>
    lateinit var tabIcons6UnSelected: Array<Int>
    lateinit var tabIcons6Selected: Array<Int>

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
        tabIcons6UnSelected = arrayOf(
            R.mipmap.ic_search_blue,
            R.mipmap.filter,
            R.mipmap.ic_trending_blue,
            R.mipmap.ic_favourite_blue,
            R.mipmap.ic_grocery_blue,
            R.mipmap.ic_cb_blue,
            R.mipmap.ic_setting_blue
        )
        tabIcons6Selected = arrayOf(
            R.mipmap.ic_search_gray,
            R.mipmap.filter,
            R.mipmap.ic_trending_gray,
            R.mipmap.ic_favourite_gray,
            R.mipmap.ic_grocery_blue,
            R.mipmap.ic_cb_gray,
            R.mipmap.ic_setting_blue
        )
        setupTabLayoutFor6()
    }


    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

    private fun setupTabLayoutFor6() {
        binding.tabs.addTab(binding.tabs.newTab().setIcon(tabIcons6Selected[0]))
        binding.tabs.addTab(binding.tabs.newTab().setIcon(tabIcons6UnSelected[1]))
        binding.tabs.addTab(binding.tabs.newTab().setIcon(tabIcons6UnSelected[2]))
        binding.tabs.addTab(binding.tabs.newTab().setIcon(tabIcons6UnSelected[3]))
        binding.tabs.addTab(binding.tabs.newTab().setIcon(tabIcons6UnSelected[4]))
        binding.tabs.addTab(binding.tabs.newTab().setIcon(tabIcons6UnSelected[5]))
        binding.tabs.addTab(binding.tabs.newTab().setIcon(tabIcons6UnSelected[6]))

        showFragment(HomeFragment())

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.e(">>>>>", ">>>>>" + tab.position)
                setIcon(tab.position)

                when (tab.position) {
                    0 -> {
                        showFragment(HomeFragment())
                    }
                    1 -> {
                        startActivity(FilterActivity.getStartIntent(this@DashboardActivity))
                    }
                    2 -> {
                        showFragment(TrendingFragment())
                    }
                    3 -> {
                        showFragment(FavouriteFragment())
                    }
                    4 -> {
                        startActivity(WeeklyGroceryFragment.getStartIntent(this@DashboardActivity))
                    }
                    5 -> {
                        showFragment(PersonalChartFragment())
                    }
                    6 -> {
                        startActivity(SettingFragment.getStartIntent(this@DashboardActivity))
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                Log.e(">>>>>", ">>>>>" + tab.position)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    1 -> {
                        startActivity(FilterActivity.getStartIntent(this@DashboardActivity))
                    }
                    4 -> {
                        startActivity(WeeklyGroceryFragment.getStartIntent(this@DashboardActivity))
                    }
                    6 -> {
                        startActivity(SettingFragment.getStartIntent(this@DashboardActivity))
                    }
                }
            }
        })
    }

    private fun setIcon(position: Int) {
        if (position == 1 || position == 4 || position == 6) {
            return
        } else {
            when (position) {
                0 -> {
                    binding.tabs.getTabAt(0)?.setIcon(tabIcons6Selected[0])
                    binding.tabs.getTabAt(2)?.setIcon(tabIcons6UnSelected[2])
                    binding.tabs.getTabAt(3)?.setIcon(tabIcons6UnSelected[3])
                    binding.tabs.getTabAt(5)?.setIcon(tabIcons6UnSelected[5])
                }
                2 -> {
                    binding.tabs.getTabAt(0)?.setIcon(tabIcons6UnSelected[0])
                    binding.tabs.getTabAt(2)?.setIcon(tabIcons6Selected[2])
                    binding.tabs.getTabAt(3)?.setIcon(tabIcons6UnSelected[3])
                    binding.tabs.getTabAt(5)?.setIcon(tabIcons6UnSelected[5])
                }
                3 -> {
                    binding.tabs.getTabAt(0)?.setIcon(tabIcons6UnSelected[0])
                    binding.tabs.getTabAt(2)?.setIcon(tabIcons6UnSelected[2])
                    binding.tabs.getTabAt(3)?.setIcon(tabIcons6Selected[3])
                    binding.tabs.getTabAt(5)?.setIcon(tabIcons6UnSelected[5])
                }
                5 -> {
                    binding.tabs.getTabAt(0)?.setIcon(tabIcons6UnSelected[0])
                    binding.tabs.getTabAt(2)?.setIcon(tabIcons6UnSelected[2])
                    binding.tabs.getTabAt(3)?.setIcon(tabIcons6UnSelected[3])
                    binding.tabs.getTabAt(5)?.setIcon(tabIcons6Selected[5])
                }
            }
        }
    }

    private fun initialise4Tab() {
        tab4Titles = arrayOf(
            "", "", "", ""
        )
        tabIcons4UnSelected = arrayOf(
            R.mipmap.ic_search_blue,
            R.mipmap.filter,
            R.mipmap.ic_trending_blue,
            R.mipmap.ic_favourite_blue,
            R.mipmap.ic_setting_blue
        )
        tabIcons4Selected = arrayOf(
            R.mipmap.ic_search_gray,
            R.mipmap.filter,
            R.mipmap.ic_trending_gray,
            R.mipmap.ic_favourite_gray,
            R.mipmap.ic_setting_blue
        )

        setupTabLayoutFor4()
    }

    private fun setupTabLayoutFor4() {
        binding.tabs.addTab(binding.tabs.newTab().setIcon(tabIcons4Selected[0]))
        binding.tabs.addTab(binding.tabs.newTab().setIcon(tabIcons4UnSelected[1]))
        binding.tabs.addTab(binding.tabs.newTab().setIcon(tabIcons4UnSelected[2]))
        binding.tabs.addTab(binding.tabs.newTab().setIcon(tabIcons4UnSelected[3]))
        binding.tabs.addTab(binding.tabs.newTab().setIcon(tabIcons4UnSelected[4]))

        showFragment(HomeFragment())

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.e(">>>>>", ">>>>>" + tab.position)
                setIcon4(tab.position)

                when (tab.position) {
                    0 -> {
                        showFragment(HomeFragment())
                    }
                    1 -> {
                        startActivity(FilterActivity.getStartIntent(this@DashboardActivity))
                    }
                    2 -> {
                        showFragment(TrendingFragment())
                    }
                    3 -> {
                        showFragment(FavouriteFragment())
                    }
                    4 -> {
                        startActivity(SettingFragment.getStartIntent(this@DashboardActivity))
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    1 -> {
                        startActivity(FilterActivity.getStartIntent(this@DashboardActivity))
                    }
                    4 -> {
                        startActivity(SettingFragment.getStartIntent(this@DashboardActivity))
                    }
                }

            }
        })
    }

    private fun setIcon4(position: Int) {
        if (position == 1 || position == 4) {
            return
        } else {
            when (position) {
                0 -> {
                    binding.tabs.getTabAt(0)?.setIcon(tabIcons4Selected[0])
                    binding.tabs.getTabAt(2)?.setIcon(tabIcons4UnSelected[2])
                    binding.tabs.getTabAt(3)?.setIcon(tabIcons4UnSelected[3])
                }
                2 -> {
                    binding.tabs.getTabAt(0)?.setIcon(tabIcons4UnSelected[0])
                    binding.tabs.getTabAt(2)?.setIcon(tabIcons4Selected[2])
                    binding.tabs.getTabAt(3)?.setIcon(tabIcons4UnSelected[3])
                }
                3 -> {
                    binding.tabs.getTabAt(0)?.setIcon(tabIcons4UnSelected[0])
                    binding.tabs.getTabAt(2)?.setIcon(tabIcons4UnSelected[2])
                    binding.tabs.getTabAt(3)?.setIcon(tabIcons4Selected[3])
                }
            }
        }
    }

}

