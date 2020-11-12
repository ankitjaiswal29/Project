package com.fighterdiet.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R
import com.fighterdiet.databinding.ItemGroceryListRecyBinding
import com.fighterdiet.interfaces.RecyclerViewItemClickListener
import com.fighterdiet.models.weekly_grocery_list.DairyModel

class WeeklyGroceryListRecyAdapter(
    private var context: FragmentActivity?,
    private var dairyList:ArrayList<DairyModel>,
    private var itemClickListener: RecyclerViewItemClickListener?
):RecyclerView.Adapter<WeeklyGroceryListRecyAdapter.MyViewHolder>() {


    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val binding :ItemGroceryListRecyBinding ? = DataBindingUtil.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_grocery_list_recy, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position%2==0){
            holder.binding?.clMain?.setBackgroundColor(ContextCompat.getColor(context!!, R.color.checkbox_background))
            holder.binding?.tvQuantity?.setText(dairyList[position].item_quantity)
            holder.binding?.tvItemName?.setText(dairyList[position].item_names)
            if (dairyList[position].selection){
                holder.binding?.cbQuant?.isChecked=true
            }else{
                holder.binding?.cbQuant?.isChecked=false
            }
        }else{
            holder.binding?.clMain?.setBackgroundColor(ContextCompat.getColor(context!!, R.color.white))
            holder.binding?.tvQuantity?.setText(dairyList[position].item_quantity)
            holder.binding?.tvItemName?.setText(dairyList[position].item_names)
            if (dairyList[position].selection){
                holder.binding?.cbQuant?.isChecked=true
            }else{
                holder.binding?.cbQuant?.isChecked=false
            }
        }
    }

    override fun getItemCount(): Int {
        return dairyList.size
    }
}