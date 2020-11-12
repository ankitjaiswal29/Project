package com.fighterdiet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.HomeFragmentRecyclerAdapter
import com.fighterdiet.adapters.WeeklyGroceryListRecyAdapter
import com.fighterdiet.databinding.FragmentWeeklyGroceryBinding
import com.fighterdiet.models.weekly_grocery_list.DairyModel
import com.fighterdiet.utils.Utils


class WeeklyGroceryFragment : BaseFragment() {
    lateinit var binding:FragmentWeeklyGroceryBinding
    private lateinit var dairyRecyler : WeeklyGroceryListRecyAdapter
    private lateinit var dairyList:ArrayList<DairyModel>
    private lateinit var meatList:ArrayList<DairyModel>
    private lateinit var dryFoodList:ArrayList<DairyModel>
//    private lateinit var List:ArrayList<DairyModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_weekly_grocery,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
    }

    private fun initialise() {
        setUpDairyList()
        setUpMeatList()
        setUpDryFoodList()
        setUpDairyRecyclerView()
        setUpMeatRecyclerView()
        setUpDryFoodRecyclerView()
        setUpProduceRecyclerView()
        setUpSpicesRecyclerView()
        setUpBreadRecyclerView()
    }

    private fun setUpBreadRecyclerView() {
        binding.rvBread.layoutManager = LinearLayoutManager(activity)
        dairyRecyler = WeeklyGroceryListRecyAdapter(activity,dairyList){
                position,view ->
            Utils.showSnackBar(binding.rvBread,"mes")
        }
        binding.rvBread.adapter = dairyRecyler

    }

    private fun setUpSpicesRecyclerView() {
        binding.rvSpiceBaking.layoutManager = LinearLayoutManager(activity)
        dairyRecyler = WeeklyGroceryListRecyAdapter(activity,dairyList){
                position,view ->
            Utils.showSnackBar(binding.rvSpiceBaking,"mes")
        }
        binding.rvSpiceBaking.adapter = dairyRecyler

    }

    private fun setUpProduceRecyclerView() {

        binding.rvProduce.layoutManager = LinearLayoutManager(activity)
        dairyRecyler = WeeklyGroceryListRecyAdapter(activity,dairyList){
                position,view ->
            Utils.showSnackBar(binding.rvProduce,"mes")
        }
        binding.rvProduce.adapter = dairyRecyler
    }

    private fun setUpDryFoodRecyclerView() {
        binding.rvDryFood.layoutManager = LinearLayoutManager(activity)
        dairyRecyler = WeeklyGroceryListRecyAdapter(activity,dryFoodList){
                position,view ->
            Utils.showSnackBar(binding.rvDryFood,"mes")
        }
        binding.rvDryFood.adapter = dairyRecyler

    }

    private fun setUpMeatRecyclerView() {

        binding.rvMeat.layoutManager = LinearLayoutManager(activity)
        dairyRecyler = WeeklyGroceryListRecyAdapter(activity,meatList){
                position,view ->
            Utils.showSnackBar(binding.rvMeat,"mes")
        }
        binding.rvMeat.adapter = dairyRecyler
    }

    private fun setUpDairyList() {
        dairyList = ArrayList()
        dairyList.add(DairyModel(true,"Dozen","Eggs"))
        dairyList.add(DairyModel(false,"Dozen","Banana"))
        dairyList.add(DairyModel(true,"Dozen","Whole Milk"))
        dairyList.add(DairyModel(false,"Dozen","Cheese"))
    }

    private fun setUpMeatList() {
        meatList = ArrayList()
        meatList.add(DairyModel(false,"lbs","Chicken breast"))
        meatList.add(DairyModel(true,"lbs","Ground beef"))
    }

    private fun setUpDryFoodList() {
        dryFoodList = ArrayList()
        dryFoodList.add(DairyModel(false,"boxes","Eggs"))
        dryFoodList.add(DairyModel(false,"lbs","Banana"))
        dryFoodList.add(DairyModel(true,"cups","Whole Milk"))
        dryFoodList.add(DairyModel(true,"lbs","Cheese"))
    }

    private fun setUpDairyRecyclerView() {

        binding.rvDairy.layoutManager = LinearLayoutManager(activity)
        dairyRecyler = WeeklyGroceryListRecyAdapter(activity,dairyList){
                position,view ->
            Utils.showSnackBar(binding.rvDairy,"mes")
        }
        binding.rvDairy.adapter = dairyRecyler
    }


}