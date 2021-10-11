package com.fighterdiet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R
import com.fighterdiet.data.model.responseModel.GetVolumeResponseModel
import com.fighterdiet.databinding.ItemDietryInfoBinding

class VolumeAdapter(
    private var list: ArrayList<GetVolumeResponseModel.Result>,
    private var volumeListener: VolumeCountListener?
):RecyclerView.Adapter<VolumeAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val binding : ItemDietryInfoBinding? = DataBindingUtil.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_dietry_info, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding?.tvTitle?.setText(list[position].volume_name)
        holder.binding?.ivSelection?.setBackgroundResource(
            if(!list[position].isChecked)
                R.drawable.dietry_icon_not_selected
            else
                R.drawable.dietry_icon_selected)

        holder.binding?.ivSelection?.setOnClickListener {
            list[position].isChecked = !list[position].isChecked
            notifyItemChanged(position)
            volumeListener?.volumeAdapterListener(position, list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface VolumeCountListener{
        fun volumeAdapterListener(position: Int, resultModel: GetVolumeResponseModel.Result)
    }
}