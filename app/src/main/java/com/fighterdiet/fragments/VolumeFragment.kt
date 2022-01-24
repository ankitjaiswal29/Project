package com.fighterdiet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.VolumeAdapter
import com.fighterdiet.data.model.responseModel.GetVolumeResponseModel
import com.fighterdiet.databinding.FragmentVolumeBinding
import com.fighterdiet.utils.Constants

class VolumeFragment constructor(val getVolumeResponseModel: GetVolumeResponseModel) : Fragment(), VolumeAdapter.VolumeCountListener {
    lateinit var binding: FragmentVolumeBinding
    private lateinit var volumeAdapter: VolumeAdapter
    var list: ArrayList<GetVolumeResponseModel.Result> = ArrayList()
    private lateinit var volumeFragListener: VolumeFragInterface

    companion object{
        fun newInstance(getVolumeResponseModel: GetVolumeResponseModel): VolumeFragment{
            return VolumeFragment(getVolumeResponseModel)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_volume, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        volumeFragListener = (activity as VolumeFragInterface?)!!
        list = getVolumeResponseModel.result as ArrayList<GetVolumeResponseModel.Result>
        setUpRecyclerView()
    }

    private fun modifyListWhenSelectionCleared(list: ArrayList<GetVolumeResponseModel.Result>) {
        if(Constants.RecipeFilter.isFilterCleared){
            list.forEach {
                it.isChecked = false
            }
            volumeAdapter.notifyDataSetChanged()
        }
    }

//    override fun onResume() {
//        super.onResume()
//        modifyListWhenSelectionCleared(list)
//        volumeFragListener.getCurrFragmentType(1)
//    }

    private fun setUpRecyclerView() {
        binding.rvVolume.layoutManager = LinearLayoutManager(activity)
        volumeAdapter = VolumeAdapter(list,this)
        binding.rvVolume.adapter = volumeAdapter
    }

    fun clearVolumeData() {
        list.forEach {
            it.isChecked = false
        }
        volumeAdapter.notifyDataSetChanged()
    }


    override fun volumeAdapterListener(position: Int, resultModel: GetVolumeResponseModel.Result) {
//        Constants.RecipeFilter.selectedVolumeFilter[position] = resultModel
        volumeFragListener.volumefragCount(position, resultModel.volume_id, resultModel.isChecked)
    }

    interface VolumeFragInterface{
        fun volumefragCount(pos: Int, id:Int, isItemAdd:Boolean)
        fun getCurrFragmentType(screenType: Int)
    }

}