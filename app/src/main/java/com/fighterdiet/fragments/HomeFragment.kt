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
import com.fighterdiet.activities.RecipeDetailsActivity
import com.fighterdiet.adapters.HomeRecipeListRecyclerAdapter
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.model.responseModel.RecipeListResponseModel
import com.fighterdiet.data.repository.HomeRepository
import com.fighterdiet.data.repository.HomeViewModelProvider
import com.fighterdiet.databinding.FragmentHomeBinding
import com.fighterdiet.utils.Constants
import com.fighterdiet.utils.Status
import com.fighterdiet.viewModel.HomeViewModel

class HomeFragment : BaseFragment() {
    private var recipiesModel: RecipeListResponseModel? = null
    private lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentHomeBinding
    private lateinit var recipeListAdapter: HomeRecipeListRecyclerAdapter
    var recipeList: ArrayList<RecipeListResponseModel.Recipies> = ArrayList()

    var offset = 0
    var limit = 8

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObserver()
        initialize()
    }

    fun getRecipes(searchKeys: String, startFrom: Int, endTo: Int){
        viewModel.getRecipeList(searchKeys, startFrom, endTo)
    }

    fun setupViewModel() {
            viewModel = ViewModelProvider(this, HomeViewModelProvider(HomeRepository(RetrofitBuilder.apiService)))
            .get(HomeViewModel::class.java)
        getRecipes("",offset,limit)
    }

    fun setupObserver() {
        viewModel.getRecipeListResource().observe(viewLifecycleOwner, {
            when(it.status){
                Status.SUCCESS -> {
                    recipeList.clear()
                    recipiesModel = it.data?.data
                    if (it.data?.data?.result.isNullOrEmpty())
                        return@observe
                    recipeList.addAll(it.data?.data?.result!!)
                    recipeListAdapter.notifyDataSetChanged()
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
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvHomeRecycler.layoutManager = layoutManager
        recipeListAdapter = HomeRecipeListRecyclerAdapter(requireActivity(), recipeList) { position, recipe ->
            recipiesModel?.let {
                val act = RecipeDetailsActivity.getStartIntent(requireContext())
                    .putExtra(Constants.RECIPE_ID, recipe.id)
                    .putExtra(Constants.RECIPE_IMAGE, recipe.recipe_image)
                    .putExtra(Constants.RECIPE_NAME, recipe.recipe_name)
                when(it.is_subscribed){
                    "1" -> {
                        startActivity(act)
                    }
                    "0" -> {
                        startActivity(act)
                    }
//                    "0" -> startActivity(MemberShipActivity.getStartIntent(context!!))

                }
            }
        }
        binding.rvHomeRecycler.adapter = recipeListAdapter
//        binding.rvHomeRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if (!recyclerView.canScrollVertically(1)){
//                    val newOffset = offset + 8
//                    viewModel.getRecipeList("", newOffset, limit)
//                    offset = newOffset
//                }
//                if (!recyclerView.canScrollVertically()){
//                    val newOffset = offset + 8
//                    viewModel.getRecipeList("", newOffset, limit)
//                    offset = newOffset
//                }
//            }
//        })
//        binding.rvHomeRecycler.addOnScrollListener(object :
//            EndlessRecyclerViewScrollListener(layoutManager) {
//            override fun onLoadMore(page: Int, totalItemsCount: Int) {
////                fetchData(page)
//                getRecipes("",page.toString(),totalItemsCount.toString())
//            }
//
//        })
    }
}