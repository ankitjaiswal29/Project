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
import com.fighterdiet.utils.Constants


class MealsFragment(val getMealResponseModel: GetMealResponseModel) : Fragment(), MealsAdapter.MealsCountListener {
    private var list: ArrayList<GetMealResponseModel.Result>  = ArrayList()
    lateinit var binding: FragmentMealsBinding
    private lateinit var mealsAdapter : MealsAdapter
    private lateinit var mealsListener: MealsInfoInterface

    companion object{
        fun newInstance(getMealResponseModel: GetMealResponseModel): MealsFragment{
            return MealsFragment(getMealResponseModel)
        }
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
        list = getMealResponseModel.result as ArrayList<GetMealResponseModel.Result>
        setUpRecyclerView()
    }

    private fun modifyListWhenSelectionCleared(list: ArrayList<GetMealResponseModel.Result>) {
        if(Constants.RecipeFilter.isFilterCleared){
            list.forEach {
                it.isChecked = false
            }
            mealsAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        modifyListWhenSelectionCleared(list)
        mealsListener.getCurrFragmentType(2)
    }

    private fun setUpRecyclerView() {
        binding.rvMeals.layoutManager = LinearLayoutManager(activity)
        mealsAdapter = MealsAdapter(list,this)
        binding.rvMeals.adapter = mealsAdapter
    }

    @Synchronized
    fun clearMealData() {
        list.forEach {
            it.isChecked = false
        }
        mealsAdapter.notifyDataSetChanged()
    }

    override fun mealsInfoAdapterListener(position: Int, resultModel: GetMealResponseModel.Result) {
//        Constants.RecipeFilter.selectedMealFilter[position] = resultModel
        mealsListener.mealsInfoCount(position, resultModel.meal_id, resultModel.isChecked)
    }

    interface MealsInfoInterface{
        fun mealsInfoCount(pos: Int, id:Int, isItemAdd:Boolean)
        fun getCurrFragmentType(screenType: Int)
    }

}