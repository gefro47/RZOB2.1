package com.example.rzob21.utilits

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rzob21.UI.adapters.RecastAdapter
import com.example.rzob21.UI.adapters.SickLeaveAdapter
import com.example.rzob21.UI.adapters.VacationAdapter
import com.example.rzob21.models.SickLeave
import com.example.rzob21.models.Vacation
import kotlinx.android.synthetic.main.fragment_calendar.*

suspend fun initRecyclerView(calendar_recycle_view: RecyclerView, requireContext: Context) {
    val adapter1 = VacationAdapter()
    val adapter2 = RecastAdapter()
    val adapter3 = SickLeaveAdapter()

    val vacation: MutableList<Vacation> = dao.getVacationList(APP_CALENDAR_DATE_MONTH + 1) as MutableList<Vacation>
    for (i in dao.getVacationList(APP_CALENDAR_DATE_MONTH).indices){
        if(dao.getVacationList(APP_CALENDAR_DATE_MONTH)[i].date_stop.split("-")[1].toInt() == APP_CALENDAR_DATE_MONTH + 1){
            vacation.add(dao.getVacationList(APP_CALENDAR_DATE_MONTH)[i])
        }
    }
    adapter1.setData(vacation)

    val recast = dao.getRecastList(APP_CALENDAR_DATE_MONTH + 1)
    Log.d("recast", recast.toString())
    adapter2.setData(recast)

    val sickLeave: MutableList<SickLeave> = dao.getSickLeaveList(APP_CALENDAR_DATE_MONTH + 1) as MutableList<SickLeave>
    for (i in dao.getSickLeaveList(APP_CALENDAR_DATE_MONTH).indices){
        if (dao.getSickLeaveList(APP_CALENDAR_DATE_MONTH)[i].date_stop.split("-")[1].toInt() == APP_CALENDAR_DATE_MONTH + 1){
            sickLeave.add(dao.getSickLeaveList(APP_CALENDAR_DATE_MONTH)[i])
        }
    }
    adapter3.setData(sickLeave)

    val adapter = ConcatAdapter(adapter1, adapter2, adapter3)
    val recyclerView = calendar_recycle_view
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(requireContext)
}