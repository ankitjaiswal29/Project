package com.fighterdiet.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.fighterdiet.R
import com.fighterdiet.databinding.ItemFaqInfoBinding
import com.fighterdiet.model.FaqModel

class FaqAdapter(
    private val context: Context,
    private var dataList: List<FaqModel>,
    private val parentView: ViewGroup
) : RecyclerView.Adapter<FaqAdapter.MyViewHolder>() {
    private var itemOpen: ItemFaqInfoBinding? = null

    inner class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ), View.OnClickListener {
        val binding: ItemFaqInfoBinding?
        override fun onClick(v: View) {
            if (v.id == R.id.product_info_header_section) {
                if (binding!!.productInfoDescriptionSection.visibility != View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(parentView)
                    binding.productInfoIcon.animate().rotation(180f)
                    binding.productInfoDescriptionSection.visibility = View.VISIBLE
                    if (itemOpen != null) {
                        toggleItemOpen(false, itemOpen)
                    }
                    itemOpen = binding
                    toggleItemOpen(true, binding)
                } else {
                    TransitionManager.beginDelayedTransition(parentView)
                    binding.productInfoIcon.animate().rotation(0f)
                    binding.productInfoDescriptionSection.visibility = View.GONE
                    itemOpen = null
                }
            }
        }

        init {
            binding = DataBindingUtil.bind(itemView!!)
            binding!!.productInfoHeaderSection.setOnClickListener(this)
        }
    }

    private fun toggleItemOpen(open: Boolean, binding: ItemFaqInfoBinding?) {
        if (open) {
            TransitionManager.beginDelayedTransition(parentView)
            binding!!.productInfoIcon.animate().rotation(180f)
            binding.productInfoDescriptionSection.visibility = View.VISIBLE
        } else {
            TransitionManager.beginDelayedTransition(parentView)
            binding!!.productInfoIcon.animate().rotation(0f)
            binding.productInfoDescriptionSection.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_faq_info, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataList[position]
        holder.binding!!.productInfoTitle.text = item.title
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setDataList(dataList: List<FaqModel>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }
}