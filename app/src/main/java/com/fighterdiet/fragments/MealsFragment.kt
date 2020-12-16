package com.fighterdiet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.databinding.FragmentDietryInfoBinding
import com.fighterdiet.databinding.FragmentMealsBinding


class MealsFragment : Fragment() {
    lateinit var binding: FragmentMealsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meals,container, false)
        return binding.root
    }

}