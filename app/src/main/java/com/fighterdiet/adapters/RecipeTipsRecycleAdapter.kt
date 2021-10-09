package com.fighterdiet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R
import com.fighterdiet.data.model.responseModel.RecipeContentResponseModel
import com.fighterdiet.databinding.ItemDirectionsLayoutBinding
import kotlinx.android.synthetic.main.fragment_directions.view.*
import kotlinx.android.synthetic.main.item_directions_layout.view.*
import kotlinx.android.synthetic.main.item_ingredients_layout.view.*

class RecipeTipsRecycleAdapter(
    val tipsModel: List<RecipeContentResponseModel.Tip>
    ):RecyclerView.Adapter<RecipeTipsRecycleAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        fun bindItems(tips: RecipeContentResponseModel.Tip) {
            itemView.tv_direction_heading.text = "Tips #${adapterPosition+1}"
            itemView.tv_direction_desc.text = tips.tips
        }

        val binding : ItemDirectionsLayoutBinding? = DataBindingUtil.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_directions_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(tipsModel[position])
    }

    override fun getItemCount(): Int {
        return tipsModel.size
    }
}