package com.fighterdiet.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.TrendingFragmentRecyAdapter
import com.fighterdiet.databinding.FragmentTrendingBinding
import com.fighterdiet.models.home_frag.HomeModel
import com.fighterdiet.utils.Utils

class TrendingFragment : BaseFragment() {
    lateinit var binding: FragmentTrendingBinding
    private lateinit var trendingAdapter: TrendingFragmentRecyAdapter
    var homeList: ArrayList<HomeModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trending, container, false)
        return binding.root
    }

    companion object {

        fun getInstance(context: Context): Fragment {
            val bundle = Bundle()
            val fragment = TrendingFragment()
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
    }

    private fun initialise() {
        setUpHomeList()
        setUptrendingRecyclerView()
    }

    private fun setUpHomeList() {
        homeList.add(HomeModel(R.mipmap.easy_chicken, false, false))
        homeList.add(HomeModel(R.mipmap.rice_pudding, false, false))
        homeList.add(HomeModel(R.mipmap.banana, false, false))

        homeList.add(HomeModel(R.mipmap.easy_chicken, false, false))
        homeList.add(HomeModel(R.mipmap.rice_pudding, false, false))
        homeList.add(HomeModel(R.mipmap.banana, false, false))

        homeList.add(HomeModel(R.mipmap.easy_chicken, false, false))
        homeList.add(HomeModel(R.mipmap.rice_pudding, false, false))
        homeList.add(HomeModel(R.mipmap.banana, false, false))

        homeList.add(HomeModel(R.mipmap.easy_chicken, false, false))
    }

    private fun setUptrendingRecyclerView() {

        binding.rvTrendingRecycler.layoutManager = LinearLayoutManager(activity)
        trendingAdapter = TrendingFragmentRecyAdapter(activity, homeList) { position, view ->
            Utils.showSnackBar(binding.rvTrendingRecycler, "mes")
        }
        binding.rvTrendingRecycler.adapter = trendingAdapter
    }

}