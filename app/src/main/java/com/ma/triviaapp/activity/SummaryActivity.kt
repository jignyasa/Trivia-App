package com.ma.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ma.triviaapp.R
import com.ma.triviaapp.constant.Constant
import com.ma.triviaapp.roomdb.DatabaseHelper
import com.ma.triviaapp.roomdb.entity.QuestionAnswerDetailEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_summary.*

class SummaryActivity : AppCompatActivity(), View.OnClickListener {
    private var userId = 0
    private var alQuestionAnswerData = ArrayList<QuestionAnswerDetailEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)
        init()
    }

    private fun init() {
        toolbar.title = getString(R.string.summary)
        if (intent != null) {
            userId = intent.getIntExtra(Constant.USER_ID, 0)
            alQuestionAnswerData =
                intent.getSerializableExtra(Constant.QUESTION_ANSWER_DATA) as ArrayList<QuestionAnswerDetailEntity>
            Log.e("data", alQuestionAnswerData.size.toString())
        }
        getUserData()
        getQuestionAnswerDetail()
        btnFinish.setOnClickListener(this)
        btnHistory.setOnClickListener(this)
    }

    /**
     * get question answer data from arraylist
     */
    private fun getQuestionAnswerDetail() {
        for (item in alQuestionAnswerData) {
            if (item.question.equals(this.getString(R.string.question1))) {
                tvDisplayQuestion1.text = item.question
                tvDisplayAnswer1.text = item.answer
            } else {
                tvDisplayQuestion2.text = item.question
                tvDisplayAnswer2.text = item.answer
            }
        }
    }

    /**
     * get user data as per userid from local database
     */
    private fun getUserData() {
        DatabaseHelper.getDataBase(this).getUserDao().getSingleUserData(id = userId).observe(this,
            Observer {
                tvDisplayUserName.text = getString(R.string.hello).plus("\t").plus(it.userName)
            })

    }

    /**
     * add question-answer detail in local database
     */
    fun insertData() {
        Observable.fromCallable {
            DatabaseHelper.getDataBase(this).getQuestionAnswerDao()
                .addQuestionAnswerAllData(alQuestionAnswerData)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : io.reactivex.Observer<Unit> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Unit) {
                    updateUserData()
                }

                override fun onError(e: Throwable) {
                    Log.e("InsertQuestionError", e.message)
                }

            })
    }

    /**
     * update user if user finished test in to database
     */
    private fun updateUserData() {
        Observable.fromCallable {
            DatabaseHelper.getDataBase(this).getUserDao().editUserData(userId, true)
        }.subscribe(object : io.reactivex.Observer<Unit> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: Unit) {
                startActivity(Intent(this@SummaryActivity, UserActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }

            override fun onError(e: Throwable) {
                Log.e("User Edit Eroor", e.message)
            }

        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnFinish -> {
                insertData()
            }
            R.id.btnHistory -> {
                startActivity(Intent(this@SummaryActivity, HistoryActivity::class.java))
            }
        }
    }
}