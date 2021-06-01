package com.example.rzob21.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rzob21.R
import com.example.rzob21.utilits.monthNames
import com.gefro.springbootkotlinRZOBbackend.models.Income
import kotlinx.android.synthetic.main.history_of_income_item.view.*

class HistoryOfIncomeAdapter: RecyclerView.Adapter<HistoryOfIncomeAdapter.MyViewHolder>() {

    private var incomeList = emptyList<Income>()

    class MyViewHolder(itenView: View): RecyclerView.ViewHolder(itenView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_of_income_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = incomeList[position]
        val money = currentItem.income_of_money
        val monthAndYear = "${monthNames[currentItem.month-1]} ${currentItem.year}"

        holder.itemView.event_info.text = money.toString()
        holder.itemView.event_name.text = monthAndYear
    }

    override fun getItemCount(): Int {
        return incomeList.size
    }

    fun setData(income: List<Income>){
        this.incomeList = income
        notifyDataSetChanged()
    }
}