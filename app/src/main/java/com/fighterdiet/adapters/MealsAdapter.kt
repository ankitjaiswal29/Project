package com.fighterdiet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R
import com.fighterdiet.data.model.responseModel.GetMealResponseModel
import com.fighterdiet.databinding.ItemDietryInfoBinding

class MealsAdapter(
    private var list: ArrayList<GetMealResponseModel.Result>,
    private var itemClickListener: MealsCountListener?
):RecyclerView.Adapter<MealsAdapter.MyViewHolder>() {


    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val binding : ItemDietryInfoBinding? = DataBindingUtil.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_dietry_info, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding?.tvTitle?.setText(list[position].meal_name)
        holder.binding?.cbSelect?.isChecked = list[position].isChecked
        holder.binding?.cbSelect?.setOnCheckedChangeListener { buttonView, isChecked ->
            itemClickListener?.mealsInfoAdapterListener(position, list[position])
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface MealsCountListener{
        fun mealsInfoAdapterListener(position: Int, resultModel: GetMealResponseModel.Result)
    }
}
