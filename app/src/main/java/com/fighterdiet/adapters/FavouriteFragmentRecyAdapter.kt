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
import com.fighterdiet.data.model.responseModel.FavouriteListResponseModel
import com.fighterdiet.databinding.ItemHomeFragmentRecyclerDesignBinding
import java.util.ArrayList

class FavouriteFragmentRecyAdapter(
    private var context: FragmentActivity?,
    private var favouriteList: ArrayList<FavouriteListResponseModel.Favourite>,
    private var itemClickListener: (Any, Any) -> Unit
) : RecyclerView.Adapter<FavouriteFragmentRecyAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val binding: ItemHomeFragmentRecyclerDesignBinding? = DataBindingUtil.bind(itemView)

        init {
            itemView.setOnClickListener(this)
            binding?.tvCaloriesDescription?.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            when (view!!.id) {
                R.id.tvCaloriesDescription -> {

                    favouriteList[adapterPosition].isDescOpened = !favouriteList[adapterPosition].isDescOpened
                    notifyDataSetChanged()
                }

                else -> {
                    context?.startActivity(MemberShipActivity.getStartIntent(context!!))
                }

//                R.id.rlCalories -> {
//                    homeList.get(adapterPosition).isDescOpened = true
//                    notifyDataSetChanged()
//                }
//                else -> {
//                    context?.startActivity(RecipeInfoActivity.getStartIntent(context!!))
//                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_home_fragment_recycler_design, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(favouriteList[position].recipe_image)
            .placeholder(R.color.greencolor)
            .into(holder.binding!!.ivItemHome)

        holder.binding.tvCaloriesDescription.visibility = if (favouriteList[position].isDescOpened) View.VISIBLE else View.GONE

        favouriteList[position].recipe_name.let {
            holder.binding.tvRecipeName.text = it
        }
        /*holder.binding?.ivItemHome?.setImageResource(homeList[position].i)
        if (homeList.get(position).isDescOpened) {
            holder.binding?.rlCaloriesDesc?.visibility = View.VISIBLE
        } else {
            holder.binding?.rlCaloriesDesc?.visibility = View.GONE
        }*/
    }

    override fun getItemCount(): Int {
        return favouriteList.size
    }
}