package com.fighterdiet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.HomeFragmentRecyclerAdapter
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.model.responseModel.RecipeListResponseModel
import com.fighterdiet.data.repository.HomeRepository
import com.fighterdiet.data.repository.HomeViewModelProvider
import com.fighterdiet.databinding.FragmentHomeBinding
import com.fighterdiet.utils.Constants
import com.fighterdiet.utils.Status
import com.fighterdiet.viewModel.HomeViewModel

class HomeFragment : BaseFragment() {
    private lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeFragmentRecyclerAdapter
    var recipeList: ArrayList<RecipeListResponseModel.Recipies> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupViewModel()
        setupObserver()
    }

    fun setupViewModel() {
        viewModel = ViewModelProvider(this, HomeViewModelProvider(HomeRepository(RetrofitBuilder.apiService)))
            .get(HomeViewModel::class.java)
        viewModel.getRecipeList()
    }

    fun setupObserver() {
        viewModel.recipeListResource.observe(viewLifecycleOwner, {
            when(it.status){
                Status.SUCCESS -> {
                    if (it.data?.data?.result.isNullOrEmpty())
                        return@observe
                    recipeList.addAll(it.data?.data?.result!!)
                    homeAdapter.notifyDataSetChanged()
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })
    }

    private fun initialize() {
        if (Constants.isQuestonnaireCompleted) {
            binding.daysLay.visibility = VISIBLE
        } else {
            binding.daysLay.visibility = GONE
        }
        setUpHomeRecyclerView()
    }

    private fun setUpHomeRecyclerView() {
        binding.rvHomeRecycler.layoutManager = LinearLayoutManager(activity)
        homeAdapter = HomeFragmentRecyclerAdapter(activity, recipeList) { position, view ->

        }
        binding.rvHomeRecycler.adapter = homeAdapter
    }
}