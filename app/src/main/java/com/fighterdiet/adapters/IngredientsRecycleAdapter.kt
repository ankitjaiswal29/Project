package com.fighterdiet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.data.model.responseModel.RecipeContentResponseModel
import com.fighterdiet.databinding.ItemIngredientsLayoutBinding

class IngredientsRecycleAdapter(
    private val recipeIngredientModel: List<RecipeContentResponseModel.Ingredient>
):RecyclerView.Adapter<IngredientsRecycleAdapter.MyViewHolder>() {

    class MyViewHolder(val bindin: ItemIngredientsLayoutBinding):RecyclerView.ViewHolder(bindin.root) {
        init {
            bindin.rvIngredientDetail.layoutManager = LinearLayoutManager(itemView.context)
        }

        fun bindItems(ingredient: RecipeContentResponseModel.Ingredient) {
            bindin.tvIngredientTitle.text = ingredient.titie
            bindin.rvIngredientDetail.adapter = IngredientsFillingRecyclerAdapter(ingredient.data)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val view =
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.item_ingredients_layout, parent, false)
//        return MyViewHolder(view)

        val binding = ItemIngredientsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(recipeIngredientModel[position])
    }

    override fun getItemCount(): Int {
        return recipeIngredientModel.size
    }
}