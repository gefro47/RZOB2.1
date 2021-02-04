package com.example.rzob21.UI.fragments

import com.example.rzob21.R
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.fragment_recast.*


class RecastFragment : BaseChangeCalendarFragment(R.layout.fragment_recast) {
    override fun onResume() {
        super.onResume()
        recast_input_hours.setText(RECAST.recast_hours.toString())
        recast_data.text = APP_CALENDAR_DATE
    }

    override fun change() {
        val hours = recast_input_hours.text.toString()
        if (hours.isEmpty() || hours.toDouble() == 0.0){
            showToast(getString(R.string.recast_toast_hours_is_empty))
        }else {
            REF_DATABASE_ROOT.child(NODE_RECAST)
                .child(UID).child(APP_CALENDAR_DATE_YEAR.toString())
                .child(APP_CALENDAR_DATE_MONTH.toString())
                .child(APP_CALENDAR_DATE_DAY.toString())
                .child(CHILD_RECAST_HOURS)
                .setValue(hours.toDouble()).addOnCompleteListener {it1 ->
                    if (it1.isSuccessful) {
//                        showToast(getString(R.string.toast_data_update))
                        REF_DATABASE_ROOT.child(NODE_RECAST)
                            .child(UID).child(APP_CALENDAR_DATE_YEAR.toString())
                            .child(APP_CALENDAR_DATE_MONTH.toString())
                            .child(APP_CALENDAR_DATE_DAY.toString())
                            .child(CHILD_RECAST_DAY)
                            .setValue(APP_CALENDAR_DATE_DAY).addOnCompleteListener {it2 ->
                                if (it2.isSuccessful) {
                                    showToast(getString(R.string.toast_data_update))
                                    fragmentManager?.popBackStack()
                                }
                            }
                    }
                }
        }
    }
}