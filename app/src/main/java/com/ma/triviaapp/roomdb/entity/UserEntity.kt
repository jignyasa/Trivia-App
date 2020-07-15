package com.ma.triviaapp.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserEntity(){
    @PrimaryKey(autoGenerate = true)
    var userId:Int=0
    var userName:String=""
    var timeMillies:Long=0
}
