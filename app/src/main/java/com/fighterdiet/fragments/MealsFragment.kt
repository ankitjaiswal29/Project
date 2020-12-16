package com.fighterdiet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.MealsAdapter
import com.fighterdiet.adapters.VolumeAdapter
import com.fighterdiet.databinding.FragmentDietryInfoBinding
import com.fighterdiet.databinding.FragmentMealsBinding
import com.fighterdiet.model.MealsModel
import com.fighterdiet.model.VolumeModel
import com.fighterdiet.utils.Utils


class MealsFragment : Fragment() {
    lateinit var binding: FragmentMealsBinding
    private lateinit var mealsAdapter : MealsAdapter
    var list:ArrayList<MealsModel> = ArrayList()
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
        initialise()
    }

    private fun initialise() {
        setUpDietryList()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.rvMeals.layoutManager = LinearLayoutManager(activity)
        mealsAdapter = MealsAdapter(activity,list){
                position,view ->
            Utils.showSnackBar(binding.rvMeals,"mes")
        }
        binding.rvMeals.adapter = mealsAdapter
    }

    private fun setUpDietryList() {
        list.add(MealsModel("Appetizers",false))
        list.add(MealsModel("Breakfast",true))
        list.add(MealsModel("Lunch/Dinner",true))
        list.add(MealsModel("Dessert",true))
        list.add(MealsModel("Snack/Side",true))
        list.add(MealsModel("Refeed Treat",true))
    }


}