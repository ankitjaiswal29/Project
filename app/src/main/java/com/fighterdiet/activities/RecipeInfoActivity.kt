package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fighterdiet.R
import com.fighterdiet.adapters.ViewPagerRecipeInfoAdapter
import com.fighterdiet.adapters.ViewPagerWithCalDashboardAdapter
import com.fighterdiet.databinding.ActivityRecipeInfoBinding
import com.fighterdiet.fragments.*
import com.google.android.material.tabs.TabLayout

class RecipeInfoActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding :ActivityRecipeInfoBinding
    private val fragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_recipe_info)
        initialise()
    }

    private fun initialise() {
        binding.infoTool.ivCb.setOnClickListener(this)

        val info: InfoFragment = InfoFragment()
        val ingredientsFragment: IngredientsFragment = IngredientsFragment()
        val directions: DirectionsFragment = DirectionsFragment()
        val tipsFragment: TipsFragment = TipsFragment()


        fragments.add(info)
        fragments.add(ingredientsFragment)
        fragments.add(directions)
        fragments.add(tipsFragment)

        binding.tab.addTab(binding.tab.newTab().setText("Info"))
        binding.tab.addTab(binding.tab.newTab().setText("Ingredients"))
        binding.tab.addTab(binding.tab.newTab().setText("Directions"))
        binding.tab.addTab(binding.tab.newTab().setText("Tips"))
//        binding.tab.tabGravity = TabLayout.GRAVITY_FILL

        setupTabs()

    }

    private fun setupTabs() {
        val adapter = ViewPagerRecipeInfoAdapter(
            fragments,
            supportFragmentManager,
            binding.tab.tabCount
        )
        binding.vpInfoRecepie.adapter = adapter

        binding.vpInfoRecepie.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                binding.tab
            )
        )

        binding.tab.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.vpInfoRecepie.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }


    companion object {
        const val TAG = "RecipeInfoActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, RecipeInfoActivity::class.java)
        }
    }

    override fun onClick(view: View?) {

    }
}