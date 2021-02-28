package com.example.rzob21.UI.fragments

import androidx.lifecycle.ViewModelProvider
import com.example.rzob21.R
import com.example.rzob21.models.UserLocalDB
import com.example.rzob21.utilits.*
import com.example.rzob21.viewmodel.UserLocalDBViewModel
import kotlinx.android.synthetic.main.fragment_change_position.*


class ChangePositionFragment : BaseChangeFragment(R.layout.fragment_change_position) {

    private lateinit var mUserLocalDBViewModel: UserLocalDBViewModel

    override fun onResume() {
        super.onResume()
        settings_input_position.setText(USER.position)
    }

    override fun change() {
        val position = settings_input_position.text.toString()
        if (position.isEmpty()){
            showToast(getString(R.string.settings_toast_position_is_empty))
        }else {
            mUserLocalDBViewModel = ViewModelProvider(this).get(UserLocalDBViewModel::class.java)
            val UserUp = UserLocalDB(USER.id, USER.phone, position, USER.salary, USER.average_salary)
            mUserLocalDBViewModel.updateUser(UserUp)
//            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_POSITION_AT_WORK)
//                .setValue(position).addOnCompleteListener {
//                    if(it.isSuccessful){
                        showToast(getString(R.string.toast_data_update))
                        USER.position = position
                        fragmentManager?.popBackStack()
//                    }
//                }
        }
    }
}