package com.fighterdiet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.DietryInfoAdapter
import com.fighterdiet.adapters.VolumeAdapter
import com.fighterdiet.databinding.FragmentMealsBinding
import com.fighterdiet.databinding.FragmentVolumeBinding
import com.fighterdiet.model.DietryModel
import com.fighterdiet.model.VolumeModel
import com.fighterdiet.utils.Utils

class VolumeFragment : Fragment() {
    lateinit var binding: FragmentVolumeBinding
    private lateinit var volumeAdapter : VolumeAdapter
    var list:ArrayList<VolumeModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_volume,container, false)
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
        binding.rvVolume.layoutManager = LinearLayoutManager(activity)
        volumeAdapter = VolumeAdapter(activity,list){
                position,view ->
            Utils.showSnackBar(binding.rvVolume,"mes")
        }
        binding.rvVolume.adapter = volumeAdapter
    }

    private fun setUpDietryList() {
        list.add(VolumeModel("Small Volume",false))
        list.add(VolumeModel("Medium Volume",true))
        list.add(VolumeModel("Big Volume",true))
    }

}