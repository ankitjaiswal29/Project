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
import com.fighterdiet.data.model.responseModel.GetMealResponseModel
import com.fighterdiet.databinding.FragmentMealsBinding
import com.fighterdiet.model.MealsModel


class MealsFragment(val getMealResponseModel: GetMealResponseModel) : Fragment(), MealsAdapter.MealsCountListener {
    lateinit var binding: FragmentMealsBinding
    private lateinit var mealsAdapter : MealsAdapter
    var list:ArrayList<MealsModel> = ArrayList()
    private lateinit var mealsListener: MealsInfoInterface

    companion object{
        fun newInstance(getMealResponseModel: GetMealResponseModel): MealsFragment{
            return MealsFragment(getMealResponseModel)
        }
    }

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
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.rvMeals.layoutManager = LinearLayoutManager(activity)
        mealsAdapter = MealsAdapter(getMealResponseModel.result as ArrayList<GetMealResponseModel.Result>,this)
        binding.rvMeals.adapter = mealsAdapter
    }

    override fun mealsInfoAdapterListener(
        position: Int,
        resultModel: GetMealResponseModel.Result
    ) {
        mealsListener.mealsInfoCount(position, resultModel.volume_id)
    }

    interface MealsInfoInterface{
        fun mealsInfoCount(pos: Int, id:Int)
    }

}