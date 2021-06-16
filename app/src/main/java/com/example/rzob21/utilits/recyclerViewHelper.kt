package com.example.rzob21.utilits

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rzob21.UI.adapters.*

fun initRecyclerViewForCalendarFragment(calendar_recycle_view: RecyclerView, requireContext: Context) {
    val adapter1 = SickLeaveAdapter()
    val adapter3 = RecastAdapter()
    val adapter2 = VacationAdapter()

    val recast = LIST_RECAST_OF_MONTH
    val sickleave = LIST_SICK_LEAVE_OF_MONTH
    val vacation = LIST_VACATION_OF_MONTH
//    Log.d("recast", recast.toString())
    adapter1.setData(sickleave)
    adapter2.setData(vacation)
    adapter3.setData(recast)


    val adapter = ConcatAdapter(adapter1, adapter2, adapter3)
    val recyclerView = calendar_recycle_view
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(requireContext)
}

fun initRecyclerViewForHistoryOfIncomeFragment(income_recycle_view: RecyclerView, requireContext: Context) {
    val adapter1 = HistoryOfIncomeAdapter()

    val income = LIST_INCOME_OF_USER
    Log.d("income", income.toString())
    adapter1.setData(income)


    val adapter = ConcatAdapter(adapter1)
    val recyclerView = income_recycle_view
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(requireContext)
}

fun initRecyclerViewForHistoryOfVacationFragment(vacation_recycle_view: RecyclerView, requireContext: Context) {
    val adapter1 = VacationAdapterForHistory()

    val vacation = LIST_VACATION_OF_YEAR
    Log.d("vacation", vacation.toString())
    adapter1.setData(vacation)


    val adapter = ConcatAdapter(adapter1)
    val recyclerView = vacation_recycle_view
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(requireContext)
}

fun initRecyclerViewForHistoryOfSickLeaveFragment(sick_leave_recycle_view: RecyclerView, requireContext: Context) {
    val adapter1 = SickLeaveAdapterForHistory()

    val sickLeave = LIST_SICK_LEAVE_OF_YEAR
    Log.d("sickLeave", sickLeave.toString())
    adapter1.setData(sickLeave)


    val adapter = ConcatAdapter(adapter1)
    val recyclerView = sick_leave_recycle_view
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(requireContext)
}