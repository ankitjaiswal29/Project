package com.fighterdiet.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fighterdiet.R
import com.fighterdiet.activities.LoginActivity
import com.fighterdiet.databinding.FragmentWalkThroughBinding

class WalkThroughFragment : BaseFragment() {
    private lateinit var binding: FragmentWalkThroughBinding
    private var position: Int = 0
    private val images = arrayOf(
        R.mipmap.walkthrough_1,
        R.mipmap.walkthrough_2,
        R.mipmap.walkthrough_3,
        R.mipmap.walkthrough_4
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_walk_through, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
    }

    private fun initialise() {
        position = arguments?.getInt(KEY_INDEX)!!
        when (position) {
            0 -> {
                binding.ivBackgroundImage.setImageResource(images[0])
            }
            1 -> {
                binding.ivBackgroundImage.setImageResource(images[1])
            }
            2 -> {
                binding.ivBackgroundImage.setImageResource(images[2])
            }
            3 -> {
                binding.ivBackgroundImage.setImageResource(images[3])
            }
        }

       /* binding.ivBackgroundImage.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.e("TAG",">>>>> Mouse Down")
                    if (activity is LoginActivity)
                        (activity as LoginActivity).setHold(true)
                }
                MotionEvent.ACTION_UP -> {
                    Log.e("TAG",">>>>> Mouse Up")
                    if (activity is LoginActivity)
                        (activity as LoginActivity).setHold(false)
                }
                MotionEvent.ACTION_MOVE -> {
//                    Log.e("TAG",">>>>> Mouse Move")
                }

            }
            true
        }*/
    }

    companion object {
        const val KEY_INDEX = "key_index"

        fun getInstance(index: Int): Fragment {
            val bundle = Bundle()
            bundle.putInt(KEY_INDEX, index)
            val fragment = WalkThroughFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}