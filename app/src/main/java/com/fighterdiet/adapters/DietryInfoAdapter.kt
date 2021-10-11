package com.fighterdiet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R
import com.fighterdiet.databinding.ItemDietryInfoBinding
import com.fighterdiet.data.model.responseModel.GetDietaryResponseModel


class DietryInfoAdapter(
    private var list: List<GetDietaryResponseModel.Result>,
    private var itemClickListener: DietaryCountListener?
):RecyclerView.Adapter<DietryInfoAdapter.MyViewHolder>() {

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
        holder.binding?.tvTitle?.text = list[position].allergy_name
        holder.binding?.ivSelection?.setBackgroundResource(
            if(!list[position].isChecked)
                R.drawable.dietry_icon_not_selected
            else
                R.drawable.dietry_icon_selected)

        holder.binding?.ivSelection?.setOnClickListener {
            list[position].isChecked = !list[position].isChecked
            notifyItemChanged(position)
            itemClickListener?.dietaryInfoAdapterListener(position, list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface DietaryCountListener{
        fun dietaryInfoAdapterListener(position: Int, resultModel: GetDietaryResponseModel.Result)
    }
}