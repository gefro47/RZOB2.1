package com.example.rzob21.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rzob21.R
import com.example.rzob21.UI.fragments.SickLeaveFragment
import com.example.rzob21.models.PeriodModel
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.event_sick_leave_item.view.*
import kotlinx.android.synthetic.main.event_sick_leave_item.view.event_info
import kotlinx.android.synthetic.main.event_sick_leave_item.view.event_name
import java.sql.Date
import java.text.DateFormat
import java.util.*

class SickLeaveAdapterForHistory : RecyclerView.Adapter<SickLeaveAdapterForHistory.MyViewHolder>(){

    private var sickLeaveList = emptyList<PeriodModel>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.event_sick_leave_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG)
        val currentItem = sickLeaveList[position]
        holder.itemView.event_name.text = "${dateFormatter.format(Date.valueOf(currentItem.date_start))} - ${dateFormatter.format(Date.valueOf(currentItem.date_stop))}"
    }

    override fun getItemCount(): Int {
        return sickLeaveList.size
    }

    fun setData(sickleave: List<PeriodModel>){
        this.sickLeaveList = sickleave
        notifyDataSetChanged()
    }
}