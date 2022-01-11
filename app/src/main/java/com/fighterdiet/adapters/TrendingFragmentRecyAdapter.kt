package com.fighterdiet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.fighterdiet.R
import com.fighterdiet.data.model.responseModel.TrendingListResponseModel
import com.fighterdiet.databinding.ItemHomeFragmentRecyclerDesignBinding
import com.fighterdiet.utils.Utils
import java.net.URI
import java.net.URL

class TrendingFragmentRecyAdapter(
    private var context: FragmentActivity?,
    private var trendingList: ArrayList<TrendingListResponseModel.Result>,
    private var itemClickListener: (Int, TrendingListResponseModel.Result) -> Unit
) : RecyclerView.Adapter<TrendingFragmentRecyAdapter.MyViewHolder>() {


    private lateinit var circularProgressDrawable: CircularProgressDrawable

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding: ItemHomeFragmentRecyclerDesignBinding? = DataBindingUtil.bind(itemView)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrendingFragmentRecyAdapter.MyViewHolder {
        circularProgressDrawable = CircularProgressDrawable(parent.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_home_fragment_recycler_design, parent, false)
        return MyViewHolder(view)
    }



    override fun onBindViewHolder(holder: TrendingFragmentRecyAdapter.MyViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(trendingList[position].recipe_image)
            .placeholder(circularProgressDrawable)
            .override(900)
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

    fun clearAll(){
        trendingList.clear()
        notifyDataSetChanged()
    }
}