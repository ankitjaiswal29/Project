package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
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
    private lateinit var pickDayAdapter: PicDayMealAdapter
    var pickList:ArrayList<HomeModel> = ArrayList()
    private var pos = 0
    private var count =0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pic_one_day_meals)
        initialise()
    }

    private fun initialise() {
        setUpHomeList()
        setUpHomeRecyclerView()
        binding.tvNext.setOnClickListener(this)
    }

    private fun setUpHomeList() {
        pickList.add(HomeModel(R.mipmap.food_1, false,false))
        pickList.add(HomeModel(R.mipmap.food_2,false,false))
        pickList.add(HomeModel(R.mipmap.food_3,false,false))
    }

    private fun setUpHomeRecyclerView() {

        binding.rvOneDay.layoutManager = LinearLayoutManager(this)

        pickDayAdapter = PicDayMealAdapter(
            this,
            pickList
        ) { position: Int, view: View? ->

          /*  val item: HomeModel = pickList.get(position)
            if (!item.isselected) {
                item.isselected = true
                pickDayAdapter.notifyItemChanged(position)
                if (pickList.get(pos) != null) {
                    pickList.get(pos).isselected = false
                    pickDayAdapter.notifyItemChanged(pos)
                }
                pos = position
            }*/
        }
        binding.rvOneDay.adapter = pickDayAdapter
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
                    if (count<7){
                        count++
                        binding.etSearch.hint = "Pick Day "+count+" Meals"
                        pickDayAdapter.notifyDataSetChanged()
                    }else{
                        startActivity(DashboardActivity.getStartIntent(this))
                    }
                }
            }
        }
    }


    override fun onItemClick(position: Int, view: View?) {

    }
}