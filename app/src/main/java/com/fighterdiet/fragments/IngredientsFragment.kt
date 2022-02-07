package com.fighterdiet.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.IngredientsRecycleAdapter
import com.fighterdiet.data.model.responseModel.RecipeContentResponseModel
import com.fighterdiet.databinding.FragmentIngredientsBinding
import com.fighterdiet.utils.Constants

class IngredientsFragment : BaseFragment() {
    lateinit var binding: FragmentIngredientsBinding
    private lateinit var ingredientAdapter : IngredientsRecycleAdapter
    var recipeIngredientModel: List<RecipeContentResponseModel.Ingredient>?=null


    fun passData(recipeIngredientModel: List<RecipeContentResponseModel.Ingredient>){
        this.recipeIngredientModel=recipeIngredientModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
      ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_ingredients,container,false)

        Constants.RecipeDetails.recipeNotesLive.observe(viewLifecycleOwner,{
            binding.etNoteIngred.setText(it)
        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setUpRecyclerView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



    override fun onPause() {
        super.onPause()
        Constants.RecipeDetails.recipeNotesLive.postValue(binding.etNoteIngred.text.toString())
    }

    private fun setUpRecyclerView() {
        binding.rvIngredientGroup.layoutManager = LinearLayoutManager(activity)
        ingredientAdapter = IngredientsRecycleAdapter(recipeIngredientModel!!)
        binding.rvIngredientGroup.adapter = ingredientAdapter
    }
}