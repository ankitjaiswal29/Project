package com.fighterdiet.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.RecipeDirectionsRecycleAdapter
import com.fighterdiet.data.model.responseModel.RecipeContentResponseModel
import com.fighterdiet.databinding.FragmentDirectionsBinding
import com.fighterdiet.utils.Constants


class DirectionsFragment(val recipeDirectionModel: List<RecipeContentResponseModel.Direction>) : BaseFragment() {
    lateinit var binding: FragmentDirectionsBinding
    private lateinit var recipeDirectionAdapter : RecipeDirectionsRecycleAdapter

    companion object {
        fun getInstance(recipeDirectionModel: List<RecipeContentResponseModel.Direction>): Fragment {
            return DirectionsFragment(recipeDirectionModel)
        }
    }

    private fun initRv() {
        binding.rvDirections.layoutManager = LinearLayoutManager(activity)
        recipeDirectionAdapter = RecipeDirectionsRecycleAdapter(recipeDirectionModel)
        binding.rvDirections.adapter = recipeDirectionAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_directions, container,false)

        Constants.RecipeDetails.recipeNotesLive.observe(viewLifecycleOwner,{
            binding.etNote.setText(it)
        })
        initListener()
        initRv()
        return binding.root
    }

    private fun initListener() {
//        binding.etNote.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if(p0.toString()!=binding.etNote.text.toString())
//                    Constants.RecipeDetails.recipeNotesLive.postValue(p0.toString())
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//
//            }
//
//        })

//        binding.etNote.setOnFocusChangeListener { view, isFocused ->
//            if(!isFocused){
//                Constants.RecipeDetails.recipeNotesLive.postValue(binding.etNote.text.toString())
//            }
//        }

    }

    override fun onPause() {
        super.onPause()
        Constants.RecipeDetails.recipeNotesLive.postValue(binding.etNote.text.toString())
    }

}