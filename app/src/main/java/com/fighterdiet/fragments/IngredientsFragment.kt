package com.fighterdiet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.HomeFragmentRecyclerAdapter
import com.fighterdiet.adapters.IngredientsFillingRecyAdapter
import com.fighterdiet.databinding.FragmentHomeBinding
import com.fighterdiet.databinding.FragmentIngredientsBinding
import com.fighterdiet.utils.Utils

class IngredientsFragment : BaseFragment() {
    lateinit var binding: FragmentIngredientsBinding
    private lateinit var ingredientAdapter : IngredientsFillingRecyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_ingredients,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
    }

    private fun initialise() {
        setUpFillingRecyclerView()
        setUpPieRecyclerView()
    }

    private fun setUpPieRecyclerView() {
        binding.recyclerPie.layoutManager = LinearLayoutManager(activity)
        ingredientAdapter = IngredientsFillingRecyAdapter(activity){
                position,view ->
            Utils.showSnackBar(binding.recyclerPie,"mes")
        }
        binding.recyclerPie.adapter = ingredientAdapter
    }

    private fun setUpFillingRecyclerView() {
        binding.recyvlerFilling.layoutManager = LinearLayoutManager(activity)
        ingredientAdapter = IngredientsFillingRecyAdapter(activity){
                position,view ->
            Utils.showSnackBar(binding.recyvlerFilling,"mes")
        }
        binding.recyvlerFilling.adapter = ingredientAdapter
    }

}