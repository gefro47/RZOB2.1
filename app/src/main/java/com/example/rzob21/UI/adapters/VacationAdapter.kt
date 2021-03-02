package com.example.rzob21.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rzob21.R
import com.example.rzob21.UI.fragments.RecastFragment
import com.example.rzob21.UI.fragments.VacationFragment
import com.example.rzob21.models.Vacation
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.event_vacation_item.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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
        holder.itemView.image_edit.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity

//                    RECAST = LIST_OF_RECAST[position]

                val formate = SimpleDateFormat("yyyy-MM-dd")
                val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG)
                APP_CALENDAR_DATE_DAY = LIST_OF_VACATION_DATE_START[position].split("-")[2].toInt()
                val calendar1 = Calendar.getInstance()
                calendar1.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH, APP_CALENDAR_DATE_DAY)
                APP_CALENDAR_DATE = dateFormatter.format(calendar1.time)

                activity.supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.dataContainer,
                        VacationFragment(formate.format(calendar1.time), true )
                    ).commit()
            }

        })
    }

    override fun getItemCount(): Int {
        return vacationList.size
    }
    fun setData(vacation: List<Vacation>){
        this.vacationList = vacation
        notifyDataSetChanged()
    }
}