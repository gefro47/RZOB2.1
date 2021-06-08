package com.example.rzob21.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rzob21.R
import com.example.rzob21.models.SickLeave
import kotlinx.android.synthetic.main.event_sick_leave_item.view.*
import java.sql.Date
import java.text.DateFormat

class SickLeaveAdapter : RecyclerView.Adapter<SickLeaveAdapter.MyViewHolder>(){

    private var sickLeaveList = emptyList<SickLeave>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.event_sick_leave_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG)
        val currentItem = sickLeaveList[position]
        holder.itemView.event_name.text = "${dateFormatter.format(Date.valueOf(currentItem.date_start))} - ${dateFormatter.format(Date.valueOf(currentItem.date_stop))}"
        holder.itemView.event_info.text = "Больничный"
    }

    override fun getItemCount(): Int {
        return sickLeaveList.size
    }

    fun setData(sickleave: List<SickLeave>){
        this.sickLeaveList = sickleave
        notifyDataSetChanged()
    }
}