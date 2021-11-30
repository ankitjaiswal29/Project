package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityDashboardWithCalaoriesBinding
import com.fighterdiet.fragments.*
import com.fighterdiet.interfaces.DashboardCallback
import com.fighterdiet.utils.Constants
import com.fighterdiet.utils.PrefManager
import com.fighterdiet.utils.Utils
import com.google.android.material.tabs.TabLayout
import com.fighterdiet.utils.Utils.loginAlertDialog

class DashboardActivity : BaseActivity() {
    private var previousPos: Int = 0
    private lateinit var binding: ActivityDashboardWithCalaoriesBinding
    lateinit var tab6Titles: Array<String>
    lateinit var tab4Titles: Array<String>
    lateinit var tabIcons4UnSelected: Array<Int>
    lateinit var tabIcons4Selected: Array<Int>
    lateinit var tabIcons6UnSelected: Array<Int>
    lateinit var tabIcons6Selected: Array<Int>
    var offset = 0
    var limit = 8

    private val callbackDashboard = object : DashboardCallback {
        override fun onStartLoader() {
            binding.pbDashboardAct.visibility = View.VISIBLE
        }

        override fun onDataLoaded() {
            binding.pbDashboardAct.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard_with_calaories)
        Constants.DashboardDetails.isApiRequestNeeded = true
        setupUI()

        previousPos = 0

//        if (Constants.isQuestonnaireCompleted) {
//            initialise6Tab()
//        } else {
            initialise4Tab()
//        }
    }

    override fun onResume() {
        super.onResume()
        if(Constants.DashboardDetails.isApiRequestNeeded){
            showDashboardPb(true)
            showFragment(HomeFragment.initFragment(callbackDashboard), 0)
            Constants.DashboardDetails.isApiRequestNeeded = true
        }
    }

    override fun setupViewModel() {

    }

    override fun setupUI() {

        binding.etSearchRecipe.setOnEditorActionListener { v, actionId, event ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        val currFragment = supportFragmentManager.findFragmentByTag("HOME") as HomeFragment
                        if(currFragment.isVisible)
                            currFragment.getRecipes(
                                binding.etSearchRecipe.text.toString(),
                                offset,
                                limit
                            )
                        Utils.hideKeyboard(this@DashboardActivity, binding.coordinatorDashboard)
                        true
                    }
                    else -> false
                }
            }


        binding.ivCloseSearch.setOnClickListener {
            if (binding.etSearchRecipe.text.isNotEmpty()){
                binding.etSearchRecipe.setText("")
                val currFragment = supportFragmentManager.findFragmentByTag("HOME") as HomeFragment
                if(currFragment.isVisible)
                    currFragment.getRecipes(
                        "",
                        offset,
                        limit
                    )

                currFragment.setUpHomeRecyclerView()
                currFragment.recipeListAdapter.clearAll()
            }
            else
                binding.clSearchRecipe.visibility = View.GONE
            Utils.hideKeyboard(this@DashboardActivity, binding.coordinatorDashboard)
        }
    }

    override fun setupObserver() {

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

    private fun showFragment(fragment: Fragment, position: Int = -1) {
        var tag = "else"
        if(position==0){
            tag = "HOME"
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment, tag)
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

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.e(">>>>>", ">>>>>" + tab.position)
                when (tab.position) {
                    0 ->{
                        showDashboardPb(true)
                        showFragment(HomeFragment.initFragment(callbackDashboard), tab.position)
                    }
                    1 -> {
                        startActivity(FilterActivity.getStartIntent(this@DashboardActivity))
                    }
                    2 -> {
                        showFragment(TrendingFragment.initFragment(callbackDashboard))
                    }
                    3 -> {
                        if(!PrefManager.getBoolean(PrefManager.IS_LOGGED_IN)){
                            loginAlertDialog(this@DashboardActivity)
                            return
                        }
                        showFragment(FavouriteFragment.initFragment(callbackDashboard))
                    }
                    4 -> {
                        startActivity(WeeklyGroceryFragment.getStartIntent(this@DashboardActivity))
                    }
                    5 -> {
                        showFragment(PersonalChartFragment())
                    }
                    6 -> {
                        startActivity(SettingsActivity.getStartIntent(this@DashboardActivity))
                    }
                    else -> {
                        showDashboardPb(true)
                        showFragment(HomeFragment.initFragment(callbackDashboard), tab.position)
                    }
                }
                setIcon(tab.position)

                previousPos = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                Log.e(">>>>>", ">>>>>" + tab.position)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        when(previousPos){
                            0,2,3 -> {
                                if(binding.clSearchRecipe.visibility == View.VISIBLE){
                                    binding.clSearchRecipe.visibility = View.GONE
                                }else{
                                    binding.clSearchRecipe.visibility = View.VISIBLE
                                }
                                return
                            }


                        }
                    }
                    1 -> {
                        startActivity(FilterActivity.getStartIntent(this@DashboardActivity))
                    }
//                    3 -> {
//                        if(!PrefManager.getBoolean(PrefManager.IS_LOGGED_IN)){
//                            startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
//                            return
//                        }
//                    }
                    4 -> {
                        startActivity(WeeklyGroceryFragment.getStartIntent(this@DashboardActivity))
                    }
                    6 -> {
                        startActivity(SettingsActivity.getStartIntent(this@DashboardActivity))
                    }
                }
            }
        })
    }

    private fun showDashboardPb(status: Boolean) {
        binding.pbDashboardAct.visibility = if(status) View.VISIBLE else View.GONE
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

//        showFragment(HomeFragment.initFragment(callbackDashboard), 0)

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.e(">>>>>", ">>>>>" + tab.position)

                when (tab.position) {
                    0 -> {
                        showDashboardPb(true)
                        binding.toolbar.ivTopImage.visibility = View.VISIBLE;
                        binding.toolbar.tvTitle.visibility = View.GONE;
                        showFragment(HomeFragment.initFragment(callbackDashboard), tab.position)
                    }
                    1 -> {
                        startActivity(FilterActivity.getStartIntent(this@DashboardActivity))
                    }
                    2 -> {
                        if(!PrefManager.getBoolean(PrefManager.IS_LOGGED_IN)){
                            loginAlertDialog(this@DashboardActivity)
                            return

                        }
                        binding.clSearchRecipe.visibility = View.GONE
                        binding.toolbar.ivTopImage.visibility = View.GONE
                        binding.toolbar.tvTitle.visibility = View.VISIBLE
                        binding.toolbar.tvTitle.text = "Trending"
                        showFragment(TrendingFragment.initFragment(callbackDashboard))
                    }
                    3 -> {
                        if(!PrefManager.getBoolean(PrefManager.IS_LOGGED_IN)){
                            loginAlertDialog(this@DashboardActivity)
                            return
                        }
                        binding.clSearchRecipe.visibility = View.GONE
                        binding.toolbar.ivTopImage.visibility = View.GONE
                        binding.toolbar.tvTitle.visibility = View.VISIBLE
                        binding.toolbar.tvTitle.text = "Favorites"
                        showFragment(FavouriteFragment.initFragment(callbackDashboard))
                    }
                    4 -> {
                        Constants.DashboardDetails.isApiRequestNeeded = false
                        binding.toolbar.ivTopImage.visibility = View.VISIBLE;
                        binding.toolbar.tvTitle.visibility = View.GONE;
                        startActivity(SettingsActivity.getStartIntent(this@DashboardActivity))
                    }
                    else ->{
                        showDashboardPb(true)
                        binding.toolbar.ivTopImage.visibility = View.VISIBLE;
                        binding.toolbar.tvTitle.visibility = View.GONE;
                        showFragment(HomeFragment.initFragment(callbackDashboard), tab.position)
                    }
                }
                setIcon4(tab.position)

                previousPos = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        if(previousPos == 0){
                            if(binding.clSearchRecipe.visibility == View.VISIBLE){
                                binding.clSearchRecipe.visibility = View.GONE
                            }else{
                                binding.clSearchRecipe.visibility = View.VISIBLE
                            }
                            return
                        }
                    }
                    1 -> {

                        startActivity(FilterActivity.getStartIntent(this@DashboardActivity))
                    }

                    3 -> {
//                        if(!PrefManager.getBoolean(PrefManager.IS_LOGGED_IN)){
//                            loginAlertDialog()
//                            return
//                        }
                    }

                    4 -> {
                        startActivity(SettingsActivity.getStartIntent(this@DashboardActivity))
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

    fun setToolBarVisibility(isToolBarVisible: Boolean) {
       /* if (isToolBarVisible)
            binding.toolbar.ivTopImage.visibility = View.VISIBLE;
        else
            binding.toolbar.ivTopImage.visibility = View.GONE;*/
    }

    override fun onBackPressed() {
        when(previousPos){
            2,3 -> {
                binding.toolbar.ivTopImage.visibility = View.VISIBLE;
                binding.toolbar.tvTitle.visibility = View.GONE;
                showDashboardPb(true)
                showFragment(HomeFragment.initFragment(callbackDashboard), 0)
                setIcon4(0)
                previousPos = 0
            }
            else -> super.onBackPressed()
        }

    }

}

