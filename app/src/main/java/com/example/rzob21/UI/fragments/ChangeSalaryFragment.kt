package com.example.rzob21.UI.fragments

import com.example.rzob21.ApiInterface.UserApi
import com.example.rzob21.R
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.fragment_change_salary.*


class ChangeSalaryFragment : BaseChangeFragment(R.layout.fragment_change_salary) {


    override fun onResume() {
        super.onResume()
        if (USER.salary != 0.0 && USER.salary != null) settings_input_salary.setText(USER.salary.toString())
    }

    override fun change() {
        val salary = settings_input_salary.text.toString()
        if(salary.isEmpty() || salary.toDouble() == 0.0){
            showToast("Зарплата не может быть пустой или быть ровна нулю")
        }else{
            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_SALARY)
                .setValue(salary.toDouble())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        showToast(getString(R.string.toast_data_update))
                        USER.salary = salary.toDouble()
                        UserApi().put()
                        fragmentManager?.popBackStack()
                    }
                }
        }
    }
}