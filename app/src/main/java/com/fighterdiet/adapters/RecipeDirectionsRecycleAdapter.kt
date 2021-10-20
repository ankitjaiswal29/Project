package com.fighterdiet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.data.model.responseModel.RecipeContentResponseModel
import com.fighterdiet.databinding.ItemDirectionsLayoutBinding

class RecipeDirectionsRecycleAdapter(
    val directionModel: List<RecipeContentResponseModel.Direction>
    ):RecyclerView.Adapter<RecipeDirectionsRecycleAdapter.MyViewHolder>() {

    class MyViewHolder(val bind: ItemDirectionsLayoutBinding):RecyclerView.ViewHolder(bind.root) {

        fun bindItems(direction: RecipeContentResponseModel.Direction) {
            bind.tvDirectionHeading.text = direction.title
            bind.tvDirectionDesc.text = direction.direction
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemDirectionsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(directionModel[position])
    }

    override fun getItemCount(): Int {
        return directionModel.size
    }
}