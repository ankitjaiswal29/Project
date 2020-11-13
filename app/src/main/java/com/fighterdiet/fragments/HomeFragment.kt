package com.fighterdiet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.HomeFragmentRecyclerAdapter
import com.fighterdiet.databinding.FragmentHomeBinding
import com.fighterdiet.models.home_frag.HomeModel
import com.fighterdiet.utils.Utils

class HomeFragment : BaseFragment() {
    lateinit var binding:FragmentHomeBinding
    private lateinit var homeAdapter : HomeFragmentRecyclerAdapter
    var homeList:ArrayList<HomeModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setUpHomeList()
        setUpHomeRecyclerView()
    }

    private fun setUpHomeList() {
        homeList.add(HomeModel(R.mipmap.food_1))
        homeList.add(HomeModel(R.mipmap.food_2))
        homeList.add(HomeModel(R.mipmap.food_3))
    }

    private fun setUpHomeRecyclerView() {

        binding.rvHomeRecycler.layoutManager = LinearLayoutManager(activity)
        homeAdapter = HomeFragmentRecyclerAdapter(activity,homeList){
            position,view ->
            Utils.showSnackBar(binding.rvHomeRecycler,"mes")
        }
        binding.rvHomeRecycler.adapter = homeAdapter
    }
}