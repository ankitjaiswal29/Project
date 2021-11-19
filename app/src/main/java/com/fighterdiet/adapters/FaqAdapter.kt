package com.fighterdiet.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.fighterdiet.R
import com.fighterdiet.data.model.responseModel.FaqListResponseModel
import com.fighterdiet.databinding.ItemFaqInfoBinding

class FaqAdapter(
    private val context: Context,
    private var dataList: List<FaqListResponseModel.Result>,
    private val parentView: ViewGroup
) : RecyclerView.Adapter<FaqAdapter.MyViewHolder>(){
    private var itemOpen: ItemFaqInfoBinding? = null
    var dataFilterList = ArrayList<FaqListResponseModel.Result>()

    inner class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ), View.OnClickListener {
        val binding: ItemFaqInfoBinding? = DataBindingUtil.bind(itemView!!)
        override fun onClick(v: View) {
            when (v){
                binding?.productInfoHeaderSection -> {
                    toggleItemOpen(!dataList[absoluteAdapterPosition].isOpened, binding)
                }
            }
        }


        fun toggleItemOpen(open: Boolean, binding: ItemFaqInfoBinding?) {
            if(binding==null)
                return

            dataList[absoluteAdapterPosition].isOpened = open
            if (open) {
//                TransitionManager.beginDelayedTransition(binding.clFaqItemRoot)
                binding.productInfoIcon.animate().rotation(180f)
                binding.productInfoDescriptionSection.visibility = View.VISIBLE
            } else {
//                TransitionManager.beginDelayedTransition(binding.clFaqItemRoot)
                binding.productInfoIcon.animate().rotation(0f)
                binding.productInfoDescriptionSection.visibility = View.GONE
            }
//            notifyItemChanged(absoluteAdapterPosition)
        }

        init {
            dataFilterList = dataList as ArrayList<FaqListResponseModel.Result>
            binding!!.productInfoHeaderSection.setOnClickListener(this)
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_faq_info, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataList[position]
        holder.binding!!.productInfoTitle.text = item.question
        holder.binding.productInfoSubtitle.text=item.answer
        holder.toggleItemOpen(dataList[position].isOpened, holder.binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setDataList(dataList: List<FaqListResponseModel.Result>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }
}