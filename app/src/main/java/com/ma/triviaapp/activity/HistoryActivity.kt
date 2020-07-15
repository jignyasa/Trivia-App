package com.ma.triviaapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ma.triviaapp.R
import com.ma.triviaapp.adapter.HistoryAdapter
import com.ma.triviaapp.roomdb.DatabaseHelper
import com.ma.triviaapp.roomdb.entity.HistoryItem
import com.ma.triviaapp.roomdb.entity.QuestionAnswerDetailEntity
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    private lateinit var historyAdapter: HistoryAdapter
    private var alHistory=ArrayList<HistoryItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        toolbar.title="History Data"
        historyAdapter= HistoryAdapter(this)
        rvHistory.adapter=historyAdapter
        rvHistory.layoutManager=LinearLayoutManager(this)
        getUserData()
    }

    /**
     * get user data
     */
    private fun getUserData() {
        DatabaseHelper.getDataBase(this).getUserDao().getUserData().observe(this, Observer {
            if(it.size>0) {
                tvNoData.visibility=View.GONE
                rvHistory.visibility=View.VISIBLE
                for (item in it) {
                    alHistory.add(HistoryItem(item.userName, item.userId, item.timeMillies))
                }
                getQuestionAnswerData()
            }else
            {
                tvNoData.visibility=View.VISIBLE
                rvHistory.visibility=View.GONE
            }
        })
    }

    /**
     * get question answer data
     */
    private fun getQuestionAnswerData() {
        DatabaseHelper.getDataBase(this).getQuestionAnswerDao().getQuestionAnswerData().observe(this,
            Observer {
                if(it.size>0)
                {
                    for(historyData in alHistory)
                    {
                        val alData=it.filter { questionAnswerDetailEntity -> questionAnswerDetailEntity.userId==historyData.userId }
                        historyData.alQuestionAnswer=alData as ArrayList<QuestionAnswerDetailEntity>
                    }
                historyAdapter.addData(alHistory)
                }
            })
    }
}