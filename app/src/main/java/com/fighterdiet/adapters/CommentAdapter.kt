package com.fighterdiet.adapters

import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemCommentsBinding? = DataBindingUtil.bind(itemView)

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