package com.fighterdiet.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.FavouriteFragmentRecyAdapter
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.model.responseModel.FavouriteListResponseModel
import com.fighterdiet.data.model.responseModel.RecipeListResponseModel
import com.fighterdiet.data.repository.FavouriteRepository
import com.fighterdiet.data.repository.HomeRepository
import com.fighterdiet.data.repository.HomeViewModelProvider
import com.fighterdiet.databinding.FragmentFavouriteBinding
import com.fighterdiet.models.home_frag.HomeModel
import com.fighterdiet.utils.Status
import com.fighterdiet.utils.Utils
import com.fighterdiet.viewModel.FavouriteViewModeProvider
import com.fighterdiet.viewModel.FavouriteViewModel
import com.fighterdiet.viewModel.HomeViewModel

class FavouriteFragment : BaseFragment() {
    private lateinit var viewModel: FavouriteViewModel
    lateinit var binding: FragmentFavouriteBinding
    private lateinit var favouriteAdapter: FavouriteFragmentRecyAdapter
  //  var homeList: ArrayList<HomeModel> = ArrayList()
    var favouriteList: ArrayList<FavouriteListResponseModel.Favourite> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
        setupViewModel()
        setupObserver()
    }
    fun setupViewModel() {
        viewModel = ViewModelProvider(this, FavouriteViewModeProvider(FavouriteRepository(RetrofitBuilder.apiService)))
            .get(FavouriteViewModel::class.java)
        viewModel.getFavouriteList()
    }
    fun setupObserver() {
        viewModel.favouriteListResource.observe(viewLifecycleOwner, {
            when(it.status){
                Status.SUCCESS -> {
                    if (it.data?.data?.result.isNullOrEmpty())
                        return@observe
                    favouriteList.addAll(it.data?.data?.result!!)
                    favouriteAdapter.notifyDataSetChanged()
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })
    }
    private fun initialise() {
      //  setUpHomeList()
        setUpFavouriteRecyclerView()
    }

    private fun setUpFavouriteRecyclerView() {
        binding.rvFavouriteRecycler.layoutManager = LinearLayoutManager(activity)
        favouriteAdapter = FavouriteFragmentRecyAdapter(activity, favouriteList) { position, view ->
           // Utils.showSnackBar(binding.rvFavouriteRecycler, "mes")
        }
        binding.rvFavouriteRecycler.adapter = favouriteAdapter
    }

    private fun setUpHomeList() {
     /*   homeList.add(HomeModel(R.mipmap.easy_chicken, false, false))
        homeList.add(HomeModel(R.mipmap.rice_pudding, false, false))
        homeList.add(HomeModel(R.mipmap.banana, false, false))

        homeList.add(HomeModel(R.mipmap.easy_chicken, false, false))
        homeList.add(HomeModel(R.mipmap.rice_pudding, false, false))
        homeList.add(HomeModel(R.mipmap.banana, false, false))

        homeList.add(HomeModel(R.mipmap.easy_chicken, false, false))
        homeList.add(HomeModel(R.mipmap.rice_pudding, false, false))
        homeList.add(HomeModel(R.mipmap.banana, false, false))

        homeList.add(HomeModel(R.mipmap.easy_chicken, false, false))
*/
    }

    companion object {

        fun getInstance(context: Context): Fragment {
            val bundle = Bundle()
            val fragment = FavouriteFragment()
            return fragment
        }
    }

}