package com.ma.triviaapp.roomdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ma.triviaapp.roomdb.entity.QuestionAnswerDetailEntity
import com.ma.triviaapp.roomdb.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE userId IN (:id)")
    fun getSingleUserData(id:Int):LiveData<UserEntity>

    @Insert
    fun addUserData(item:UserEntity):Long

    @Query("DELETE FROM user WHERE userId IN (:id)")
    fun deleteUserData(id: Int)

    @Query("UPDATE user SET isFinishTest=:isFinish WHERE userId IN (:id)")
    fun editUserData(id: Int,isFinish:Boolean)

    @Query("SELECT * FROM user")
    fun getData():List<UserEntity>
}