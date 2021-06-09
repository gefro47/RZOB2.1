package com.example.rzob21.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rzob21.R
import com.example.rzob21.UI.fragments.RecastFragment
import com.example.rzob21.UI.fragments.SickLeaveFragment
import com.example.rzob21.models.SickLeave
import com.example.rzob21.utilits.*
import com.gefro.springbootkotlinRZOBbackend.models.Recast
import kotlinx.android.synthetic.main.event_recast_item.view.*
import kotlinx.android.synthetic.main.event_sick_leave_item.view.*
import kotlinx.android.synthetic.main.event_sick_leave_item.view.event_info
import kotlinx.android.synthetic.main.event_sick_leave_item.view.event_name
import java.sql.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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

        holder.itemView.sick_leave_view.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity

                val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG)
                APP_CALENDAR_DATE_DAY = LIST_SICK_LEAVE_OF_MONTH[position].date_start.split("-")[2].toInt()
                val calendar1 = Calendar.getInstance()
                calendar1.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH, APP_CALENDAR_DATE_DAY)
                APP_CALENDAR_DATE = dateFormatter.format(calendar1.time)

                val sickLeave = SickLeave(LIST_SICK_LEAVE_OF_MONTH[position].id, LIST_SICK_LEAVE_OF_MONTH[position].date_start, LIST_SICK_LEAVE_OF_MONTH[position].date_stop)

                activity.supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(
                        R.id.dataContainer,
                        SickLeaveFragment(sickLeave, true )
                    ).commit()
            }

        })
    }

    override fun getItemCount(): Int {
        return sickLeaveList.size
    }

    fun setData(sickleave: List<SickLeave>){
        this.sickLeaveList = sickleave
        notifyDataSetChanged()
    }
}