package com.ma.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ma.triviaapp.R
import com.ma.triviaapp.constant.Constant
import com.ma.triviaapp.roomdb.DatabaseHelper
import com.ma.triviaapp.roomdb.entity.QuestionAnswerDetailEntity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_question_answer.*
import kotlinx.android.synthetic.main.lay_first_question.*
import kotlinx.android.synthetic.main.lay_second_question.*

class QuestionAnswerActivity : AppCompatActivity(), View.OnClickListener {
    private var answer = ""
    private var question = ""
    private var userId = 0
    private var alQuestionDetails = ArrayList<QuestionAnswerDetailEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_answer)
        init()
    }

    /**
     * if display child 2 then back to the child 1
     * otherwise back to user activity and delete current user
     */
    override fun onBackPressed() {
        if (vfQuestions.displayedChild == 1) {
            vfQuestions.showPrevious()
            toolbar.title =
                getString(R.string.question).plus("\t").plus(vfQuestions.displayedChild + 1)
        } else {
            deleteUser()
        }
    }

    /**
     * delete user if user not finish test and back to the user activity
     */
    private fun deleteUser() {
        Observable.fromCallable {
            DatabaseHelper.getDataBase(this).getUserDao().deleteUserData(userId)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Unit> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Unit) {
                    finish()
                }

                override fun onError(e: Throwable) {
                    Log.e("QuestionAnswerActivity", e.message)
                }

            })
    }

    private fun init() {
        toolbar.title = getString(R.string.question).plus("\t").plus(vfQuestions.displayedChild + 1)
        if (intent != null) {
            userId = intent.getIntExtra(Constant.USER_ID, 0)
        }
        btnNextQuestion.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        validation()
    }

    private fun validation() {
        if (vfQuestions.displayedChild == 0) {
            /**
             * get selected cricketer
             */
            answer = when (rgbCricketer.checkedRadioButtonId) {
                R.id.rdSachin -> getString(R.string.sachin_tendulkar)
                R.id.rdVirat -> getString(R.string.virat_kolli)
                R.id.rdAdam -> getString(R.string.adam_gilchirst)
                R.id.rdJacques -> getString(R.string.jacques_kallis)
                else -> ""
            }
            question = getString(R.string.question1)
        } else if (vfQuestions.displayedChild == 1) {
            /**
             * get flag colors
             */
            val alAns = ArrayList<String>()
            if (chkOrange.isChecked)
                alAns.add(getString(R.string.orange))
            if (chkWhite.isChecked)
                alAns.add(getString(R.string.white))
            if (chkYellow.isChecked)
                alAns.add(getString(R.string.yellow))
            if (chkGreen.isChecked)
                alAns.add(getString(R.string.green))
            question = getString(R.string.question2)
            answer = TextUtils.join(",", alAns)
        }
        /**
         * check answer is empty or not
         */
        if (answer.isEmpty())
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
        else
            addDataIntoModel(question, answer)
    }

    /**
     * insert question answer data
     */
    private fun addDataIntoModel(question: String, answer: String) {
        val item = QuestionAnswerDetailEntity()
        item.userId = userId
        item.question = question
        item.answer = answer
        alQuestionDetails.add(item)
        if (vfQuestions.displayedChild == 0) {
            vfQuestions.showNext()
            toolbar.title =
                getString(R.string.question).plus("\t").plus(vfQuestions.displayedChild + 1)
        } else {
            startActivity(
                Intent(this@QuestionAnswerActivity, SummaryActivity::class.java)
                    .putExtra(Constant.USER_ID, userId)
                    .putExtra(Constant.QUESTION_ANSWER_DATA, alQuestionDetails)
            )
        }
    }
}