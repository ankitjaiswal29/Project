package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.QuizAnswerAdapter
import com.fighterdiet.databinding.ActivityQuizBinding
import com.fighterdiet.model.Question
import com.fighterdiet.utils.ProgressDialog
import com.fighterdiet.utils.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import java.lang.reflect.Type


class QuizActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityQuizBinding
    private lateinit var qusArrayList: ArrayList<Question>
    private lateinit var adapter: QuizAnswerAdapter
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz)
        initialise()
    }

    private fun initialise() {
        binding.btnNext.setOnClickListener(this)
        binding.ivPrevious.setOnClickListener(this)
        setAdapter()
        getQuizData()
    }

    companion object {
        const val TAG = "QuizActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, QuizActivity::class.java)
        }
    }

    private fun getQuizData() {
        val strQuizData = ProgressDialog.loadJSONFromAsset(this)
        Log.e(TAG, ">>>>> Data :: $strQuizData")
        try {
            val gson = Gson()
            val listType: Type = object : TypeToken<ArrayList<Question>>() {}.type
            qusArrayList = gson.fromJson(strQuizData, listType)
            position++;
            setCurrentQuestion()

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


    private fun setAdapter() {
        adapter = QuizAnswerAdapter(this);
        binding.rvAnswer.adapter = adapter
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNext -> {
                Utils.hideKeyboard(this)
                if (position < (qusArrayList.size - 1)) {
                    position++;
                    setCurrentQuestion()
                } else {
                    // Submit Quiz
                    finish()
                    startActivity(ProfileAfterQuestionsActivity.getStartIntent(this))
                }
            }

            R.id.ivPrevious -> {
                Utils.hideKeyboard(this)
                position--
                setCurrentQuestion()
            }

        }
    }

    private fun setCurrentQuestion() {
        val question: Question = qusArrayList.get(position)
        setQuestion(question)
        updateUI()
        if (question.type == 1 || question.type == 2) {
            binding.rvAnswer.layoutManager = LinearLayoutManager(this)
        } else {
            binding.rvAnswer.layoutManager = GridLayoutManager(this, 3)
        }
        adapter.setQuestion(question)
        adapter.notifyDataSetChanged()
    }

    private fun setQuestion(question: Question) {
        binding.tvQuestion.setText(question.question)
    }

    private fun updateUI() {
        if (position == 0) {
            // First
            binding.ivPrevious.visibility = View.GONE
            binding.btnNext.visibility = View.VISIBLE
        } else if (position == (qusArrayList.size - 1)) {
            // Last
            binding.ivPrevious.visibility = View.VISIBLE
            binding.btnNext.visibility = View.VISIBLE
        } else {
            binding.ivPrevious.visibility = View.VISIBLE
            binding.btnNext.visibility = View.VISIBLE
        }
    }


}