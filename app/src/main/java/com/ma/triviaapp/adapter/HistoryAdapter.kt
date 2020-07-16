package com.ma.triviaapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ma.triviaapp.R
import com.ma.triviaapp.constant.Constant
import com.ma.triviaapp.roomdb.entity.HistoryItem
import kotlinx.android.synthetic.main.lay_history_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.VHolder>() {
    var alList = ArrayList<HistoryItem>()

    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView() {
            val item = alList[adapterPosition]
           itemView.tvGameDate.text = "Game ".plus(adapterPosition + 1).plus(" : ")
                .plus(getDateFormat(item.timeMillis))
           itemView.tvUserName.text =
                itemView.context.getString(R.string.name).plus(" : ").plus(item.userName)

            for (questionItem in item.alQuestionAnswer) {
                if (questionItem.question.equals(itemView.context.getString(R.string.question1))) {
                   itemView.tvQuestionOne.text = questionItem.question
                   itemView.tvQuestionOneAnswer.text = questionItem.answer
                } else {
                   itemView.tvQuestionTwo.text = questionItem.question
                   itemView.tvQuestionTwoAnswer.text = questionItem.answer
                }
            }
        }

        private fun getDateFormat(timeMillis: Long): String {
            var sDate = ""
            val sdf = SimpleDateFormat(Constant.DATE_FORMAT, Locale.ENGLISH)
            try {
                sDate = sdf.format(timeMillis)
            } catch (e: Exception) {
                Log.e("DateParsingError", e.message)
            }
            return sDate
        }
    }

    fun addData(list: ArrayList<HistoryItem>) {
        alList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.lay_history_item, parent, false)
        return VHolder(v)
    }

    override fun getItemCount(): Int = alList.size


    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bindView()
    }
}