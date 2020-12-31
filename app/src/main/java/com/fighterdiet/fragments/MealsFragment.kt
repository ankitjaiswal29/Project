package com.fighterdiet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.activities.FilterActivity.Companion.count
import com.fighterdiet.activities.FilterActivity
import com.fighterdiet.adapters.MealsAdapter
import com.fighterdiet.databinding.FragmentMealsBinding
import com.fighterdiet.model.MealsModel


class MealsFragment : Fragment(), MealsAdapter.MealsCountListener {
    lateinit var binding: FragmentMealsBinding
    private lateinit var mealsAdapter : MealsAdapter
    var list:ArrayList<MealsModel> = ArrayList()
    private lateinit var mealsListener: MealsInfoInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meals,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealsListener = (activity as MealsInfoInterface?)!!
        initialise()
    }

    private fun initialise() {
        setUpDietryList()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.rvMeals.layoutManager = LinearLayoutManager(activity)
        mealsAdapter = MealsAdapter(activity,list,this)/*{
                position,view ->
            Utils.showSnackBar(binding.rvMeals,"mes")
        }*/
        binding.rvMeals.adapter = mealsAdapter
    }

    private fun setUpDietryList() {
        list.add(MealsModel("Appetizers",false))
        list.add(MealsModel("Breakfasts",true))
        list.add(MealsModel("Lunch/Dinners",true))
        list.add(MealsModel("Desserts",true))
        list.add(MealsModel("Snack/Sides",true))
        list.add(MealsModel("Refeed Treats",true))
    }

    override fun mealsInfoAdapterListener(position: Int, checkBox: CheckBox) {
        if (checkBox.isChecked){
            count++
        }else if (!checkBox.isChecked){
            count--

        }
        mealsListener.mealsInfoCount(count)
    }

    interface MealsInfoInterface{
        fun mealsInfoCount(count: Int)
    }

}