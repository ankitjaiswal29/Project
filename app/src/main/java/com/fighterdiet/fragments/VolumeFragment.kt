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
import com.fighterdiet.databinding.FragmentVolumeBinding
import com.fighterdiet.model.VolumeModel

class VolumeFragment : Fragment(), VolumeAdapter.VolumeCountListener {
    lateinit var binding: FragmentVolumeBinding
    private lateinit var volumeAdapter: VolumeAdapter
    var list: ArrayList<VolumeModel> = ArrayList()
    private lateinit var volumeFragListener: VolumeFragInterface


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
        initialise()
    }

    private fun initialise() {
        setUpDietryList()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.rvVolume.layoutManager = LinearLayoutManager(activity)
        volumeAdapter = VolumeAdapter(activity, list,this) /*{ position, view ->
            Utils.showSnackBar(binding.rvVolume, "mes")
        }*/
        binding.rvVolume.adapter = volumeAdapter
    }

    private fun setUpDietryList() {
        list.add(VolumeModel("Small Volume", false))
        list.add(VolumeModel("Medium Volume", true))
        list.add(VolumeModel("Big Volume", true))
    }

    override fun volumeAdapterListener(position: Int, checkBox: CheckBox) {
        if (checkBox.isChecked){
            count++
        }else if (!checkBox.isChecked){
            count--

        }
        volumeFragListener.volumefragCount(count)
//       Utils.showToast(context, "fragment   $count")
    }

    interface VolumeFragInterface{
        fun volumefragCount(count: Int)
    }

}