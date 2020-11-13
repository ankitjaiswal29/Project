package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.HomeFragmentRecyclerAdapter
import com.fighterdiet.databinding.ActivityPicDaySevenMealsBinding
import com.fighterdiet.models.home_frag.HomeModel
import com.fighterdiet.utils.Utils

class PicDaySevenMealsActivity : AppCompatActivity() {
    lateinit var binding:ActivityPicDaySevenMealsBinding
    private lateinit var homeAdapter : HomeFragmentRecyclerAdapter
    var homeList:ArrayList<HomeModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_pic_day_seven_meals)
        initialise()
    }

    private fun initialise() {
        setUpHomeList()
        setUpHomeRecyclerView()
    }

    private fun setUpHomeList() {
        homeList.add(HomeModel(R.mipmap.food_1))
        homeList.add(HomeModel(R.mipmap.food_2))
        homeList.add(HomeModel(R.mipmap.food_3))
    }

    private fun setUpHomeRecyclerView() {

        binding.rvPicSeven.layoutManager = LinearLayoutManager(this)
        homeAdapter = HomeFragmentRecyclerAdapter(this,homeList){
                position,view ->
            Utils.showSnackBar(binding.rvPicSeven,"mes")
        }
        binding.rvPicSeven.adapter = homeAdapter
    }

    companion object {
        const val TAG = "PicDaySevenMealsActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, ResetPasswordActivity::class.java)
        }
    }
}