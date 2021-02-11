package com.example.rzob21.UI.fragments

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.rzob21.R
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.change_event_list.view.*
import kotlinx.android.synthetic.main.event_recast_item.view.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment(R.layout.fragment_calendar) {

//
//    private lateinit var mRecycleView: RecyclerView
//    private lateinit var mRefRecast: DatabaseReference
//    private lateinit var mAdapter: FirebaseRecyclerAdapter<Recast, RecastHolder>

    class RecastHolder(view: View):RecyclerView.ViewHolder(view){
        val date:TextView = view.event_name
        val info:TextView = view.event_info
        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                showToast("${position + 1}")
            }
        }
    }

    class HolidayHolder(view: View):RecyclerView.ViewHolder(view){
        val date:TextView = view.event_name
        val info:TextView = view.event_info

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                showToast("${position + 1}")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(APP_CALENDAR_DATE == "") data.text = calendarikToday() else data.text = APP_CALENDAR_DATE
        calendarik()
        hideKeyboard()
        holidayPlusDate()
    }

    override fun onResume() {
        super.onResume()
        initRecycleView()
        initFields()
    }


    private fun initRecycleView() {
        initEvent(calendar_recycle_view)
    }

    private fun initFields() {
        container_for_add.setOnClickListener {
            val mDialogView = LayoutInflater.from(APP_ACTIVITY).inflate(
                R.layout.change_event_list,
                null
            )
            val mBuilder = AlertDialog.Builder(APP_ACTIVITY)
                .setView(mDialogView)
                .show()


            if (LIST_OF_RECAST_DATE.contains(APP_CALENDAR_DATE_DAY)){
                disabledItemOfChangeEventList(mDialogView.container_for_recast.recast_image, mDialogView.container_for_recast.recast_label_view, mDialogView.container_for_recast)
            }else{
            mDialogView.container_for_recast.setOnClickListener {
                replaceFragment(RecastFragment(APP_CALENDAR_DATE_DAY))
                mBuilder.cancel()
            }
            }
            if (LIST_OF_HOLIDAY_DATE.contains(APP_CALENDAR_DATE_DAY)){
                disabledItemOfChangeEventList(mDialogView.container_for_holiday.holiday,mDialogView.container_for_holiday.holiday_label,mDialogView.container_for_holiday)
            }else{
                mDialogView.container_for_holiday.setOnClickListener {
                    replaceFragment(HolidayFragment())
                    mBuilder.cancel()
                }
            }
        }

    }
    fun initDay(){
        val p_date_m = APP_CALENDAR_DATE_MONTH
        val p_date_y = APP_CALENDAR_DATE_YEAR
        if (p_date_m != APP_CALENDAR_DATE_MONTH_CHECK || p_date_y != APP_CALENDAR_DATE_YEAR_CHECK){
            LIST_OF_RECAST_DATE = mutableListOf<Int>()
            initRecycleView()
            APP_CALENDAR_DATE_MONTH_CHECK = p_date_m
            APP_CALENDAR_DATE_YEAR_CHECK = p_date_y
        }
    }

    fun calendarik(){
        val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG)
        val formate1 = SimpleDateFormat("MMMM")
        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->

            APP_CALENDAR_DATE_YEAR = year
            APP_CALENDAR_DATE_MONTH = month + 1
            APP_CALENDAR_DATE_DAY = dayOfMonth

            val calendar1 = Calendar.getInstance()
            calendar1.set(year, month, dayOfMonth)
            APP_CALENDAR_DATE = dateFormatter.format(calendar1.time)
            APP_DATE_PICK_MONTH_L = formate1.format(calendar1.time)
            data.text = APP_CALENDAR_DATE
            APP_DATE_DAY_OF_WEEK = calendar1.get(Calendar.DAY_OF_WEEK)
            initDay()
            holidayPlusDate()
        }
    }
}
//holiday_input_days.text.toString().toInt()