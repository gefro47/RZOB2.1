package com.example.rzob21.UI.fragments

import com.example.rzob21.ApiInterface.UserApi
import com.example.rzob21.R
import com.example.rzob21.utilits.*
import kotlinx.android.synthetic.main.fragment_change_position.*


class ChangePositionFragment : BaseChangeFragment(R.layout.fragment_change_position) {


    override fun onResume() {
        super.onResume()
        settings_input_position.setText(USER.position)
    }

    override fun change() {
        val position = settings_input_position.text.toString()
        if (position.isEmpty()){
            showToast(getString(R.string.settings_toast_position_is_empty))
        }else {
            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_POSITION_AT_WORK)
                .setValue(position).addOnCompleteListener {
                    if(it.isSuccessful){
                        showToast(getString(R.string.toast_data_update))
                        USER.position = position
                        UserApi().put()
                        fragmentManager?.popBackStack()
                    }
                }
        }
    }
}