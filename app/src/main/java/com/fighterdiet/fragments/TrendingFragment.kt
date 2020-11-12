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
import com.fighterdiet.adapters.HomeFragmentRecyclerAdapter
import com.fighterdiet.adapters.TrendingFragmentRecyAdapter
import com.fighterdiet.databinding.FragmentTrendingBinding
import com.fighterdiet.utils.Utils

class TrendingFragment : BaseFragment() {
    lateinit var binding:FragmentTrendingBinding
    private lateinit var trendingAdapter : TrendingFragmentRecyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trending,container, false)
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
        setUptrendingRecyclerView()
    }

    private fun setUptrendingRecyclerView() {

        binding.rvTrendingRecycler.layoutManager = LinearLayoutManager(activity)
        trendingAdapter = TrendingFragmentRecyAdapter(activity){
                position,view ->
            Utils.showSnackBar(binding.rvTrendingRecycler,"mes")
        }
        binding.rvTrendingRecycler.adapter = trendingAdapter
    }

}