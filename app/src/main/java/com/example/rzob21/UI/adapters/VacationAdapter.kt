package com.example.rzob21.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rzob21.R
import com.example.rzob21.models.Vacation
import kotlinx.android.synthetic.main.event_vacation_item.view.*

class VacationAdapter: RecyclerView.Adapter<VacationAdapter.MyViewHolder>() {

    private var vacationList = emptyList<Vacation>()

    class MyViewHolder(itenView: View): RecyclerView.ViewHolder(itenView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.event_vacation_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = vacationList[position]
        holder.itemView.event_name.text = "Отпуск ${currentItem.number_of_days} дней(-я)"
        holder.itemView.event_info.text = "C ${currentItem.date_start} по ${currentItem.date_stop}"
    }

    override fun getItemCount(): Int {
        return vacationList.size
    }
    fun setData(vacation: List<Vacation>){
        this.vacationList = vacation
        notifyDataSetChanged()
    }
}