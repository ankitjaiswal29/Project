package com.fighterdiet.adapters

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.fighterdiet.R
import com.fighterdiet.data.model.responseModel.RecipeListResponseModel
import com.fighterdiet.databinding.ItemHomeFragmentRecyclerDesignBinding
import java.util.*

class HomeRecipeListRecyclerAdapter(
    var context: FragmentActivity?,
    private var recipeList: ArrayList<RecipeListResponseModel.Recipies>,
    private var itemClickListener: (Int, RecipeListResponseModel.Recipies) -> Unit
) : RecyclerView.Adapter<HomeRecipeListRecyclerAdapter.MyViewHolder>() {
    private lateinit var circularProgressDrawable: CircularProgressDrawable
    private var isLastUpdated: Boolean = false
    var i = 0

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val binding: ItemHomeFragmentRecyclerDesignBinding? = DataBindingUtil.bind(itemView)

        init {
            itemView.setOnClickListener(this)
            binding?.tvCaloriesDescription?.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            when (view!!.id) {
                R.id.tvCaloriesDescription -> {
                    recipeList[adapterPosition].isDescOpened = !recipeList.get(adapterPosition).isDescOpened
                    notifyDataSetChanged()
                }

                else -> {
                    itemClickListener.invoke(adapterPosition, recipeList[adapterPosition])
                }

            }
        }
    }



    fun clearSelection() {
        for (i in 0 until recipeList.size) {
            recipeList[i].isSelected = false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        circularProgressDrawable = CircularProgressDrawable(parent.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_home_fragment_recycler_design, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {
            Glide.with(holder.itemView.context)
                .load(recipeList[position].recipe_image)
                .placeholder(circularProgressDrawable)
                .into(holder.binding!!.ivItemHome)

            holder.binding.tvCaloriesDescription.visibility = if (recipeList[position].isDescOpened) View.VISIBLE else View.GONE

            recipeList[position].recipe_name.let {
                holder.binding.tvRecipeName.text = it
            }

        }
        catch (e:Exception){
            e.printStackTrace()
        }

//
//        if (homeList.get(position).isselected) {
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                holder.binding?.imvItemHome?.foreground = ContextCompat.getDrawable(
//                    context!!,
//                    R.drawable.bg_image_selected)
//            }
//        } else {
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                holder.binding?.imvItemHome?.foreground = null
//            }
//        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    fun addData(dataViews: ArrayList<RecipeListResponseModel.Recipies>?) {
        dataViews?.let {
            this.recipeList.addAll(dataViews)
            notifyDataSetChanged()
        }
    }

    fun getItemAtPosition(position: Int): RecipeListResponseModel.Recipies {
        return recipeList[position]
    }

    fun addLoadingView() {
        //Add loading item
        Handler(Looper.getMainLooper()).post {
//            recipeList.add(null)
            notifyItemInserted(recipeList.size - 1)
        }
    }

    fun removeLoadingView() {
        //Remove loading item
        if (recipeList.size != 0) {
            recipeList.removeAt(recipeList.size - 1)
            notifyItemRemoved(recipeList.size)
        }
    }

    fun addAll(result: List<RecipeListResponseModel.Recipies>) {
        if(isLastUpdated){
            recipeList.clear()
        }
        isLastUpdated = false
        recipeList.addAll(result)
        if(itemCount>0){
            notifyItemRangeInserted(itemCount, recipeList.size - 1)
            notifyDataSetChanged()
        }
    }

    fun updateAll(result: List<RecipeListResponseModel.Recipies>, isSearchMode: Boolean) {
        if(!isLastUpdated)
            recipeList.clear()

        if(isSearchMode)
            recipeList.clear()

        isLastUpdated = true
        recipeList.addAll(result)
        if(itemCount>0){
            notifyItemRangeInserted(itemCount, recipeList.size - 1)
            notifyDataSetChanged()
        }
    }

    fun clearAll(){
        recipeList.clear()
    }


}