package com.fighterdiet.adapters

import android.os.Build
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R
import com.fighterdiet.data.model.responseModel.CommentListResponseModel
import com.fighterdiet.databinding.ItemCommentsBinding
import com.fighterdiet.databinding.ItemDeleteSpamBinding
import com.fighterdiet.databinding.ItemDirectionsLayoutBinding
import com.fighterdiet.interfaces.RecyclerItemClickListener
import com.fighterdiet.utils.Constants
import com.fighterdiet.utils.PrefManager


class CommentAdapter(
    private var commentList: List<CommentListResponseModel.CommentRecipe>,
    private var itemClickListener: RecyclerItemClickListener
) : RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {

    inner class MyViewHolder(val bindin: ItemCommentsBinding) : RecyclerView.ViewHolder(bindin.root), View.OnClickListener {
        init {
            bindin.ivMore.setOnClickListener(this)
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
            val bindDialog: ItemDeleteSpamBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewType.context), R.layout.item_delete_spam, null, false)

            mPopupWindowFilter = PopupWindow(
                bindDialog.root,
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

            if(commentList[adapterPosition].user_id == PrefManager.getString(PrefManager.KEY_USER_ID)?.toInt()?:0) {
                bindDialog.tvDelete.visibility = View.VISIBLE
            }

            mPopupWindowFilter.showAsDropDown(viewType)

            bindDialog.tvDelete.setOnClickListener {
                mPopupWindowFilter.dismiss()
                itemClickListener.onItemClick(Constants.OPERATION_DELETE, commentList[adapterPosition])
            }

            bindDialog.tvSpam.setOnClickListener {
                mPopupWindowFilter.dismiss()
                itemClickListener.onItemClick(Constants.OPERATION_REPORT_SPAM, commentList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
//        val view =
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.item_comments, parent, false)
//        return MyViewHolder(view)

        val binding = ItemCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val content = SpannableString(commentList[position].user_name)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        holder.bindin.userName.text = content
        if(commentList[position].updated_at.isNotEmpty()){
            val date = commentList[position].updated_at.split("T")
            holder.bindin.commentTime.text = "${date[0]} ${date[1].split(".")[0]}"
        }
        holder.bindin.comment.text = commentList[position].comments
        // holder.binding?.imvItemHome?.setImageResource(commentList[position].image)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

}
