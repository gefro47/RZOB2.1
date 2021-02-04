package com.example.rzob21.UI.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rzob21.R
import com.example.rzob21.models.Recast
import com.example.rzob21.utilits.*
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.change_event_list.view.*
import kotlinx.android.synthetic.main.event_recast_item.view.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment(R.layout.fragment_calendar) {


    private lateinit var mRecycleView: RecyclerView
    private lateinit var mRefRecast: DatabaseReference
    private lateinit var mRefRecastM: DatabaseReference
    private lateinit var mAdapter: FirebaseRecyclerAdapter<Recast, RecastHolder>
    private var check_date_month = APP_CALENDAR_DATE_MONTH_CHECK
    private var check_date_year = APP_CALENDAR_DATE_YEAR_CHECK

    class RecastHolder(view: View):RecyclerView.ViewHolder(view){
        val date:TextView = view.event_name
        val info:TextView = view.event_info
    }

    override fun onStart() {
        super.onStart()
        calendarik()
        hideKeyboard()
    }

    override fun onResume() {
        super.onResume()
        initFields()
        initRecycleView()
    }

    private fun initRecycleView() {
        mRecycleView = calendar_recycle_view
        mRecycleView.layoutManager = LinearLayoutManager(APP_ACTIVITY)
        mRefRecast = REF_DATABASE_ROOT.child(NODE_RECAST).child(UID).child(APP_CALENDAR_DATE_YEAR.toString()).child(
            APP_CALENDAR_DATE_MONTH.toString()
        )

        val options = FirebaseRecyclerOptions.Builder<Recast>()
            .setQuery(mRefRecast, Recast::class.java)
            .build()

        mAdapter = object:FirebaseRecyclerAdapter<Recast, RecastHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecastHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.event_recast_item, parent, false)
                return RecastHolder(view)
            }

            override fun onBindViewHolder(
                holder: RecastHolder,
                position: Int,
                model: Recast
            ){
                mRefRecastM = REF_DATABASE_ROOT.child(NODE_RECAST).child(UID).child(
                    APP_CALENDAR_DATE_YEAR.toString()
                ).child(APP_CALENDAR_DATE_MONTH.toString()).child(model.day_of_month.toString())
                mRefRecastM.addValueEventListener(AppValueEventListener {
                    val recast = it.getRecast()

                    holder.date.text = "${recast.day_of_month} $APP_DATE_PICK_MONTH_L"
                    holder.info.text = recast.recast_hours.toString()
                })
            }
        }
        mRecycleView.adapter = mAdapter
        mAdapter.startListening()
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
//            replaceFragment(ChangeEventListFragment())
            mDialogView.container_for_recast.setOnClickListener {
                replaceFragment(RecastFragment())
                mBuilder.cancel()
            }
        }

    }
    fun initDay(){
        val p_date_m = APP_CALENDAR_DATE_MONTH
        val p_date_y = APP_CALENDAR_DATE_YEAR
        if (p_date_m != check_date_month || p_date_y != check_date_year){
            mAdapter.notifyDataSetChanged()
            initRecycleView()
            check_date_month = p_date_m
            check_date_year = p_date_y
        }
    }

    private fun calendarik() {
        val calendar1 = Calendar.getInstance()
        val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG)
        val formate = SimpleDateFormat("yyyy-MM-dd")//date for FB
        val formate1 = SimpleDateFormat("MMMM")
        val Date =formate.format(Date()).split("-")
        APP_DATE_TODAY = dateFormatter.format(Date())
        data.text = APP_DATE_TODAY
        APP_DATE_PICK_MONTH_L = formate1.format(Date())
        APP_CALENDAR_DATE_YEAR = Date[0].toInt()
        APP_CALENDAR_DATE_YEAR_CHECK = Date[1].toInt()
        APP_CALENDAR_DATE_MONTH = Date[1].toInt()
        APP_CALENDAR_DATE_MONTH_CHECK = Date[1].toInt()
        APP_CALENDAR_DATE_DAY = Date[2].toInt()
        APP_CALENDAR_DATE = APP_DATE_TODAY
        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar1.set(year, month, dayOfMonth)
            APP_CALENDAR_DATE = dateFormatter.format(calendar1.time)
            APP_CALENDAR_DATE_YEAR = year
            APP_CALENDAR_DATE_MONTH = month + 1
            APP_CALENDAR_DATE_DAY = dayOfMonth
            APP_DATE_PICK_MONTH_L = formate1.format(calendar1.time)
            data.text = APP_CALENDAR_DATE
            initDay()
        }
    }
}
