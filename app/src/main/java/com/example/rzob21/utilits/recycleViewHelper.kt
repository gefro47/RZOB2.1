package com.example.rzob21.utilits

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rzob21.R
import com.example.rzob21.UI.fragments.CalendarFragment
import com.example.rzob21.UI.fragments.RecastFragment
import com.example.rzob21.models.Holiday
import com.example.rzob21.models.Recast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.event_recast_item.view.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

private lateinit var mRecycleView: RecyclerView
private lateinit var mRefRecast: DatabaseReference
private lateinit var mRefHoliday: DatabaseReference
//private lateinit var mAdapter: FirebaseRecyclerAdapter<Recast, CalendarFragment.RecastHolder>


fun initEvent(calendar_recycle_view: RecyclerView){

    lateinit var mAdapter1: FirebaseRecyclerAdapter<Recast, CalendarFragment.RecastHolder>
    lateinit var mAdapter2: FirebaseRecyclerAdapter<Holiday, CalendarFragment.HolidayHolder>
    mRecycleView = calendar_recycle_view
    mRecycleView.layoutManager = LinearLayoutManager(APP_ACTIVITY)
    mRefRecast = REF_DATABASE_ROOT.child(NODE_RECAST).child(UID).child(APP_CALENDAR_DATE_YEAR.toString()).child(APP_CALENDAR_DATE_MONTH.toString())
    mRefHoliday = REF_DATABASE_ROOT.child(NODE_HOLIDAY).child(UID).child(APP_CALENDAR_DATE_YEAR.toString()).child(APP_CALENDAR_DATE_MONTH.toString())

    val options1 = FirebaseRecyclerOptions.Builder<Recast>()
        .setQuery(mRefRecast, Recast::class.java)
        .build()

    mAdapter1 = object: FirebaseRecyclerAdapter<Recast, CalendarFragment.RecastHolder>(options1) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarFragment.RecastHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.event_recast_item, parent, false)
            return CalendarFragment.RecastHolder(view)
        }
        override fun onBindViewHolder(
            holder: CalendarFragment.RecastHolder,
            position: Int,
            model: Recast
        ){
            readDataRecastForRecycleView(holder,model)
            holder.itemView.image_edit.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    val activity = v!!.context as AppCompatActivity

//                    RECAST = LIST_OF_RECAST[position]

                    val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG)
                    APP_CALENDAR_DATE_DAY = LIST_OF_RECAST_DATE[position]
                    val calendar1 = Calendar.getInstance()
                    calendar1.set(APP_CALENDAR_DATE_YEAR, APP_CALENDAR_DATE_MONTH-1, APP_CALENDAR_DATE_DAY)
                    APP_CALENDAR_DATE = dateFormatter.format(calendar1.time)

                    activity.supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.dataContainer,
                            RecastFragment(APP_CALENDAR_DATE_DAY, true )
                        ).commit()
                }

            })
        }
    }
    mAdapter1.startListening()

    val options2 = FirebaseRecyclerOptions.Builder<Holiday>()
        .setQuery(mRefHoliday, Holiday::class.java)
        .build()

    mAdapter2 = object: FirebaseRecyclerAdapter<Holiday, CalendarFragment.HolidayHolder>(options2) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarFragment.HolidayHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.event_holiday_item, parent, false)
            return CalendarFragment.HolidayHolder(view)
        }

        override fun onBindViewHolder(
            holder: CalendarFragment.HolidayHolder,
            position: Int,
            model: Holiday
        ){
            readDataHolidayForRecycleView(holder,model)
        }
    }
    mAdapter2.startListening()
    val mAdapter = ConcatAdapter(mAdapter1, mAdapter2)
    mRecycleView.adapter = mAdapter
}