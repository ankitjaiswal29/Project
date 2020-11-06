package com.fighterdiet.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R

class QuizAnswerAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class QuizTypeOne(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    inner class QuizTypeTwo(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    inner class QuizTypeThree(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


    override fun getItemViewType(position: Int): Int {
        return 1
    }

    override fun getItemCount(): Int {
        return 10
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

    }
}

interface ViewType {
    companion object {
        const val TYPE_QUIZ_ONE: Int = 1
        const val TYPE_QUIZ_TWO: Int = 2
        const val TYPE_QUIZ_THREE: Int = 3
    }
}