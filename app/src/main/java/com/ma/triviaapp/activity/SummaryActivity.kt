package com.ma.triviaapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.ma.triviaapp.R
import com.ma.triviaapp.constant.Constant
import com.ma.triviaapp.roomdb.DatabaseHelper
import kotlinx.android.synthetic.main.activity_summary.*

class SummaryActivity : AppCompatActivity(),View.OnClickListener {
    private var userId=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)
        toolbar.title="Summary Activity"
        if(intent!=null)
        {
            userId=intent.getIntExtra(Constant.USER_ID,0)
        }
        getUserData()
        getQuestionAnswerDetail()
        btnFinish.setOnClickListener(this)
        btnHistory.setOnClickListener(this)
    }

    /**
     * get question answer data for single user
     */
    private fun getQuestionAnswerDetail() {
        DatabaseHelper.getDataBase(this).getQuestionAnswerDao().getQuestionAnswerData(userId).observe(this,
            Observer {
                for(item in it)
                {
                    if(item.question.equals(this.getString(R.string.question1))) {
                        tvDisplayQuestion1.text = item.question
                        tvDisplayAnswer1.text=item.answer
                    }
                    else {
                        tvDisplayQuestion2.text = item.question
                        tvDisplayAnswer2.text=item.answer
                    }
                }
            })
    }

    /**
     * get user data as per userid
     */
    private fun getUserData() {
        DatabaseHelper.getDataBase(this).getUserDao().getSingleUserData(id = userId).observe(this,
            Observer {
                tvDisplayUserName.text=getString(R.string.hello).plus("\t").plus(it.userName)
            })

    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.btnFinish->{
                startActivity(Intent(this@SummaryActivity,UserActivity::class.java))
                finish()
            }
            R.id.btnHistory->
            {
                startActivity(Intent(this@SummaryActivity,HistoryActivity::class.java))
            }
        }
    }
}