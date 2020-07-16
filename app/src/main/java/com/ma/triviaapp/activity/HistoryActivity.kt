package com.ma.triviaapp.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ma.triviaapp.R
import com.ma.triviaapp.adapter.HistoryAdapter
import com.ma.triviaapp.roomdb.DatabaseHelper
import com.ma.triviaapp.roomdb.entity.HistoryItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    private lateinit var historyAdapter: HistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        init()
    }

    private fun init() {
        toolbar.title = getString(R.string.history_data)
        historyAdapter = HistoryAdapter()
        rvHistory.layoutManager = LinearLayoutManager(this)
        rvHistory.adapter = historyAdapter
        addHistoryData()
    }

    /**
     * add history data in to adapter
     */
    private fun addHistoryData() {
        Observable.fromCallable {
            val userItem = DatabaseHelper.getDataBase(this).getUserDao().getData()
            val alHistory = ArrayList<HistoryItem>()
            for (userEntity in userItem) {
                val list = DatabaseHelper.getDataBase(this).getQuestionAnswerDao()
                    .getAllQuestionAnswerData(userEntity.userId)
                val item = HistoryItem()
                item.userId = userEntity.userId
                item.userName = userEntity.userName
                item.timeMillis = userEntity.timeMillies
                item.alQuestionAnswer = ArrayList(list)
                if (list.size > 0)
                    alHistory.add(item)
            }
            alHistory
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : io.reactivex.Observer<ArrayList<HistoryItem>?> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(response: ArrayList<HistoryItem>) {
                    if (response.isNotEmpty()) {
                        tvNoData.visibility = View.GONE
                        rvHistory.visibility = View.VISIBLE
                        historyAdapter.addData(response)
                    } else {
                        tvNoData.visibility = View.VISIBLE
                        rvHistory.visibility = View.GONE
                    }
                }

                override fun onError(e: Throwable) {

                }
            })

    }

    /**
     * get question answer data
     */
    /* private fun getQuestionAnswerData() {
         DatabaseHelper.getDataBase(this).getQuestionAnswerDao().getQuestionAnswerData()
             .observe(this,
                 Observer {
                     if (it.isNotEmpty()) {
                         for (historyData in alHistory) {
                             val alData =
                                 it.filter { questionAnswerDetailEntity -> questionAnswerDetailEntity.userId == historyData.userId }
                             historyData.alQuestionAnswer =
                                 alData as ArrayList<QuestionAnswerDetailEntity>
                         }
                         tvNoData.visibility = View.GONE
                         rvHistory.visibility = View.VISIBLE
                         historyAdapter.addData(alHistory)
                     } else
                     {
                         tvNoData.visibility = View.VISIBLE
                         rvHistory.visibility = View.GONE
                     }
                 })
     }*/
}