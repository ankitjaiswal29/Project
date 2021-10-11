package com.fighterdiet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fighterdiet.R
import com.fighterdiet.adapters.DietryInfoAdapter
import com.fighterdiet.data.model.responseModel.GetAllergyResponseModel
import com.fighterdiet.databinding.FragmentDietryInfoBinding
import com.fighterdiet.model.DietryModel

class DietryInfoFragment(val getAllergyResponseModel: GetAllergyResponseModel) : Fragment(),
    DietryInfoAdapter.DietaryCountListener {
    lateinit var binding: FragmentDietryInfoBinding
    private lateinit var dietryInfoAdapter : DietryInfoAdapter
    private lateinit var dietaryListener: DietaryInfoInterface
    var list:ArrayList<DietryModel> = ArrayList()

    companion object{
        fun newInstance(getAllergyResponseModel: GetAllergyResponseModel): DietryInfoFragment{
            return DietryInfoFragment(getAllergyResponseModel)
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
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        if(getAllergyResponseModel.result.isEmpty())
            return

        dietryInfoAdapter = DietryInfoAdapter(getAllergyResponseModel.result, this)
        binding.rvDietryInfo.adapter = dietryInfoAdapter
    }

    override fun dietaryInfoAdapterListener(position: Int, resultModel: GetAllergyResponseModel.Result) {
        dietaryListener.dietarySelectedInfo(position, resultModel.allergy_id)
    }

    interface DietaryInfoInterface{
        fun dietarySelectedInfo(pos: Int, id:Int)
    }

}