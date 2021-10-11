package com.fighterdiet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.VolumeAdapter
import com.fighterdiet.activities.FilterActivity.Companion.count
import com.fighterdiet.data.model.responseModel.GetAllergyResponseModel
import com.fighterdiet.data.model.responseModel.GetVolumeResponseModel
import com.fighterdiet.databinding.FragmentVolumeBinding
import com.fighterdiet.model.VolumeModel

class VolumeFragment(val getVolumeResponseModel: GetVolumeResponseModel) : Fragment(), VolumeAdapter.VolumeCountListener {
    lateinit var binding: FragmentVolumeBinding
    private lateinit var volumeAdapter: VolumeAdapter
    var list: ArrayList<VolumeModel> = ArrayList()
    private lateinit var volumeFragListener: VolumeFragInterface

    companion object{
        fun newInstance(getVolumeResponseModel: GetVolumeResponseModel): VolumeFragment{
            return VolumeFragment(getVolumeResponseModel)
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_volume, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        volumeFragListener = (activity as VolumeFragInterface?)!!
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        if(getVolumeResponseModel.result.isEmpty())
            return

        binding.rvVolume.layoutManager = LinearLayoutManager(activity)
        volumeAdapter = VolumeAdapter(getVolumeResponseModel.result as ArrayList<GetVolumeResponseModel.Result>,this)
        binding.rvVolume.adapter = volumeAdapter
    }

    override fun volumeAdapterListener(position: Int, resultModel: GetVolumeResponseModel.Result) {
        volumeFragListener.volumefragCount(position, resultModel.volume_id)
    }

    interface VolumeFragInterface{
        fun volumefragCount(pos: Int, id:Int)
    }

}