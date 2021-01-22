package com.fighterdiet.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.FavouriteFragmentRecyAdapter
import com.fighterdiet.adapters.HomeFragmentRecyclerAdapter
import com.fighterdiet.adapters.TrendingFragmentRecyAdapter
import com.fighterdiet.databinding.FragmentFavouriteBinding
import com.fighterdiet.models.home_frag.HomeModel
import com.fighterdiet.utils.Utils

class FavouriteFragment : BaseFragment() {
    lateinit var binding: FragmentFavouriteBinding
    private lateinit var favouriteAdapter : FavouriteFragmentRecyAdapter
    var homeList:ArrayList<HomeModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
    }

    private fun initialise() {
        setUpHomeList()
        setUpFavouriteRecyclerView()
    }

    private fun setUpFavouriteRecyclerView() {
        binding.rvFavouriteRecycler.layoutManager = LinearLayoutManager(activity)
        favouriteAdapter = FavouriteFragmentRecyAdapter(activity,homeList){
                position,view ->
            Utils.showSnackBar(binding.rvFavouriteRecycler,"mes")
        }
        binding.rvFavouriteRecycler.adapter = favouriteAdapter
    }

    private fun setUpHomeList() {
        homeList.add(HomeModel(R.mipmap.rice_pudding,false,false))
        homeList.add(HomeModel(R.mipmap.easy_chicken,false,false))
        homeList.add(HomeModel(R.mipmap.banana,false,false))
    }

    companion object {

        fun getInstance(context: Context): Fragment {
            val bundle = Bundle()
            val fragment = FavouriteFragment()
            return fragment
        }
    }

}