package com.example.rzob21.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rzob21.R
import com.example.rzob21.UI.fragments.SickLeaveFragment
import com.example.rzob21.UI.fragments.VacationFragment
import com.example.rzob21.models.PeriodModel
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.event_sick_leave_item.view.*
import kotlinx.android.synthetic.main.event_sick_leave_item.view.event_info
import kotlinx.android.synthetic.main.event_sick_leave_item.view.event_name
import kotlinx.android.synthetic.main.event_vacation_item.view.*
import java.sql.Date
import java.text.DateFormat
import java.util.*

class VacationAdapterForHistory: RecyclerView.Adapter<VacationAdapterForHistory.MyViewHolder>(){

    private var vacationList = emptyList<PeriodModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacationAdapterForHistory.MyViewHolder {
        return VacationAdapterForHistory.MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.event_vacation_item, parent, false)
        )
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onBindViewHolder(holder: VacationAdapterForHistory.MyViewHolder, position: Int) {
        val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG)
        val currentItem = vacationList[position]
        holder.itemView.event_name.text = "${dateFormatter.format(Date.valueOf(currentItem.date_start))} - ${dateFormatter.format(Date.valueOf(currentItem.date_stop))}"
//        holder.itemView.event_info.text = "Отпуск"

    }

    override fun getItemCount(): Int {
        return vacationList.size
    }

    fun setData(vacation: List<PeriodModel>){
        this.vacationList = vacation
        notifyDataSetChanged()
    }
}