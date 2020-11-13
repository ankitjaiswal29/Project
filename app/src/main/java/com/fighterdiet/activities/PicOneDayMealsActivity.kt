package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.HomeFragmentRecyclerAdapter
import com.fighterdiet.adapters.PicDayMealAdapter
import com.fighterdiet.databinding.ActivityPicOneDayMealsBinding
import com.fighterdiet.interfaces.RecyclerViewItemClickListener
import com.fighterdiet.models.home_frag.HomeModel

class PicOneDayMealsActivity : BaseActivity(), View.OnClickListener, RecyclerViewItemClickListener {
    lateinit var binding:ActivityPicOneDayMealsBinding
    private lateinit var homeAdapter : PicDayMealAdapter
    var homeList:ArrayList<HomeModel> = ArrayList()
    private var pos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pic_one_day_meals)
        initialise()
    }

    private fun initialise() {
        setUpHomeList()
        setUpHomeRecyclerView()
        binding.tvNext.setOnClickListener(this)
    }

    private fun setUpHomeList() {
        homeList.add(HomeModel(R.mipmap.food_1, false,false))
        homeList.add(HomeModel(R.mipmap.food_2,false,false))
        homeList.add(HomeModel(R.mipmap.food_3,false,false))
    }

    private fun setUpHomeRecyclerView() {

        binding.rvOneDay.layoutManager = LinearLayoutManager(this)

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

                R.id.tv_next -> {
                    startActivity(PicDaySevenMealsActivity.getStartIntent(this))
                }

            }
        }
    }


    override fun onItemClick(position: Int, view: View?) {

    }
}