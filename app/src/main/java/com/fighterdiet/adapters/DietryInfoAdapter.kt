package com.fighterdiet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R
import com.fighterdiet.databinding.ItemDietryInfoBinding
import com.fighterdiet.data.model.responseModel.GetAllergyResponseModel


class DietryInfoAdapter(
    private var list: List<GetAllergyResponseModel.Result>,
    private var itemClickListener: DietaryCountListener?
):RecyclerView.Adapter<DietryInfoAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val binding : ItemDietryInfoBinding? = DataBindingUtil.bind(itemView)

        init {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_dietry_info, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding?.tvTitle?.text = list[position].allergy_name
        holder.binding?.cbSelect?.isChecked = list[position].isChecked
        holder.binding?.cbSelect?.setOnCheckedChangeListener { buttonView, isChecked ->
            itemClickListener?.dietaryInfoAdapterListener(position, list[position])
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface DietaryCountListener{
        fun dietaryInfoAdapterListener(position: Int, resultModel: GetAllergyResponseModel.Result)
    }
}