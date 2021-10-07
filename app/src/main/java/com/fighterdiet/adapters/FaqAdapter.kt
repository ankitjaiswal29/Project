package com.fighterdiet.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.fighterdiet.R
import com.fighterdiet.data.model.responseModel.FaqListResponseModel
import com.fighterdiet.databinding.ItemFaqInfoBinding
import com.fighterdiet.model.FaqModel
import java.util.*
import kotlin.collections.ArrayList

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
            dataFilterList = dataList as ArrayList<FaqListResponseModel.Result>
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
        holder.binding!!.productInfoTitle.text = item.question
        holder.binding.productInfoSubtitle.text=item.answer
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    /*override fun getFilter(): Filter {
    return object :Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val charSearch = constraint.toString()
            if (charSearch.isEmpty()) {
                dataFilterList = dataList as ArrayList<FaqListResponseModel.Result>
            } else {
                val resultList = ArrayList<FaqListResponseModel.Result>()
                for (row in dataList) {
                    if (row.question.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                        resultList.add(row)
                    }
                }
                dataFilterList = resultList
            }
            val filterResults = FilterResults()
            filterResults.values = dataFilterList
            return filterResults
        }


        override fun publishResults(p0: CharSequence?, results: FilterResults?) {
            dataFilterList = results?.values as ArrayList<FaqListResponseModel.Result>
            notifyDataSetChanged()
        }

    }
    }
*/
    fun setDataList(dataList: List<FaqListResponseModel.Result>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }
}