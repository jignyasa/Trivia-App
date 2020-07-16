package com.ma.triviaapp.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "question_answer")
class QuestionAnswerDetailEntity:Serializable {
    @PrimaryKey(autoGenerate = true)
    var no: Int = 0
    var userId: Int = 0
    var question: String = ""
    var answer: String = ""

}