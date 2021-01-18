package com.fighterdiet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R
import com.fighterdiet.activities.MemberShipActivity
import com.fighterdiet.activities.RecipeInfoActivity
import com.fighterdiet.databinding.ItemHomeFragmentRecyclerDesignBinding
import com.fighterdiet.interfaces.RecyclerViewItemClickListener
import com.fighterdiet.models.home_frag.HomeModel

class FavouriteFragmentRecyAdapter(
    private var context: FragmentActivity?,
    private var homeList:ArrayList<HomeModel>,
    private var itemClickListener: RecyclerViewItemClickListener?
):RecyclerView.Adapter<FavouriteFragmentRecyAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val binding : ItemHomeFragmentRecyclerDesignBinding? = DataBindingUtil.bind(itemView)

        init {
            itemView.setOnClickListener(this)
            binding?.rlCalories?.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            when (view!!.id) {
                R.id.rlCalories -> {

                    if (homeList.get(adapterPosition).isDescOpened){
                        homeList.get(adapterPosition).isDescOpened = false
                    }else{
                        homeList.get(adapterPosition).isDescOpened = true
                    }
                    notifyDataSetChanged()
                }

                else ->{

                    if (adapterPosition%2 != 0){
                        context?.startActivity(RecipeInfoActivity.getStartIntent(context!!))
                    }else  context?.startActivity(MemberShipActivity.getStartIntent(context!!))
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

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.binding?.imvItemHome?.setImageResource(homeList[position].image)
        if (homeList.get(position).isDescOpened) {
            holder.binding?.rlCaloriesDesc?.visibility = View.VISIBLE
        } else {
            holder.binding?.rlCaloriesDesc?.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return homeList.size
    }
}