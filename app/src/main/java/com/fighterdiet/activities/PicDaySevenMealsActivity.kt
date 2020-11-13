package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.HomeFragmentRecyclerAdapter
import com.fighterdiet.adapters.PicDayMealAdapter
import com.fighterdiet.databinding.ActivityPicDaySevenMealsBinding
import com.fighterdiet.models.home_frag.HomeModel
import com.fighterdiet.utils.Utils

class PicDaySevenMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivityPicDaySevenMealsBinding
    private lateinit var homeAdapter: PicDayMealAdapter
    var homeList: ArrayList<HomeModel> = ArrayList()
    private var pos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pic_day_seven_meals)
        initialise()
    }

    private fun initialise() {
        binding.tvNext.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(DashboardActivity.getStartIntent(this@PicDaySevenMealsActivity))
            }
        })
        setUpHomeList()
        setUpHomeRecyclerView()
    }

    private fun setUpHomeList() {
        homeList.add(HomeModel(R.mipmap.food_1,false,false))
        homeList.add(HomeModel(R.mipmap.food_2,false,false))
        homeList.add(HomeModel(R.mipmap.food_3,false,false))
    }

    private fun setUpHomeRecyclerView() {

        binding.rvPicSeven.layoutManager = LinearLayoutManager(this)
        homeAdapter = PicDayMealAdapter(
            this,
            homeList
        ) { position: Int, view: View? ->

            val item: HomeModel = homeList.get(position)
            if (!item.isselected) {
                item.isselected = true
                homeAdapter.notifyItemChanged(position)
                if (homeList.get(pos) != null) {
                    homeList.get(pos).isselected = false
                    homeAdapter.notifyItemChanged(pos)
                }
                pos = position
            }
        }
        binding.rvPicSeven.adapter = homeAdapter
    }

    companion object {
        const val TAG = "PicDaySevenMealsActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, PicDaySevenMealsActivity::class.java)
        }
    }
}