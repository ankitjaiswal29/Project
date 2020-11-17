package com.fighterdiet.adapters

import android.R.attr.button
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R
import com.fighterdiet.databinding.ItemCommentsBinding
import com.fighterdiet.interfaces.RecyclerViewItemClickListener
import com.fighterdiet.model.CommentModel


class CommentAdapter(
    private var context: FragmentActivity?,
    private var commentList: ArrayList<CommentModel>,
    private var itemClickListener: RecyclerViewItemClickListener?
) : RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
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

            mPopupWindowFilter.setOutsideTouchable(true)
            // constraintRoot.setBackgroundResource(R.color.color_gray)
//            // constraintRoot.background.alpha = 129
//            mPopupWindowFilter!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

            if (Build.VERSION.SDK_INT >= 21) {
                mPopupWindowFilter.elevation = 5.0f
            }

            mPopupWindowFilter.showAsDropDown(viewType)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_comments, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val content = SpannableString(commentList[position].personName)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        holder.binding?.userName?.text = content
        holder.binding?.commentTime?.text = commentList[position].time
        holder.binding?.comment?.text = commentList[position].comment
        // holder.binding?.imvItemHome?.setImageResource(commentList[position].image)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

}