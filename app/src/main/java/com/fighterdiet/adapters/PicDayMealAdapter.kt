package com.fighterdiet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R
import com.fighterdiet.activities.RecipeInfoActivity
import com.fighterdiet.databinding.ItemHomeFragmentRecyclerDesignBinding
import com.fighterdiet.interfaces.RecyclerViewItemClickListener
import com.fighterdiet.models.home_frag.HomeModel

class PicDayMealAdapter(
    var context: FragmentActivity?,
    private var homeList: ArrayList<HomeModel>,
    private var itemClickListener: RecyclerViewItemClickListener?
) : RecyclerView.Adapter<PicDayMealAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val binding: ItemHomeFragmentRecyclerDesignBinding? = DataBindingUtil.bind(itemView)

        init {
            itemView.setOnClickListener(this)
            binding?.rlCalories?.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            var doubleClickLastTime = 0L
            if (itemClickListener!= null){

                    if(System.currentTimeMillis() - doubleClickLastTime < 300){
                        doubleClickLastTime = 0
//                        doAction()
                    }else{
                        itemClickListener!!.onItemClick(adapterPosition,view)
                        doubleClickLastTime = System.currentTimeMillis()
                    }

            }

            when(view?.id){
                R.id.rlCalories -> {
                    homeList.get(adapterPosition).isDescOpened = true
                    notifyDataSetChanged()
                }


            }
//            context?.startActivity(RecipeInfoActivity.getStartIntent(context!!))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_home_fragment_recycler_design, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding?.imvItemHome?.setImageResource(homeList[position].image)
        if (homeList.get(position).isDescOpened) {
            holder.binding?.rlCaloriesDesc?.visibility = View.VISIBLE
        } else {
            holder.binding?.rlCaloriesDesc?.visibility = View.GONE
        }
        
        if (homeList[position].isselected){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                holder.binding?.imvItemHome?.foreground = ContextCompat.getDrawable(context!!, R.drawable.bg_image_selected)
            }
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                holder.binding?.imvItemHome?.foreground = null
            }
        }
    }

    override fun getItemCount(): Int {
        return homeList.size
    }

}