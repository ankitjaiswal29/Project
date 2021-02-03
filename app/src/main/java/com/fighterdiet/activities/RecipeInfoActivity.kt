package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fighterdiet.R
import com.fighterdiet.adapters.ViewPagerRecipeInfoAdapter
import com.fighterdiet.databinding.ActivityRecipeInfoBinding
import com.fighterdiet.fragments.*
import com.fighterdiet.utils.Utils
import com.google.android.material.tabs.TabLayout

class RecipeInfoActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityRecipeInfoBinding
    private val fragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* getWindow().setFlags(
             WindowManager.LayoutParams.FLAG_SECURE,
             WindowManager.LayoutParams.FLAG_SECURE
         )*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_info)
        initialise()
    }

    private fun initialise() {
        binding.infoTool.back.setOnClickListener(this)
        binding.ivComment.setOnClickListener(this)
        binding.ivBanner.setOnClickListener(this)
        binding.ivFav.setOnClickListener(this)
        binding.ivShare.setOnClickListener(this)

        val info = InfoFragment()
        val ingredientsFragment = IngredientsFragment()
        val directions = DirectionsFragment()
        val tipsFragment = TipsFragment()

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
        when (view?.id) {

            R.id.back -> {
                onBackPressed()
            }

            R.id.iv_comment -> {
                val commentFragment = CommentFragment.newInstance()
                commentFragment.show(
                    supportFragmentManager,
                    CommentFragment::class.simpleName
                )
            }

            R.id.iv_banner -> {
                startActivity(
                    ImageZoomOutActivity.getStartIntent(this).putExtra("from", "Activity")
                )
            }

            R.id.iv_fav -> {
                Utils.showToast(this, "Will cover in functionality")
            }

            R.id.iv_share -> {
                Utils.showToast(this, "Will cover in functionality")
            }

        }
    }
}