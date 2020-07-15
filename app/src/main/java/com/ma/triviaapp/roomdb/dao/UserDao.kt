package com.ma.triviaapp.roomdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ma.triviaapp.roomdb.entity.QuestionAnswerDetailEntity
import com.ma.triviaapp.roomdb.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUserData():LiveData<List<UserEntity>>

    @Query("SELECT * FROM user WHERE userId IN (:id)")
    fun getSingleUserData(id:Int):LiveData<UserEntity>

    @Insert
    fun addUserData(item:UserEntity):Long

}