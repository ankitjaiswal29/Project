package com.fighterdiet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.HomeFragmentRecyclerAdaptor
import com.fighterdiet.databinding.FragmentHomeBinding
import com.fighterdiet.utils.Utils

class HomeFragment : Fragment() {
    lateinit var binding:FragmentHomeBinding
    private lateinit var homeAdaptor : HomeFragmentRecyclerAdaptor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setUpHomeRecyclerView()
    }

    private fun setUpHomeRecyclerView() {
        /* rvImage.apply {
             val linearLayoutManager = LinearLayoutManager(activity)
             linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
             layoutManager = linearLayoutManager
             inviteFriendAdapter =
                 InviteFriendAdapter(
                     this@FriendInviteActivity, strangerList,
                     this@FriendInviteActivity
                 )
             inviteFriendAdapter.adapterItemClickListener
             adapter = inviteFriendAdapter
         }*/

    /*    binding.rvHomeRecycler.apply {
            val line = LinearLayoutManager(activity)
            layoutManager = line
            homeAdaptor = HomeFragmentRecyclerAdaptor(activity) { position, view ->

            }
            adapter = homeAdaptor

        }*/

//        binding.couponRecycler.layoutManager = LinearLayoutManager(this)
//        couponAdapter = CouponAdapter(
//            this, couponList
//        ) { position, view ->
//            val couponModel = couponList.get(position)
//            val bundle = Bundle()
//            bundle.putParcelable(KEY_COUPON_MODEL, couponModel)
//            val couponIntent=Intent()
//            couponIntent.putExtras(bundle)
//            setResult(RESULT_OK, couponIntent)
//            finish()
//        }
//        binding.couponRecycler.adapter = couponAdapter

        binding.rvHomeRecycler.layoutManager = LinearLayoutManager(activity)
        homeAdaptor = HomeFragmentRecyclerAdaptor(activity){
            position,view ->
            Utils.showSnackBar(binding.rvHomeRecycler,"mes")
        }
        binding.rvHomeRecycler.adapter = homeAdaptor
    }
}