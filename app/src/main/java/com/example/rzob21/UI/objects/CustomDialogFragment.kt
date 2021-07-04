package com.example.rzob21.UI.objects

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import com.example.rzob21.ApiInterface.IncomeApi
import com.example.rzob21.R
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.add_income_dialog.*
import kotlinx.android.synthetic.main.add_income_dialog.view.*
import kotlinx.android.synthetic.main.fragment_history_of_income.*


class CustomDialogFragment: DialogFragment() {
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        var rooView = inflater.inflate(R.layout.add_income_dialog, container, false)
//        return rooView
//    }
//
//    override fun onResume() {
//        super.onResume()
//        text_for_date.setText(INCOME_HISTORY_DATE_PICK_MONTH_AND_YEAR)
//        input_income_dialog.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
//            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
//                IncomeApi().post(
//                    input_income_dialog.text.toString().toDouble(),
//                    APP_INCOME_OF_HISTORY_DATE?.toString()!!.split("-")[1].toInt(),
//                    APP_INCOME_OF_HISTORY_DATE?.toString()!!.split("-")[0].toInt(),
//                    false
//                )
//                val imm =
//                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
//                imm!!.hideSoftInputFromWindow(view?.windowToken, 0)
//                dismiss()
//                return@OnKeyListener true
//            }
//            false
//        })
//    }
}