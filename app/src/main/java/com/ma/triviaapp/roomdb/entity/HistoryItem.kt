package com.ma.triviaapp.roomdb.entity

data class HistoryItem( var userName: String = "",
                        var userId: Int = 0,
                        var timeMillis: Long = 0,
                        var alQuestionAnswer: ArrayList<QuestionAnswerDetailEntity> = ArrayList())