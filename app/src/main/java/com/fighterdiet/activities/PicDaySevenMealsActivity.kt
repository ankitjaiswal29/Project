package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.PicDayMealAdapter
import com.fighterdiet.databinding.ActivityPicDaySevenMealsBinding
import com.fighterdiet.models.home_frag.HomeModel

class PicDaySevenMealsActivity : BaseActivity() {
    lateinit var binding: ActivityPicDaySevenMealsBinding
    private lateinit var homeAdapter: PicDayMealAdapter
    var homeList: ArrayList<HomeModel> = ArrayList()
    private var pos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pic_day_seven_meals)
        initialise()
    }

    override fun setupUI() {

    }

    override fun setupViewModel() {

    }

    override fun setupObserver() {

    }

    private fun initialise() {
        binding.tvNext.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent=Intent(this@PicDaySevenMealsActivity,DashboardActivity::class.java)
                startActivity(intent)
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

//            val item: HomeModel = homeList.get(position)
//            if (!item.isselected) {
//                item.isselected = true
//                homeAdapter.notifyItemChanged(position)
//                if (homeList.get(pos) != null) {
//                    homeList.get(pos).isselected = false
//                    homeAdapter.notifyItemChanged(pos)
//                }
//                pos = position
//            }
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