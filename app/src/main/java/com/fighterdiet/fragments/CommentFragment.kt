package com.fighterdiet.fragments

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.CommentAdapter
import com.fighterdiet.databinding.FragmentCommentBinding
import com.fighterdiet.model.CommentModel
import com.fighterdiet.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


/**
 * A simple [Fragment] subclass.
 * Use the [CommentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommentFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var mContext: Context
    private lateinit var binding: FragmentCommentBinding

    companion object {
        fun newInstance(): CommentFragment {
            return CommentFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val sheetView: View =
            layoutInflater.inflate(R.layout.fragment_comment, container, false)
        binding = DataBindingUtil.bind(sheetView)!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val content = SpannableString(getString(R.string.comments))
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        binding.tvTitle.text = content
        binding.ivBack.setOnClickListener(this)
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        val commentList: ArrayList<CommentModel> = ArrayList()
        commentList.add(
            CommentModel(
                "julie29",
                "2 hrs ago",
                getString(R.string.i_love_this_recipe_for_my_familynof_4_nhint_sub_appricots_for_peaches_it_gives_it_a_sweeter_taste),
                false
            )
        )
        commentList.add(
            CommentModel(
                "cookin52",
                "4 hrs ago",
                getString(R.string.i_love_this_recipe_for_my_familynof_4_nhint_sub_appricots_for_peaches_it_gives_it_a_sweeter_taste),
                false
            )
        )
        commentList.add(
            CommentModel(
                "Amberlyn22",
                "4 hrs ago",
                getString(R.string.i_love_this_recipe_for_my_familynof_4_nhint_sub_appricots_for_peaches_it_gives_it_a_sweeter_taste),
                true
            )
        )

        binding.rvComment.layoutManager = LinearLayoutManager(activity)
        val commentAdapter = CommentAdapter(activity, commentList) { position, view ->
            Utils.showSnackBar(binding.rvComment, "mes")
        }
        binding.rvComment.adapter = commentAdapter
    }

    override fun onClick(view: View?) {
        dismiss()
    }
}