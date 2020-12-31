package com.fighterdiet.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R
import com.fighterdiet.databinding.ItemDietryInfoBinding
import com.fighterdiet.model.DietryModel
import com.fighterdiet.activities.FilterActivity.Companion.count


class DietryInfoAdapter(
    var context: FragmentActivity?,
    private var list: ArrayList<DietryModel>,
    private var itemClickListener: DietaryCountListener?
):RecyclerView.Adapter<DietryInfoAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val binding : ItemDietryInfoBinding? = DataBindingUtil.bind(itemView)

        init {

            binding?.cbSelect?.setOnCheckedChangeListener(object :
                CompoundButton.OnCheckedChangeListener {

                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                    itemClickListener?.dietaryInfoAdapterListener(adapterPosition, binding.cbSelect)
                    Log.d( "onCheckedChanged: ",""+count)
                }
            })

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_dietry_info, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding?.tvTitle?.setText(list[position].dietryName)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface DietaryCountListener{
        fun dietaryInfoAdapterListener(position: Int,checkBox: CheckBox)
    }
}