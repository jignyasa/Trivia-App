package com.ma.triviaapp.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ma.triviaapp.roomdb.dao.QuestionAnswerDao
import com.ma.triviaapp.roomdb.dao.UserDao
import com.ma.triviaapp.roomdb.entity.QuestionAnswerDetailEntity
import com.ma.triviaapp.roomdb.entity.UserEntity

@Database(entities = [UserEntity::class,QuestionAnswerDetailEntity::class],version = 1,exportSchema = false)
abstract class DatabaseHelper : RoomDatabase()
{
    companion object{
        fun getDataBase(context: Context):DatabaseHelper
        {
            var INSTANCE:DatabaseHelper?=null
            if(INSTANCE==null)
                INSTANCE= Room.databaseBuilder(context,DatabaseHelper::class.java,"question_answer").allowMainThreadQueries().build()
            return INSTANCE
        }
    }
    abstract fun getQuestionAnswerDao():QuestionAnswerDao

    abstract fun getUserDao():UserDao
}