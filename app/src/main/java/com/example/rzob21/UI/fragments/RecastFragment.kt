package com.example.rzob21.UI.fragments

import android.view.KeyEvent
import android.view.View
import com.example.rzob21.ApiInterface.RecastApi
import com.example.rzob21.R
import com.example.rzob21.utilits.*
import com.gefro.springbootkotlinRZOBbackend.models.Recast
import kotlinx.android.synthetic.main.fragment_recast.*
import kotlinx.coroutines.*
import retrofit2.*


class RecastFragment(val recast: Recast = Recast(date = APP_DATE.toString(),recasthours = 0.0), var boolean: Boolean = false) : BaseChangeCalendarFragment(R.layout.fragment_recast) {



    override fun onResume() {
//        Log.d("kek", APP_DATE)
//        Log.d("kek", APP_DATE_TODAY)
//        Log.d("kek", APP_CALENDAR_DATE)
        super.onResume()
        if (boolean){
            recast_delete_image.visibility = View.VISIBLE
            recast_input_hours.setText(recast.recasthours.toString())
            recast_input_hours.requestFocus()
            recast_input_hours.setSelection(recast.recasthours.toString().length)
        }else recast_delete_image.visibility = View.INVISIBLE
        recast_data.text = APP_CALENDAR_DATE
        recast_input_hours.requestFocus()
        recast_input_hours.setSelection(recast_input_hours.getText().length)
        showKeyboard()
        recast_input_hours.setOnKeyListener(View.OnKeyListener{ v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                    change()
                    return@OnKeyListener true
                }
            false
            })
        recast_delete_image.setOnClickListener{
            val initDelete = GlobalScope.launch(Dispatchers.Main){
                val postOperation = async(Dispatchers.IO){
                    RecastApi().delete(Recast(recast.id, APP_DATE.toString()))
                }
                postOperation.await()
                fragmentManager?.popBackStack()
            }
        }
    }

    override fun change() {
        if (boolean){
            val hours = recast_input_hours.text.toString()
            if (hours.isEmpty() || hours.toDouble() == 0.0){
                showToast(getString(R.string.recast_toast_hours_is_empty))
            }else {
                val recast = Recast(recast.id, recast.date, hours.toDouble())
                val initPut = GlobalScope.launch(Dispatchers.Main) {
                    val putOperation = async(Dispatchers.IO) {
                        RecastApi().put(recast)
                    }
                    putOperation.await()
                    fragmentManager?.popBackStack()
                }
            }
        }else{
            val hours = recast_input_hours.text.toString()
            if (hours.isEmpty() || hours.toDouble() == 0.0){
                showToast(getString(R.string.recast_toast_hours_is_empty))
            }else {
                val initPost = GlobalScope.launch(Dispatchers.Main) {
                    val postOperation = async(Dispatchers.IO) {
                        RecastApi().post(hours.toDouble())
                    }
                    postOperation.await()
                    fragmentManager?.popBackStack()
                }
            }
        }
    }
}