package com.fighterdiet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fighterdiet.R
import com.fighterdiet.activities.MemberShipActivity
import com.fighterdiet.data.model.responseModel.RecipeListResponseModel
import com.fighterdiet.databinding.ItemHomeFragmentRecyclerDesignBinding
import com.fighterdiet.interfaces.RecyclerViewItemClickListener
import com.fighterdiet.models.home_frag.HomeModel
import java.util.*

class HomeFragmentRecyclerAdapter(
    var context: FragmentActivity?,
    private var homeList: ArrayList<RecipeListResponseModel.Recipies>,
    private var itemClickListener: RecyclerViewItemClickListener?
) : RecyclerView.Adapter<HomeFragmentRecyclerAdapter.MyViewHolder>() {
    var i = 0

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val binding: ItemHomeFragmentRecyclerDesignBinding? = DataBindingUtil.bind(itemView)

        init {
            itemView.setOnClickListener(this)
            binding?.rlCalories?.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            when (view!!.id) {
                R.id.rlCalories -> {
                    homeList[adapterPosition].isDescOpened = !homeList.get(adapterPosition).isDescOpened
                    notifyDataSetChanged()
                }

                else -> {
                    context?.startActivity(MemberShipActivity.getStartIntent(context!!))
                }

                //Note   Don't remove this code until finalize by client

                /*  else -> {
                      i++
                      val handler = Handler()
                      val run: Runnable = object : Runnable {
                          override fun run() {
                              i = 0
                          }
                      }
                      if (i == 1) {
                          clearSelection()
                          homeList[adapterPosition].isselected = true
                          notifyDataSetChanged()
                          handler.postDelayed(run, 400)
                          context?.startActivity(RecipeInfoActivity.getStartIntent(context!!))
                      } else if (i == 2) {
                          context?.startActivity(RecipeInfoActivity.getStartIntent(context!!))
                      }
                  }*/
            }
        }
    }

    fun clearSelection() {
        for (i in 0 until homeList.size) {
            homeList[i].isSelected = false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_home_fragment_recycler_design, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(homeList[position].recipe_image)
            .placeholder(R.color.greencolor)
            .into(holder.binding!!.ivItemHome)
        if (homeList.get(position).isDescOpened) {
            holder.binding.rlCaloriesDesc.visibility = View.VISIBLE
        } else {
            holder.binding.rlCaloriesDesc.visibility = View.GONE
        }

        homeList[position].recipe_name.let {
            holder.binding.tvRecipeName.text = it
        }

//
//        if (homeList.get(position).isselected) {
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                holder.binding?.imvItemHome?.foreground = ContextCompat.getDrawable(
//                    context!!,
//                    R.drawable.bg_image_selected)
//            }
//        } else {
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                holder.binding?.imvItemHome?.foreground = null
//            }
//        }
    }

    override fun getItemCount(): Int {
        return homeList.size
    }

}