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

class FavouriteFragmentRecyAdapter(
    private var context: FragmentActivity?,
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
        holder: FavouriteFragmentRecyAdapter.MyViewHolder,
        position: Int
    ) {
    }

    override fun getItemCount(): Int {
        return 3
    }
}