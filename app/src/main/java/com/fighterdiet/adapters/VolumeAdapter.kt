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
import com.fighterdiet.activities.FilterActivity
import com.fighterdiet.data.model.responseModel.GetAllergyResponseModel
import com.fighterdiet.data.model.responseModel.GetVolumeResponseModel
import com.fighterdiet.databinding.ItemDietryInfoBinding
import com.fighterdiet.interfaces.RecyclerViewItemClickListener
import com.fighterdiet.model.VolumeModel

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
        holder.binding?.cbSelect?.isChecked = list[position].isChecked
        holder.binding?.cbSelect?.setOnCheckedChangeListener { buttonView, isChecked ->
            volumeListener?.volumeAdapterListener(position, list[position])
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface VolumeCountListener{
        fun volumeAdapterListener(position: Int, resultModel: GetVolumeResponseModel.Result)
    }
}