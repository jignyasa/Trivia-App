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
import com.ma.triviaapp.roomdb.entity.QuestionAnswerDetailEntity
import kotlinx.android.synthetic.main.lay_history_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HistoryAdapter(val context:Context) :RecyclerView.Adapter<HistoryAdapter.VHolder>()
{
    var alList=ArrayList<HistoryItem>()
    class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun addData(list:ArrayList<HistoryItem>)
    {
        alList=list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val v=LayoutInflater.from(context).inflate(R.layout.lay_history_item,parent,false)
        return VHolder(v)
    }

    override fun getItemCount(): Int {
        return alList.size
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val item=alList.get(position)
        holder.itemView.tvGameTime.text="Game ".plus(position+1).plus(" : ").plus(getDateFormate(Constant.DATE_FORMAT,item.timeMillis))
        holder.itemView.tvName.text=context.getString(R.string.name).plus(" : ").plus(item.userName)
        for(questionItem in item.alQuestionAnswer) {
            if(questionItem.question.equals(context.getString(R.string.question1))) {
                holder.itemView.tvQuestionOne.text =questionItem.question
                holder.itemView.tvQuestionOneAnswer.text=questionItem.answer
            }else
            {
                holder.itemView.tvQuestionTwo.text =questionItem.question
                holder.itemView.tvQuestionTwoAnswer.text=questionItem.answer
            }
        }
    }

    fun getDateFormate(dateFormate:String,timeMillies:Long):String
    {
        var sDate=""
        val sdf= SimpleDateFormat(dateFormate, Locale.ENGLISH)
        try {
            sDate=sdf.format(timeMillies)
        }catch (e: Exception)
        {
            Log.e("error",e.message)
        }
        return sDate
    }
}