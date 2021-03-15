package com.example.rzob21.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rzob21.R
import com.example.rzob21.UI.fragments.SickLeaveFragment
import com.example.rzob21.UI.fragments.VacationFragment
import com.example.rzob21.models.SickLeave
import com.example.rzob21.models.Vacation
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.event_vacation_item.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Month
import java.time.format.TextStyle
import java.util.*

class SickLeaveAdapter: RecyclerView.Adapter<SickLeaveAdapter.MyViewHolder>() {

    private var sickLeaveList = emptyList<SickLeave>()

    class MyViewHolder(itenView: View): RecyclerView.ViewHolder(itenView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.event_sick_leave_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = sickLeaveList[position]
        val monthNames = listOf("Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря")
        holder.itemView.event_name.text = "Больничный ${currentItem.number_of_days} дней(-я)"
        holder.itemView.event_info.text = "C ${currentItem.date_start.split("-")[2].toInt()} ${monthNames[currentItem.date_start.split("-")[1].toInt()-1]} " +
                "по ${currentItem.date_stop.split("-")[2].toInt()} ${monthNames[currentItem.date_stop.split("-")[1].toInt()-1]}"
        holder.itemView.image_edit.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity
                val formate = SimpleDateFormat("yyyy-MM-dd")
                val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG)
                APP_CALENDAR_DATE_DAY = sickLeaveList[position].date_start.split("-")[2].toInt()
                APP_CALENDAR_DATE_MONTH_S = sickLeaveList[position].date_start.split("-")[1].toInt() - 1
                val calendar1 = Calendar.getInstance()
                calendar1.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH_S, APP_CALENDAR_DATE_DAY)
                APP_CALENDAR_DATE = dateFormatter.format(calendar1.time)
                activity.supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.dataContainer,
                        SickLeaveFragment(formate.format(calendar1.time), true )
                    ).commit()
            }

        })
    }

    override fun getItemCount(): Int {
        return sickLeaveList.size
    }
    fun setData(sickLeave: List<SickLeave>){
        this.sickLeaveList = sickLeave
        notifyDataSetChanged()
    }
}