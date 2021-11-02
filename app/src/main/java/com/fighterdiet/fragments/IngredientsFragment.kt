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

class IngredientsFragment(val recipeIngredientModel: List<RecipeContentResponseModel.Ingredient>) : BaseFragment() {
    lateinit var binding: FragmentIngredientsBinding
    private lateinit var ingredientAdapter : IngredientsRecycleAdapter

    companion object {
        fun getInstance(recipeIngredientModel: List<RecipeContentResponseModel.Ingredient>): Fragment {
            return IngredientsFragment(recipeIngredientModel)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_ingredients,container,false)
        setUpRecyclerView()
        Constants.RecipeDetails.recipeNotesLive.observe(viewLifecycleOwner,{
            binding.etNoteIngred.setText(it)
        })
        initListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initListener() {
//        binding.etNoteIngred.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if(p0.toString()!=binding.etNoteIngred.text.toString())
//                    Constants.RecipeDetails.recipeNotesLive.postValue(p0.toString())
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//
//            }
//
//        })
//        binding.etNoteIngred.setOnFocusChangeListener { view, isFocused ->
//            if(!isFocused){
//                Constants.RecipeDetails.recipeNotesLive.postValue(binding.etNoteIngred.text.toString())
//            }
//        }

    }

    override fun onPause() {
        super.onPause()
        Constants.RecipeDetails.recipeNotesLive.postValue(binding.etNoteIngred.text.toString())
    }

    private fun setUpRecyclerView() {
        binding.rvIngredientGroup.layoutManager = LinearLayoutManager(activity)
        ingredientAdapter = IngredientsRecycleAdapter(recipeIngredientModel)
        binding.rvIngredientGroup.adapter = ingredientAdapter
    }
}