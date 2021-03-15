package com.example.rzob21.UI.fragments

import androidx.lifecycle.ViewModelProvider
import com.example.rzob21.R
import com.example.rzob21.models.UserLocalDB
import com.example.rzob21.utilits.USER
import com.example.rzob21.utilits.showToast
import com.example.rzob21.viewmodel.UserLocalDBViewModel
import kotlinx.android.synthetic.main.fragment_change_salary.*


class ChangeSalaryFragment : BaseChangeFragment(R.layout.fragment_change_salary) {

    private lateinit var mUserLocalDBViewModel: UserLocalDBViewModel

    override fun onResume() {
        super.onResume()
        if (USER.salary != 0.0) settings_input_salary.setText(USER.salary.toString())
    }

    override fun change() {
        val salary = settings_input_salary.text.toString()
        if(salary.isEmpty() || salary.toDouble() == 0.0){
            showToast("Зарплата не может быть пустой или быть ровна нулю")
        }else{
            mUserLocalDBViewModel = ViewModelProvider(this).get(UserLocalDBViewModel::class.java)
            val UserUp = UserLocalDB(USER.id, USER.phone, USER.position,salary.toDouble(), USER.average_salary)
            mUserLocalDBViewModel.updateUser(UserUp)
//            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_SALARY)
//                .setValue(salary.toDouble())
//                .addOnCompleteListener {
//                    if(it.isSuccessful){
                        showToast(getString(R.string.toast_data_update))
                        USER.salary = salary.toDouble()
                        fragmentManager?.popBackStack()
//                    }
//                }
        }
    }
}