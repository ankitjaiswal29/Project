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
import com.fighterdiet.data.model.responseModel.TrendingListResponseModel
import com.fighterdiet.databinding.ItemHomeFragmentRecyclerDesignBinding
import com.fighterdiet.interfaces.RecyclerViewItemClickListener
import com.fighterdiet.models.home_frag.HomeModel

class TrendingFragmentRecyAdapter(
    private var context: FragmentActivity?,
    private var trendingList: ArrayList<TrendingListResponseModel.Result>,
    private var itemClickListener: RecyclerViewItemClickListener?
) : RecyclerView.Adapter<TrendingFragmentRecyAdapter.MyViewHolder>() {


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

                   /* if (trendingList.get(adapterPosition).isDescOpened) {
                        trendingList.get(adapterPosition).isDescOpened = false
                    } else {
                        trendingList.get(adapterPosition).isDescOpened = true
                    }
                    notifyDataSetChanged()*/
                }

                else -> {
                    context?.startActivity(MemberShipActivity.getStartIntent(context!!))
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrendingFragmentRecyAdapter.MyViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_home_fragment_recycler_design, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingFragmentRecyAdapter.MyViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(trendingList[position].recipe_image)
            .placeholder(R.color.greencolor)
            .into(holder.binding!!.ivItemHome)
      //  holder.binding?.ivItemHome?.setImageResource(trendingList[position].recipe_image)
       /* if (trendingList.get(position).isDescOpened) {
            holder.binding?.rlCaloriesDesc?.visibility = View.VISIBLE
        } else {
            holder.binding?.rlCaloriesDesc?.visibility = View.GONE
        }*/
    }

    override fun getItemCount(): Int {
        return trendingList.size
    }
}