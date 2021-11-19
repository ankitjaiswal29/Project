package com.fighterdiet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.data.model.responseModel.RecipeContentResponseModel
import com.fighterdiet.databinding.ItemDirectionsLayoutBinding

class RecipeTipsRecycleAdapter(
    val tipsModel: List<RecipeContentResponseModel.Tip>
    ):RecyclerView.Adapter<RecipeTipsRecycleAdapter.MyViewHolder>() {

    class MyViewHolder(val bind: ItemDirectionsLayoutBinding):RecyclerView.ViewHolder(bind.root) {

        fun bindItems(tips: RecipeContentResponseModel.Tip) {
            bind.tvDirectionHeading.text = "Tips #${bindingAdapterPosition+1}"
            bind.tvDirectionDesc.text = tips.tips
        }

        val binding : ItemDirectionsLayoutBinding? = DataBindingUtil.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemDirectionsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeTipsRecycleAdapter.MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(tipsModel[position])
    }

    override fun getItemCount(): Int {
        return tipsModel.size
    }
}