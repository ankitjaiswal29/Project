package com.fighterdiet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.data.model.responseModel.RecipeContentResponseModel
import com.fighterdiet.databinding.ItemFillingDesignBinding

class IngredientsFillingRecyAdapter(
    private var ingredientData: List<RecipeContentResponseModel.Ingredient.Data>,
):RecyclerView.Adapter<IngredientsFillingRecyAdapter.MyViewHolder>() {

    class MyViewHolder(val bindin: ItemFillingDesignBinding):RecyclerView.ViewHolder(bindin.root) {
        fun onBindView(data: RecipeContentResponseModel.Ingredient.Data) {
            bindin.tvWeight.text = data.weight
            bindin.tvUnit.text = data.unit
            bindin.tvIngredientDesc.text = data.ingredients
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFillingDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientsFillingRecyAdapter.MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBindView(ingredientData[position])
    }

    override fun getItemCount(): Int {
        return ingredientData.size
    }
}