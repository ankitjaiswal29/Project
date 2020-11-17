package com.fighterdiet.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R
import com.fighterdiet.databinding.ItemQuizTypeOneBinding
import com.fighterdiet.databinding.ItemQuizTypeThreeBinding
import com.fighterdiet.databinding.ItemQuizTypeTwoBinding
import com.fighterdiet.model.Question

class QuizAnswerAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var question: Question
    private var selectedOption = -1
    private var selectedOptions = arrayListOf<Int>()

    private var imageList2 = arrayListOf<Int>(
        R.mipmap.icn_milk_dairy, R.mipmap.icn_soy, R.mipmap.icn_seafood,
        R.mipmap.icn_shellfish, R.mipmap.icn_eggs, R.mipmap.icn_peanuts,
        R.mipmap.icn_waffle, R.mipmap.icn_tree_nuts, R.mipmap.icn_gluten,
        R.mipmap.icn_pescatarian, R.mipmap.icn_vegan, R.mipmap.icn_vegetable
    )

    private var imageList1 = arrayListOf<Int>(
        R.mipmap.icn_peanut_butter, R.mipmap.icn_chocoate, R.mipmap.icn_fruits,
        R.mipmap.icn_pizza, R.mipmap.icn_ice_cream, R.mipmap.icn_cookies,
        R.mipmap.icn_sandwich, R.mipmap.icn_spaghetti, R.mipmap.icn_sushi,
        R.mipmap.icn_burrito, R.mipmap.icn_french_fries, R.mipmap.icn_hamburger
    )

    inner class QuizTypeOne(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var binding: ItemQuizTypeOneBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
            binding?.tvAnswer?.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            selectedOption = adapterPosition
            notifyDataSetChanged()
        }
    }

    inner class QuizTypeTwo(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemQuizTypeTwoBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }

    }

    inner class QuizTypeThree(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var binding: ItemQuizTypeThreeBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            question.answers.get(adapterPosition).isSelected =
                !question.answers.get(adapterPosition).isSelected
            notifyDataSetChanged()
        }
    }


    override fun getItemViewType(position: Int): Int {
        return question.type
    }

    override fun getItemCount(): Int {
        return question.answers.size
    }

    fun setQuestion(question: Question) {
        selectedOption = -1
        selectedOptions.clear()
        this.question = question
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ViewType.TYPE_QUIZ_ONE -> {
                // Question Type One
                return QuizTypeOne(
                    LayoutInflater.from(context).inflate(R.layout.item_quiz_type_one, parent, false)
                )
            }
            ViewType.TYPE_QUIZ_TWO -> {
                // Question Type Two
                return QuizTypeTwo(
                    LayoutInflater.from(context).inflate(R.layout.item_quiz_type_two, parent, false)
                )
            }
            else -> {
                // Question Type Three
                return QuizTypeThree(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_quiz_type_three, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is QuizTypeOne) {
            if (position == selectedOption) {
                holder.binding?.tvAnswer?.setBackgroundResource(R.drawable.shape_answer_selected)
            } else {
                holder.binding?.tvAnswer?.setBackgroundResource(R.drawable.shape_answer_unselected)
            }
            holder.binding?.tvAnswer?.setText(question.answers.get(position).answerText)
        } else if (holder is QuizTypeTwo) {

        } else if (holder is QuizTypeThree) {
            if (question.id == 8) {
                holder.binding?.ivImage?.setImageResource(imageList1[position])
            } else {
                holder.binding?.ivImage?.setImageResource(imageList2[position])
            }
            holder.binding?.tvDesc?.setText(question.answers.get(position).answerText)
            if (question.answers.get(position).isSelected) {
                holder.binding?.groupSelector?.visibility = View.VISIBLE
            } else {
                holder.binding?.groupSelector?.visibility = View.GONE
            }
        }
    }
}

interface ViewType {
    companion object {
        const val TYPE_QUIZ_ONE: Int = 1
        const val TYPE_QUIZ_TWO: Int = 2
        const val TYPE_QUIZ_THREE: Int = 3
    }
}