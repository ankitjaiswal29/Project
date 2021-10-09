package com.fighterdiet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R
import com.fighterdiet.data.model.responseModel.RecipeContentResponseModel
import com.fighterdiet.databinding.ItemFillingDesignBinding
import com.fighterdiet.interfaces.RecyclerViewItemClickListener
import kotlinx.android.synthetic.main.item_filling_design.view.*
import kotlinx.android.synthetic.main.item_quiz_type_two.view.*

class IngredientsFillingRecyAdapter(
    private var ingredientData: List<RecipeContentResponseModel.Ingredient.Data>,
):RecyclerView.Adapter<IngredientsFillingRecyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val binding : ItemFillingDesignBinding? = DataBindingUtil.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_filling_design, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tv_weight.text = ingredientData[position].weight
        holder.itemView.tv_unit.text = ingredientData[position].unit
        holder.itemView.tv_ingredient_desc.text = ingredientData[position].ingredients
    }

    override fun getItemCount(): Int {
        return ingredientData.size
    }
}