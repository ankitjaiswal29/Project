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

class TrendingFragmentRecyAdapter(
    private var context: FragmentActivity?,
    private var trendingList: ArrayList<TrendingListResponseModel.Result>,
    private var itemClickListener: (Int, TrendingListResponseModel.Result) -> Unit
) : RecyclerView.Adapter<TrendingFragmentRecyAdapter.MyViewHolder>() {


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding: ItemHomeFragmentRecyclerDesignBinding? = DataBindingUtil.bind(itemView)

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
        trendingList[position].recipe_name.let {
            holder.binding.tvRecipeName.text = it
        }
        holder.binding.root.setOnClickListener {
            itemClickListener.invoke(position, trendingList[position])
        }
    }

    override fun getItemCount(): Int {
        return trendingList.size
    }
}