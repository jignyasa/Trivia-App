package com.ma.triviaapp.roomdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ma.triviaapp.roomdb.entity.QuestionAnswerDetailEntity
import com.ma.triviaapp.roomdb.entity.UserEntity

@Dao
interface QuestionAnswerDao {

    @Insert
    fun addQuestionAnswerData(item:QuestionAnswerDetailEntity)

    @Query("SELECT * FROM question_answer")
    fun getQuestionAnswerData():LiveData<List<QuestionAnswerDetailEntity>>

    @Query("SELECT * FROM question_answer WHERE userId IN (:id)")
    fun getQuestionAnswerData(id:Int):LiveData<List<QuestionAnswerDetailEntity>>

}