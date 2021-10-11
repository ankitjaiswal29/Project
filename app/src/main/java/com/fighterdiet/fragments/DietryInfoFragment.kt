package com.fighterdiet.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fighterdiet.R
import com.fighterdiet.adapters.DietryInfoAdapter
import com.fighterdiet.data.model.responseModel.GetDietaryResponseModel
import com.fighterdiet.databinding.FragmentDietryInfoBinding
import com.fighterdiet.utils.Constants

class DietryInfoFragment(val getDietaryResponseModel: GetDietaryResponseModel) : Fragment(),
    DietryInfoAdapter.DietaryCountListener {
    lateinit var binding: FragmentDietryInfoBinding
    private lateinit var dietryInfoAdapter : DietryInfoAdapter
    private lateinit var dietaryListener: DietaryInfoInterface
    var list:ArrayList<GetDietaryResponseModel.Result> = ArrayList()

    companion object{
        fun newInstance(getDietaryResponseModel: GetDietaryResponseModel): DietryInfoFragment{
            return DietryInfoFragment(getDietaryResponseModel)
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dietry_info, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dietaryListener = (activity as DietaryInfoInterface?)!!
        list = getDietaryResponseModel.result as ArrayList<GetDietaryResponseModel.Result>
        setUpRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        dietaryListener.initFilterSelectionUi(0)
    }

    private fun setUpRecyclerView() {
        dietryInfoAdapter = DietryInfoAdapter(list, this)
        binding.rvDietryInfo.adapter = dietryInfoAdapter
    }

    fun clearDietaryData() {
        list.forEach {
            it.isChecked = false
        }
        dietryInfoAdapter.notifyDataSetChanged()
    }


    override fun dietaryInfoAdapterListener(
        position: Int,
        resultModel: GetDietaryResponseModel.Result
    ) {
        Constants.RecipeFilter.selectedDietaryFilter[position] = resultModel
        dietaryListener.dietarySelectedInfo(position, resultModel.allergy_id, resultModel.isChecked)
    }

    interface DietaryInfoInterface{
        fun dietarySelectedInfo(pos: Int, id:Int, isItemAdd:Boolean)
        fun initFilterSelectionUi(screenType: Int)
    }

}