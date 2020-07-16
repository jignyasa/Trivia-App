package com.ma.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ma.triviaapp.R
import com.ma.triviaapp.constant.Constant
import com.ma.triviaapp.roomdb.DatabaseHelper
import com.ma.triviaapp.roomdb.entity.UserEntity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (validation()) {
            insertUserData()
        }
    }

    /**
     * insert user details
     */
    private fun insertUserData() {
        val item = UserEntity()
        item.userName = edtUserName.text.toString()
        item.timeMillies = System.currentTimeMillis()
        Observable.fromCallable {
            DatabaseHelper.getDataBase(this).getUserDao().addUserData(item)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Long) {
                    startActivity(
                        Intent(this@UserActivity, QuestionAnswerActivity::class.java)
                            .putExtra(Constant.USER_ID, t.toInt())
                    )
                }

                override fun onError(e: Throwable) {
                    Log.e("UserActivity", "${e.message}")
                }

            })
    }

    /**
     * check validation for username
     */
    private fun validation(): Boolean {
        if (edtUserName.text.isNullOrBlank()) {
            Toast.makeText(this, "Please enter valid Username", Toast.LENGTH_SHORT).show()
            return false
        } else
            return true
    }
}