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

    @Insert
    fun addQuestionAnswerAllData(list:ArrayList<QuestionAnswerDetailEntity>)

    @Query("SELECT * FROM question_answer WHERE userId IN (:id)")
    fun getAllQuestionAnswerData(id:Int): List<QuestionAnswerDetailEntity>

}