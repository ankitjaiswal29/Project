package com.fighterdiet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R
import com.fighterdiet.databinding.ItemHomeFragmentRecyclerDesignBinding
import com.fighterdiet.interfaces.RecyclerViewItemClickListener
import com.fighterdiet.models.home_frag.HomeModel

class FavouriteFragmentRecyAdapter(
    private var context: FragmentActivity?,
    private var homeList:ArrayList<HomeModel>,
    private var itemClickListener: RecyclerViewItemClickListener?
):RecyclerView.Adapter<FavouriteFragmentRecyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val binding : ItemHomeFragmentRecyclerDesignBinding? = DataBindingUtil.bind(itemView)

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
    }

    override fun getItemCount(): Int {
        return homeList.size
    }
}