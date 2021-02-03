package com.example.rzob21.UI.fragments

import androidx.fragment.app.Fragment
import com.example.rzob21.R
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.DateFormat
import java.util.*

class CalendarFragment : Fragment(R.layout.fragment_calendar) {



    override fun onStart() {
        super.onStart()
        calendarik()
    }

    private fun calendarik() {
        val calendar1 = Calendar.getInstance()
        val dateFormatter = DateFormat.getDateInstance(DateFormat.LONG)
        val date1 = Date()
//        val formate = SimpleDateFormat("d/M/yyyy")//date for FB
        data.text = dateFormatter.format(Date())
//        data.text = formate.format(date1)//date for FB
        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar1.set(year,month,dayOfMonth)
//            val date = "$dayOfMonth/$month/$year"//date for FB
            data.text = dateFormatter.format(calendar1.time)
        }
    }
}
