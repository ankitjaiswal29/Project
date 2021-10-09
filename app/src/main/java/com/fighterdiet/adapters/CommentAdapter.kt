package com.fighterdiet.adapters

import android.os.Build
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R
import com.fighterdiet.data.model.responseModel.CommentListResponseModel
import com.fighterdiet.databinding.ItemCommentsBinding
import com.fighterdiet.interfaces.RecyclerItemClickListener
import com.fighterdiet.interfaces.RecyclerViewItemClickListener
import kotlinx.android.synthetic.main.item_delete_spam.view.*


class CommentAdapter(
    private var commentList: List<CommentListResponseModel.CommentRecipe>,
    private var itemClickListener: RecyclerItemClickListener
) : RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val binding: ItemCommentsBinding? = DataBindingUtil.bind(itemView)
        init {
            binding?.ivMore?.setOnClickListener(this)
        }

        override fun onClick(viewType: View?) {

            when(viewType?.id){

                R.id.iv_more ->{
                    showPopUpCommunities(viewType)
                }

            }

        }

        private fun showPopUpCommunities(viewType: View) {
            var mPopupWindowFilter: PopupWindow? = null
            val customView: View = LayoutInflater.from(viewType.context).inflate(
                R.layout.item_delete_spam,
                null
            )

            mPopupWindowFilter = PopupWindow(
                customView,
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )

            mPopupWindowFilter.isOutsideTouchable = true
            // constraintRoot.setBackgroundResource(R.color.color_gray)
//            // constraintRoot.background.alpha = 129
//            mPopupWindowFilter!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

            if (Build.VERSION.SDK_INT >= 21) {
                mPopupWindowFilter.elevation = 5.0f
            }

            mPopupWindowFilter.showAsDropDown(viewType)

            customView.tv_delete.setOnClickListener {
                mPopupWindowFilter.dismiss()
                itemClickListener.onItemClick(adapterPosition, commentList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_comments, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val content = SpannableString(commentList[position].user_name)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        holder.binding?.userName?.text = content
        holder.binding?.commentTime?.text = commentList[position].updated_at
        holder.binding?.comment?.text = commentList[position].comments
        // holder.binding?.imvItemHome?.setImageResource(commentList[position].image)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

}
