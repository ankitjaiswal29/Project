package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.HomeFragmentRecyclerAdapter
import com.fighterdiet.databinding.ActivityPicOneDayMealsBinding
import com.fighterdiet.models.home_frag.HomeModel
import com.fighterdiet.utils.Utils

class PicOneDayMealsActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding:ActivityPicOneDayMealsBinding
    private lateinit var homeAdapter : HomeFragmentRecyclerAdapter
    var homeList:ArrayList<HomeModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_pic_one_day_meals)
        initialise()
    }

    private fun initialise() {
        setUpHomeList()
        setUpHomeRecyclerView()
        binding.tvNext.setOnClickListener(this)
    }

    private fun setUpHomeList() {
        homeList.add(HomeModel(R.mipmap.food_1))
        homeList.add(HomeModel(R.mipmap.food_2))
        homeList.add(HomeModel(R.mipmap.food_3))
    }

    private fun setUpHomeRecyclerView() {

        binding.rvOneDay.layoutManager = LinearLayoutManager(this)
        homeAdapter = HomeFragmentRecyclerAdapter(this,homeList){
                position,view ->
            Utils.showSnackBar(binding.rvOneDay,"mes")
        }
        binding.rvOneDay.adapter = homeAdapter
    }

    companion object {
        const val TAG = "PicOneDayMealsActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, PicOneDayMealsActivity::class.java)
        }
    }

    override fun onClick(view: View?) {
        view.let {
            when(view?.id){

                R.id.tv_next ->{
                    startActivity(PicDaySevenMealsActivity.getStartIntent(this))
                }

            }
        }
    }
}