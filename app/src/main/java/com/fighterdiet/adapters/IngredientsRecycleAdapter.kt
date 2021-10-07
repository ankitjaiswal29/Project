package com.fighterdiet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R
import com.fighterdiet.data.model.responseModel.RecipeContentResponseModel
import com.fighterdiet.databinding.ItemFillingDesignBinding
import com.fighterdiet.databinding.ItemIngredientsLayoutBinding
import kotlinx.android.synthetic.main.item_ingredients_layout.view.*

class IngredientsRecycleAdapter(
    val recipeIngredientModel: List<RecipeContentResponseModel.Ingredient>
    ):RecyclerView.Adapter<IngredientsRecycleAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        init {
            itemView.rv_ingredient_detail.layoutManager = LinearLayoutManager(itemView.context)
        }

        fun bindItems(ingredient: RecipeContentResponseModel.Ingredient) {
            itemView.tv_ingredient_title.text = ingredient.titie
            itemView.rv_ingredient_detail.adapter = IngredientsFillingRecyAdapter(ingredient.data)
        }

        val binding : ItemIngredientsLayoutBinding? = DataBindingUtil.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ingredients_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(recipeIngredientModel[position])
    }

    override fun getItemCount(): Int {
        return recipeIngredientModel.size
    }
}