package com.fighterdiet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R
import com.fighterdiet.databinding.ItemFillingDesignBinding
import com.fighterdiet.interfaces.RecyclerViewItemClickListener

class IngredientsFillingRecyAdapter(
    private var context: FragmentActivity?,
    private var itemClickListener: RecyclerViewItemClickListener?
):RecyclerView.Adapter<IngredientsFillingRecyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val binding : ItemFillingDesignBinding? = DataBindingUtil.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_filling_design, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 3
    }
}