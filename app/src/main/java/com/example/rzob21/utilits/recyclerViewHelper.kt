package com.example.rzob21.utilits

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rzob21.UI.adapters.HistoryOfIncomeAdapter
import com.example.rzob21.UI.adapters.RecastAdapter
import com.example.rzob21.UI.adapters.SickLeaveAdapter

fun initRecyclerViewForCalendarFragment(calendar_recycle_view: RecyclerView, requireContext: Context) {
    val adapter1 = SickLeaveAdapter()
    val adapter2 = RecastAdapter()

    val recast = LIST_RECAST_OF_MONTH
    val sickleave = LIST_SICK_LEAVE_OF_MONTH
    Log.d("recast", recast.toString())
    adapter1.setData(sickleave)
    adapter2.setData(recast)


    val adapter = ConcatAdapter(adapter1, adapter2)
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