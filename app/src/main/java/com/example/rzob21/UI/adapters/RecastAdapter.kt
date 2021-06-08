package com.example.rzob21.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rzob21.R
import com.example.rzob21.UI.fragments.RecastFragment
import com.example.rzob21.utilits.*
import com.gefro.springbootkotlinRZOBbackend.models.Recast
import kotlinx.android.synthetic.main.event_recast_item.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class RecastAdapter : RecyclerView.Adapter<RecastAdapter.MyViewHolder>() {

    private var recastList = emptyList<Recast>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.event_recast_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = recastList[position]
        val date = currentItem.date.split("-")
        val day = date[2].toInt()
        holder.itemView.event_name.text = "Переработка ${day} $APP_DATE_PICK_MONTH_L"
        holder.itemView.event_info.text = "Переработано часов: ${currentItem.recasthours}"
        holder.itemView.recast_view.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity

//                    RECAST = LIST_OF_RECAST[position]

                val formate = SimpleDateFormat("yyyy-MM-dd")
                val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG)
                APP_CALENDAR_DATE_DAY = LIST_RECAST_OF_MONTH[position].date.split("-")[2].toInt()
                val calendar1 = Calendar.getInstance()
                calendar1.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH, APP_CALENDAR_DATE_DAY)
                APP_CALENDAR_DATE = dateFormatter.format(calendar1.time)
                val recast = Recast(LIST_RECAST_OF_MONTH[position].id, LIST_RECAST_OF_MONTH[position].date, LIST_RECAST_OF_MONTH[position].recasthours)

                activity.supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(
                        R.id.dataContainer,
                        RecastFragment(recast, true )
                    ).commit()
            }

        })
    }

    override fun getItemCount(): Int {
        return recastList.size
    }

    fun setData(recast: List<Recast>){
        this.recastList = recast
        notifyDataSetChanged()
    }
}